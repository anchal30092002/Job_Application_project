import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { loginEmployee, loginEmployer } from "../api/person";
import { extractErrorMessage } from "../api/client";
import { useAuth } from "../context/AuthContext";
import { Banner } from "../components/Feedback";

export default function Login() {
  const [role, setRole] = useState("EMPLOYEE");
  const [emailId, setEmailId] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSubmitting(true);
    try {
      const call = role === "EMPLOYEE" ? loginEmployee : loginEmployer;
      await call(emailId, password);
      login(emailId, password, role);
      navigate(role === "EMPLOYEE" ? "/employee/profile" : "/employer/profile");
    } catch (err) {
      setError(extractErrorMessage(err, "Couldn't log in with those details."));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="app-main--narrow" style={{ margin: "0 auto" }}>
      <span className="page-eyebrow">Welcome back</span>
      <h1 style={{ fontSize: "1.9rem" }}>Log in to your dossier</h1>

      <div className="card">
        <div className="role-pick">
          <button
            type="button"
            className={`role-card ${role === "EMPLOYEE" ? "selected" : ""}`}
            onClick={() => setRole("EMPLOYEE")}
          >
            <strong>Employee</strong>
            <span>Log in to your candidate profile.</span>
          </button>
          <button
            type="button"
            className={`role-card employer ${role === "EMPLOYER" ? "selected employer" : ""}`}
            onClick={() => setRole("EMPLOYER")}
          >
            <strong>Employer</strong>
            <span>Log in to manage companies &amp; jobs.</span>
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
          <div className="field">
            <label htmlFor="password">Password</label>
            <input
              id="password"
              className="input"
              type="password"
              autoComplete="current-password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button className="btn btn-primary btn-block" type="submit" disabled={submitting}>
            {submitting ? "Logging in…" : "Log in"}
          </button>
        </form>
      </div>

      <p style={{ marginTop: 16, fontSize: "0.88rem", color: "var(--ink-soft)" }}>
        New here? <Link to="/register">Open a dossier</Link>.
      </p>
    </div>
  );
}
