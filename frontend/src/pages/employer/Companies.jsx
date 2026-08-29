import { useEffect, useState } from "react";
import { getAllCompanies, createCompany, updateCompany, deleteCompany } from "../../api/company";
import { extractErrorMessage } from "../../api/client";
import { Banner, Spinner, EmptyState } from "../../components/Feedback";

const emptyForm = { name: "", description: "" };

export default function EmployerCompanies() {
  const [companies, setCompanies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [formError, setFormError] = useState("");
  const [notice, setNotice] = useState("");
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [busy, setBusy] = useState(false);

  const load = async () => {
    setLoading(true);
    setError("");
    try {
      const res = await getAllCompanies();
      setCompanies(res.data);
    } catch (err) {
      if (err?.response?.status === 404) {
        setCompanies([]);
      } else {
        setError(extractErrorMessage(err, "Could not load companies."));
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
  }, []);

  const startCreate = () => {
    setForm(emptyForm);
    setEditingId(null);
    setShowForm(true);
    setFormError("");
  };

  const startEdit = (company) => {
    setForm({ name: company.name || "", description: company.description || "" });
    setEditingId(company.id);
    setShowForm(true);
    setFormError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setBusy(true);
    setFormError("");
    try {
      if (editingId) {
        await updateCompany(editingId, form);
        setNotice("Company updated.");
      } else {
        await createCompany(form);
        setNotice("Company created.");
      }
      setShowForm(false);
      load();
    } catch (err) {
      setFormError(extractErrorMessage(err, "Could not save the company."));
    } finally {
      setBusy(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this company? Its jobs and reviews will be orphaned.")) return;
    setBusy(true);
    setError("");
    try {
      await deleteCompany(id);
      setNotice("Company deleted.");
      load();
    } catch (err) {
      setError(extractErrorMessage(err, "Could not delete the company."));
    } finally {
      setBusy(false);
    }
  };

  return (
    <div>
      <div className="page-header">
        <div>
          <span className="page-eyebrow">Employer · Companies</span>
          <h1 style={{ fontSize: "1.8rem" }}>Companies</h1>
        </div>
        <button className="btn btn-indigo" onClick={startCreate}>
          + New company
        </button>
      </div>

      <Banner type="info">
        The backend doesn't tie a company to the employer who created it, so every employer
        can see and edit every company here — same as it does with jobs.
      </Banner>
      <Banner type="success">{notice}</Banner>
      <Banner type="error">{error}</Banner>

      {showForm && (
        <div className="card" style={{ marginBottom: 20 }}>
          <h2 style={{ fontSize: "1.05rem" }}>{editingId ? "Edit company" : "New company"}</h2>
          <Banner type="error">{formError}</Banner>
          <form onSubmit={handleSubmit}>
            <div className="field">
              <label>Company name</label>
              <input
                className="input"
                value={form.name}
                onChange={(e) => setForm({ ...form, name: e.target.value })}
                required
              />
            </div>
            <div className="field">
              <label>Description</label>
              <textarea
                className="textarea"
                value={form.description}
                onChange={(e) => setForm({ ...form, description: e.target.value })}
              />
            </div>
            <div style={{ display: "flex", gap: 8 }}>
              <button className="btn btn-indigo" type="submit" disabled={busy}>
                {editingId ? "Save changes" : "Create company"}
              </button>
              <button className="btn btn-ghost" type="button" onClick={() => setShowForm(false)}>
                Cancel
              </button>
            </div>
          </form>
        </div>
      )}

      {loading ? (
        <Spinner label="Loading companies…" />
      ) : companies.length === 0 ? (
        <EmptyState>No companies yet. Create the first one.</EmptyState>
      ) : (
        <div className="grid-2">
          {companies.map((c) => (
            <div className="index-card tone-indigo" key={c.id}>
              <div className="index-card-head">
                <h3 className="index-card-title">{c.name}</h3>
                <span className="index-card-meta">#{c.id}</span>
              </div>
              <p className="index-card-body">{c.description || "No description yet."}</p>
              <div className="index-card-actions">
                <button className="btn btn-ghost btn-sm" onClick={() => startEdit(c)}>
                  Edit
                </button>
                <button className="btn btn-danger btn-sm" onClick={() => handleDelete(c.id)} disabled={busy}>
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
