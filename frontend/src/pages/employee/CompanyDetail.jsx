import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { getCompanyById } from "../../api/company";
import { getAllJobs } from "../../api/job";
import { getAllReviews, createReview, updateReview, deleteReview } from "../../api/review";
import { extractErrorMessage } from "../../api/client";
import { Banner, Spinner, EmptyState } from "../../components/Feedback";

const emptyReview = { title: "", description: "", rating: "" };

export default function CompanyDetail() {
  const { id } = useParams();

  const [company, setCompany] = useState(null);
  const [jobs, setJobs] = useState([]);
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [form, setForm] = useState(emptyReview);
  const [editingReviewId, setEditingReviewId] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [formError, setFormError] = useState("");
  const [notice, setNotice] = useState("");
  const [busy, setBusy] = useState(false);

  const load = async () => {
    setLoading(true);
    setError("");
    try {
      const [companyRes, jobsRes] = await Promise.all([getCompanyById(id), getAllJobs()]);
      setCompany(companyRes.data);
      setJobs((jobsRes.data || []).filter((j) => j.company?.id === Number(id)));
    } catch (err) {
      setError(extractErrorMessage(err, "Could not load this company."));
      setLoading(false);
      return;
    }

    try {
      const reviewsRes = await getAllReviews(id);
      setReviews(reviewsRes.data);
    } catch (err) {
      if (err?.response?.status !== 404) {
        setError(extractErrorMessage(err, "Could not load reviews."));
      } else {
        setReviews([]);
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [id]);

  const startCreate = () => {
    setForm(emptyReview);
    setEditingReviewId(null);
    setShowForm(true);
    setFormError("");
  };

  const startEdit = (review) => {
    setForm({ title: review.title || "", description: review.description || "", rating: review.rating || "" });
    setEditingReviewId(review.reviewId);
    setShowForm(true);
    setFormError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setBusy(true);
    setFormError("");
    try {
      if (editingReviewId) {
        await updateReview(id, editingReviewId, form);
        setNotice("Remark updated.");
      } else {
        await createReview(id, form);
        setNotice("Remark posted.");
      }
      setShowForm(false);
      load();
    } catch (err) {
      setFormError(extractErrorMessage(err, "Could not save your remark."));
    } finally {
      setBusy(false);
    }
  };

  const handleDelete = async (reviewId) => {
    if (!window.confirm("Delete this remark?")) return;
    setBusy(true);
    try {
      await deleteReview(id, reviewId);
      setNotice("Remark deleted.");
      load();
    } catch (err) {
      setError(extractErrorMessage(err, "Could not delete the remark."));
    } finally {
      setBusy(false);
    }
  };

  if (loading) return <Spinner label="Loading company…" />;
  if (error && !company) return <Banner type="error">{error}</Banner>;
  if (!company) return null;

  return (
    <div className="stack">
      <Link to="/employee/companies" style={{ fontSize: "0.85rem", color: "var(--ink-soft)" }}>
        ← Back to companies
      </Link>

      <div className="card">
        <span className="page-eyebrow">Company #{company.id}</span>
        <h1 style={{ fontSize: "1.7rem" }}>{company.name}</h1>
        <p style={{ color: "var(--ink-soft)" }}>{company.description || "No description provided."}</p>
      </div>

      <Banner type="success">{notice}</Banner>
      <Banner type="error">{error}</Banner>

      <div className="card">
        <h2 style={{ fontSize: "1.05rem" }}>Open roles</h2>
        {jobs.length === 0 ? (
          <p style={{ color: "var(--ink-soft)" }}>No jobs listed for this company yet.</p>
        ) : (
          <div className="stack">
            {jobs.map((job) => (
              <div className="index-card" key={job.id}>
                <div className="index-card-head">
                  <h3 className="index-card-title">{job.title}</h3>
                  <span className="index-card-meta">{job.location}</span>
                </div>
                <p className="index-card-body">{job.description}</p>
                {(job.minSalary || job.maxSalary) && (
                  <span className="tag">
                    {job.minSalary || "?"} – {job.maxSalary || "?"}
                  </span>
                )}
              </div>
            ))}
          </div>
        )}
      </div>

      <div className="card">
        <div className="repeat-block-head">
          <h2 style={{ fontSize: "1.05rem", margin: 0 }}>Remarks</h2>
          <button className="btn btn-primary btn-sm" onClick={startCreate}>
            + Leave a remark
          </button>
        </div>

        {showForm && (
          <div className="repeat-block">
            <Banner type="error">{formError}</Banner>
            <form onSubmit={handleSubmit}>
              <div className="form-row">
                <div className="field">
                  <label>Title</label>
                  <input className="input" value={form.title} onChange={(e) => setForm({ ...form, title: e.target.value })} required />
                </div>
                <div className="field">
                  <label>Rating</label>
                  <select className="select" value={form.rating} onChange={(e) => setForm({ ...form, rating: e.target.value })} required>
                    <option value="">Select</option>
                    {[1, 2, 3, 4, 5].map((n) => (
                      <option key={n} value={n}>
                        {n} / 5
                      </option>
                    ))}
                  </select>
                </div>
              </div>
              <div className="field">
                <label>Remark</label>
                <textarea className="textarea" value={form.description} onChange={(e) => setForm({ ...form, description: e.target.value })} required />
              </div>
              <div style={{ display: "flex", gap: 8 }}>
                <button className="btn btn-primary btn-sm" type="submit" disabled={busy}>
                  {editingReviewId ? "Save changes" : "Post remark"}
                </button>
                <button className="btn btn-ghost btn-sm" type="button" onClick={() => setShowForm(false)}>
                  Cancel
                </button>
              </div>
            </form>
          </div>
        )}

        {reviews.length === 0 ? (
          <EmptyState>No remarks yet — be the first to leave one.</EmptyState>
        ) : (
          <div className="stack">
            {reviews.map((r) => (
              <div className="index-card tone-amber" key={r.reviewId}>
                <div className="index-card-head">
                  <h3 className="index-card-title">{r.title}</h3>
                  <span className="tag rating-tag">{r.rating} / 5</span>
                </div>
                <p className="index-card-body">{r.description}</p>
                <div className="index-card-actions">
                  <button className="btn btn-ghost btn-sm" onClick={() => startEdit(r)}>
                    Edit
                  </button>
                  <button className="btn btn-danger btn-sm" onClick={() => handleDelete(r.reviewId)} disabled={busy}>
                    Delete
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
