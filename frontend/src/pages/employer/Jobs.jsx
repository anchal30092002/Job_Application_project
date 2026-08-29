import { useEffect, useState } from "react";
import { getAllJobs, createJob, updateJob, deleteJob } from "../../api/job";
import { getAllCompanies } from "../../api/company";
import { extractErrorMessage } from "../../api/client";
import { Banner, Spinner, EmptyState } from "../../components/Feedback";

const emptyForm = { title: "", description: "", minSalary: "", maxSalary: "", location: "", companyId: "" };

export default function EmployerJobs() {
  const [jobs, setJobs] = useState([]);
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
      const [jobsRes, companiesRes] = await Promise.allSettled([getAllJobs(), getAllCompanies()]);
      setJobs(jobsRes.status === "fulfilled" ? jobsRes.value.data : []);
      setCompanies(companiesRes.status === "fulfilled" ? companiesRes.value.data : []);
    } catch (err) {
      setError(extractErrorMessage(err, "Could not load jobs."));
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

  const startEdit = (job) => {
    setForm({
      title: job.title || "",
      description: job.description || "",
      minSalary: job.minSalary || "",
      maxSalary: job.maxSalary || "",
      location: job.location || "",
      companyId: job.company?.id || "",
    });
    setEditingId(job.id);
    setShowForm(true);
    setFormError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setBusy(true);
    setFormError("");

    const payload = {
      title: form.title,
      description: form.description,
      minSalary: form.minSalary,
      maxSalary: form.maxSalary,
      location: form.location,
      company: { id: Number(form.companyId) },
    };

    try {
      if (editingId) {
        // The backend's update endpoint ignores the company field, so moving
        // a job to a different company isn't possible from here.
        await updateJob(editingId, payload);
        setNotice("Job updated.");
      } else {
        if (!form.companyId) {
          setFormError("Pick a company first — jobs can't exist without one.");
          setBusy(false);
          return;
        }
        await createJob(payload);
        setNotice("Job posted.");
      }
      setShowForm(false);
      load();
    } catch (err) {
      setFormError(extractErrorMessage(err, "Could not save the job."));
    } finally {
      setBusy(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this job listing?")) return;
    setBusy(true);
    setError("");
    try {
      await deleteJob(id);
      setNotice("Job deleted.");
      load();
    } catch (err) {
      setError(extractErrorMessage(err, "Could not delete the job."));
    } finally {
      setBusy(false);
    }
  };

  return (
    <div>
      <div className="page-header">
        <div>
          <span className="page-eyebrow">Employer · Jobs</span>
          <h1 style={{ fontSize: "1.8rem" }}>Manage jobs</h1>
        </div>
        <button className="btn btn-indigo" onClick={startCreate} disabled={companies.length === 0}>
          + Post a job
        </button>
      </div>

      {companies.length === 0 && !loading && (
        <Banner type="info">You'll need a company before you can post a job.</Banner>
      )}
      <Banner type="success">{notice}</Banner>
      <Banner type="error">{error}</Banner>

      {showForm && (
        <div className="card" style={{ marginBottom: 20 }}>
          <h2 style={{ fontSize: "1.05rem" }}>{editingId ? "Edit job" : "New job"}</h2>
          <Banner type="error">{formError}</Banner>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="field">
                <label>Job title</label>
                <input className="input" value={form.title} onChange={(e) => setForm({ ...form, title: e.target.value })} required />
              </div>
              <div className="field">
                <label>Company</label>
                <select
                  className="select"
                  value={form.companyId}
                  onChange={(e) => setForm({ ...form, companyId: e.target.value })}
                  disabled={Boolean(editingId)}
                  required
                >
                  <option value="">Select a company</option>
                  {companies.map((c) => (
                    <option key={c.id} value={c.id}>
                      {c.name}
                    </option>
                  ))}
                </select>
                {editingId && <span className="field-hint">The company can't be changed after posting.</span>}
              </div>
            </div>
            <div className="field">
              <label>Description</label>
              <textarea className="textarea" value={form.description} onChange={(e) => setForm({ ...form, description: e.target.value })} />
            </div>
            <div className="form-row">
              <div className="field">
                <label>Min salary</label>
                <input className="input" value={form.minSalary} onChange={(e) => setForm({ ...form, minSalary: e.target.value })} placeholder="e.g. 6 LPA" />
              </div>
              <div className="field">
                <label>Max salary</label>
                <input className="input" value={form.maxSalary} onChange={(e) => setForm({ ...form, maxSalary: e.target.value })} placeholder="e.g. 10 LPA" />
              </div>
              <div className="field">
                <label>Location</label>
                <input className="input" value={form.location} onChange={(e) => setForm({ ...form, location: e.target.value })} />
              </div>
            </div>
            <div style={{ display: "flex", gap: 8 }}>
              <button className="btn btn-indigo" type="submit" disabled={busy}>
                {editingId ? "Save changes" : "Post job"}
              </button>
              <button className="btn btn-ghost" type="button" onClick={() => setShowForm(false)}>
                Cancel
              </button>
            </div>
          </form>
        </div>
      )}

      {loading ? (
        <Spinner label="Loading jobs…" />
      ) : jobs.length === 0 ? (
        <EmptyState>No jobs posted yet.</EmptyState>
      ) : (
        <div className="stack">
          {jobs.map((job) => (
            <div className="index-card tone-indigo" key={job.id}>
              <div className="index-card-head">
                <h3 className="index-card-title">{job.title}</h3>
                <span className="index-card-meta">{job.company?.name || "Unassigned"}</span>
              </div>
              <p className="index-card-body">{job.description}</p>
              <div style={{ display: "flex", flexWrap: "wrap", gap: 6, marginTop: 8 }}>
                {job.location && <span className="tag">{job.location}</span>}
                {(job.minSalary || job.maxSalary) && (
                  <span className="tag">
                    {job.minSalary || "?"} – {job.maxSalary || "?"}
                  </span>
                )}
              </div>
              <div className="index-card-actions">
                <button className="btn btn-ghost btn-sm" onClick={() => startEdit(job)}>
                  Edit
                </button>
                <button className="btn btn-danger btn-sm" onClick={() => handleDelete(job.id)} disabled={busy}>
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
