import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { registerUser } from "../api/person";
import { extractErrorMessage } from "../api/client";
import { useAuth } from "../context/AuthContext";
import { Banner } from "../components/Feedback";

export default function Register() {
  const [role, setRole] = useState("EMPLOYEE");
  const [emailId, setEmailId] = useState("");
  const [password, setPassword] = useState("");
  const [confirm, setConfirm] = useState("");
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (password !== confirm) {
      setError("Passwords don't match.");
      return;
    }
    if (!emailId || !password) {
      setError("Email and password are required.");
      return;
    }

    setSubmitting(true);
    try {
      await registerUser(emailId, password);
      login(emailId, password, role);
      navigate(role === "EMPLOYEE" ? "/employee/complete-profile" : "/employer/complete-profile");
    } catch (err) {
      setError(extractErrorMessage(err, "Could not register. That email may already exist."));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="app-main--narrow" style={{ margin: "0 auto" }}>
      <span className="page-eyebrow">Step 1 of 2</span>
      <h1 style={{ fontSize: "1.9rem" }}>Open a dossier</h1>
      <p style={{ color: "var(--ink-soft)", marginBottom: 24 }}>
        Choose which side of the ledger you're on. You can only complete one profile type per
        account, so pick the track you'll actually use.
      </p>

      <div className="card">
        <div className="role-pick">
          <button
            type="button"
            className={`role-card ${role === "EMPLOYEE" ? "selected" : ""}`}
            onClick={() => setRole("EMPLOYEE")}
          >
            <strong>Employee</strong>
            <span>Build a profile with education &amp; experience, browse companies, leave remarks.</span>
          </button>
          <button
            type="button"
            className={`role-card employer ${role === "EMPLOYER" ? "selected employer" : ""}`}
            onClick={() => setRole("EMPLOYER")}
          >
            <strong>Employer</strong>
            <span>Open companies, post jobs, and manage listings.</span>
          </button>
        </div>

        <Banner type="error">{error}</Banner>

        <form onSubmit={handleSubmit}>
          <div className="field">
            <label htmlFor="emailId">Email</label>
            <input
              id="emailId"
              className="input"
              type="email"
              autoComplete="email"
              value={emailId}
              onChange={(e) => setEmailId(e.target.value)}
              required
            />
          </div>
          <div className="form-row">
            <div className="field">
              <label htmlFor="password">Password</label>
              <input
                id="password"
                className="input"
                type="password"
                autoComplete="new-password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <div className="field">
              <label htmlFor="confirm">Confirm password</label>
              <input
                id="confirm"
                className="input"
                type="password"
                autoComplete="new-password"
                value={confirm}
                onChange={(e) => setConfirm(e.target.value)}
                required
              />
            </div>
          </div>
          <button className="btn btn-primary btn-block" type="submit" disabled={submitting}>
            {submitting ? "Creating account…" : "Create account"}
          </button>
        </form>
      </div>

      <p style={{ marginTop: 16, fontSize: "0.88rem", color: "var(--ink-soft)" }}>
        Already registered? <Link to="/login">Log in instead</Link>.
      </p>
    </div>
  );
}
