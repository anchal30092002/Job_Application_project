import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getEmployerProfile, activateEmployer, deleteEmployer } from "../../api/person";
import { extractErrorMessage } from "../../api/client";
import { useAuth } from "../../context/AuthContext";
import { Banner, Spinner } from "../../components/Feedback";

export default function EmployerProfile() {
  const { session, logout } = useAuth();
  const navigate = useNavigate();

  const [profile, setProfile] = useState(null);
  const [notFound, setNotFound] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [actionMessage, setActionMessage] = useState("");
  const [actionError, setActionError] = useState("");
  const [busy, setBusy] = useState(false);

  const load = async () => {
    setLoading(true);
    setError("");
    setNotFound(false);
    try {
      const res = await getEmployerProfile(session.emailId, session.password);
      setProfile(res.data);
    } catch (err) {
      if (err?.response?.status === 404) {
        setNotFound(true);
      } else {
        setError(extractErrorMessage(err, "Could not load your profile."));
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleActivate = async () => {
    setBusy(true);
    setActionMessage("");
    setActionError("");
    try {
      await activateEmployer(session.emailId, session.password);
      setActionMessage("Your account is now active.");
      load();
    } catch (err) {
      setActionError(extractErrorMessage(err, "Could not activate your account."));
    } finally {
      setBusy(false);
    }
  };

  const handleDelete = async () => {
    if (!window.confirm("This deactivates your employer account. Continue?")) return;
    setBusy(true);
    setActionError("");
    try {
      await deleteEmployer(session.emailId, session.password);
      logout();
      navigate("/");
    } catch (err) {
      setActionError(extractErrorMessage(err, "Could not delete your account."));
    } finally {
      setBusy(false);
    }
  };

  if (loading) return <Spinner label="Loading your profile…" />;

  if (notFound) {
    return (
      <div className="empty-state">
        <h2 style={{ fontSize: "1.2rem" }}>No profile on file yet</h2>
        <p>You've registered, but haven't completed your employer profile.</p>
        <Link to="/employer/complete-profile" className="btn btn-indigo">
          Complete your profile
        </Link>
      </div>
    );
  }

  if (error) {
    return <Banner type="error">{error}</Banner>;
  }

  if (!profile) return null;

  const initials = (profile.emailId || "?").trim().slice(0, 2).toUpperCase();

  return (
    <div className="stack">
      <div className="id-badge employer">
        <div className="id-badge-photo">{initials}</div>
        <div className="id-badge-info">
          <span className="id-badge-role">Employer record</span>
          <h1 className="id-badge-name">{profile.name || "Unnamed contact"}</h1>
          <p className="id-badge-line">{profile.emailId}</p>
          {profile.phoneNo && <p className="id-badge-line">{profile.phoneNo}</p>}
          <span className="id-badge-status">
            <span className={`status-dot ${profile.active ? "" : "inactive"}`} />
            {profile.active ? "Active" : "Inactive"}
          </span>
        </div>
      </div>

      <Banner type="success">{actionMessage}</Banner>
      <Banner type="error">{actionError}</Banner>

      {!profile.active && (
        <div className="card card-tight">
          <strong>Your account is inactive.</strong> Activate it to open companies and post
          jobs.
          <div style={{ marginTop: 10 }}>
            <button className="btn btn-indigo btn-sm" onClick={handleActivate} disabled={busy}>
              Activate account
            </button>
          </div>
        </div>
      )}

      <div className="grid-2">
        <div className="card">
          <h2 style={{ fontSize: "1.02rem" }}>About</h2>
          <p style={{ color: "var(--ink-soft)" }}>{profile.summary || "No summary added."}</p>
          <div style={{ display: "flex", flexWrap: "wrap", gap: 4, marginTop: 8 }}>
            {profile.gender && <span className="tag">{profile.gender}</span>}
            {profile.citizen && <span className="tag">{profile.citizen}</span>}
          </div>
        </div>
        <div className="card">
          <h2 style={{ fontSize: "1.02rem" }}>Address</h2>
          {profile.address ? (
            <p style={{ color: "var(--ink-soft)" }}>
              {[
                profile.address.houseNO ?? profile.address.houseno,
                profile.address.street,
                profile.address.city,
                profile.address.state,
                profile.address.country,
                profile.address.pincode,
              ]
                .filter(Boolean)
                .join(", ")}
            </p>
          ) : (
            <p style={{ color: "var(--ink-soft)" }}>No address on file.</p>
          )}
        </div>
      </div>

      <div className="card card-tight" style={{ display: "flex", justifyContent: "space-between", alignItems: "center", flexWrap: "wrap", gap: 12 }}>
        <div>
          <strong>Manage your listings</strong>
          <p style={{ margin: "4px 0 0", color: "var(--ink-soft)", fontSize: "0.85rem" }}>
            Open companies and post jobs from here.
          </p>
        </div>
        <div style={{ display: "flex", gap: 8 }}>
          <Link to="/employer/companies" className="btn btn-ghost btn-sm">
            My companies
          </Link>
          <Link to="/employer/jobs" className="btn btn-ghost btn-sm">
            Manage jobs
          </Link>
        </div>
      </div>

      <div className="card card-tight" style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
        <div>
          <strong>Close your account</strong>
          <p style={{ margin: "4px 0 0", color: "var(--ink-soft)", fontSize: "0.85rem" }}>
            This deactivates your employer record.
          </p>
        </div>
        <button className="btn btn-danger" onClick={handleDelete} disabled={busy}>
          Delete account
        </button>
      </div>
    </div>
  );
}
