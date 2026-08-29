import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getEmployeeProfile, activateEmployee, deleteEmployee } from "../../api/person";
import { extractErrorMessage } from "../../api/client";
import { useAuth } from "../../context/AuthContext";
import { Banner, Spinner } from "../../components/Feedback";

export default function EmployeeProfile() {
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
      const res = await getEmployeeProfile(session.emailId, session.password);
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
      await activateEmployee(session.emailId, session.password);
      setActionMessage("Your account is now active.");
      load();
    } catch (err) {
      setActionError(extractErrorMessage(err, "Could not activate your account."));
    } finally {
      setBusy(false);
    }
  };

  const handleDelete = async () => {
    if (!window.confirm("This deactivates your employee account. Continue?")) return;
    setBusy(true);
    setActionError("");
    try {
      await deleteEmployee(session.emailId, session.password);
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
        <p>You've registered, but haven't completed your employee profile.</p>
        <Link to="/employee/complete-profile" className="btn btn-primary">
          Complete your profile
        </Link>
      </div>
    );
  }

  if (error) {
    return <Banner type="error">{error}</Banner>;
  }

  if (!profile) return null;

  const initials = (profile.name || profile.emailId || "?").trim().slice(0, 2).toUpperCase();

  return (
    <div className="stack">
      <div className="id-badge employee">
        <div className="id-badge-photo">{initials}</div>
        <div className="id-badge-info">
          <span className="id-badge-role">Employee record</span>
          <h1 className="id-badge-name">{profile.name || "Unnamed"}</h1>
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
          <strong>Your account is inactive.</strong> Activate it to apply for jobs and leave
          remarks.
          <div style={{ marginTop: 10 }}>
            <button className="btn btn-primary btn-sm" onClick={handleActivate} disabled={busy}>
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
            {profile.noticePeriod ? <span className="tag">{profile.noticePeriod}-day notice</span> : null}
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

      <div className="card">
        <h2 style={{ fontSize: "1.02rem" }}>Education</h2>
        {profile.education && profile.education.length > 0 ? (
          <div className="stack">
            {profile.education.map((ed) => (
              <div className="index-card" key={ed.educationId}>
                <div className="index-card-head">
                  <h3 className="index-card-title">{ed.degree || "Degree"}</h3>
                  <span className="index-card-meta">{ed.grade ?? ""}</span>
                </div>
                <p className="index-card-body">
                  {[ed.stream, ed.college, ed.university].filter(Boolean).join(" · ")}
                </p>
              </div>
            ))}
          </div>
        ) : (
          <p style={{ color: "var(--ink-soft)" }}>No education on file.</p>
        )}
      </div>

      <div className="card">
        <h2 style={{ fontSize: "1.02rem" }}>Experience</h2>
        {profile.experience && profile.experience.length > 0 ? (
          <div className="stack">
            {profile.experience.map((ex) => (
              <div className="index-card" key={ex.experienceID}>
                <div className="index-card-head">
                  <h3 className="index-card-title">{ex.designation || "Role"}</h3>
                  <span className="index-card-meta">{ex.years ? `${ex.years} yrs` : ""}</span>
                </div>
                <p className="index-card-body">
                  {[ex.companyName, ex.location].filter(Boolean).join(" · ")}
                </p>
                {ex.skills && ex.skills.length > 0 && (
                  <div style={{ marginTop: 8 }}>
                    {ex.skills.map((s) => (
                      <span className="tag" key={s}>
                        {s}
                      </span>
                    ))}
                  </div>
                )}
              </div>
            ))}
          </div>
        ) : (
          <p style={{ color: "var(--ink-soft)" }}>No experience on file.</p>
        )}
      </div>

      <div className="card card-tight" style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
        <div>
          <strong>Close your account</strong>
          <p style={{ margin: "4px 0 0", color: "var(--ink-soft)", fontSize: "0.85rem" }}>
            This deactivates your employee record.
          </p>
        </div>
        <button className="btn btn-danger" onClick={handleDelete} disabled={busy}>
          Delete account
        </button>
      </div>
    </div>
  );
}
