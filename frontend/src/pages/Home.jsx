import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Home() {
  const { session } = useAuth();

  return (
    <div>
      <section className="hero">
        <div>
          <span className="hero-eyebrow">Careers ledger · built on a Spring Boot API</span>
          <h1>
            One <em>dossier</em> for every job you'll ever apply to — or hire for.
          </h1>
          <p className="lead">
            Dossier is a small, honest job board: employees keep one living profile with
            education and experience, employers keep a record of companies and openings,
            and every company carries the remarks left on it. No noise, just the ledger.
          </p>
          <div className="hero-actions">
            {session ? (
              <Link
                to={session.role === "EMPLOYEE" ? "/employee/profile" : "/employer/profile"}
                className="btn btn-primary"
              >
                Go to my profile
              </Link>
            ) : (
              <>
                <Link to="/register" className="btn btn-primary">
                  Open a dossier
                </Link>
                <Link to="/login" className="btn btn-ghost">
                  Log in
                </Link>
              </>
            )}
          </div>
        </div>

        <div className="hero-panel">
          <div className="hero-panel-id">RECORD · 000-EMPLOYEE</div>
          <div className="hero-panel-row">
            <span className="hero-panel-label">Track</span>
            <span>Employee</span>
          </div>
          <div className="hero-panel-row">
            <span className="hero-panel-label">Profile</span>
            <span>Education · Experience · Summary</span>
          </div>
          <div className="hero-panel-row">
            <span className="hero-panel-label">Can do</span>
            <span>Browse companies, leave remarks</span>
          </div>
          <div className="hero-panel-row">
            <span className="hero-panel-label">Track</span>
            <span>Employer</span>
          </div>
          <div className="hero-panel-row">
            <span className="hero-panel-label">Can do</span>
            <span>Open companies, post &amp; edit jobs</span>
          </div>
        </div>
      </section>

      <section>
        <div className="feature-grid">
          <div className="feature-card">
            <span className="num">01</span>
            <h3>Register once, two tracks</h3>
            <p>Sign up with just an email and password, then complete either an employee or employer dossier.</p>
          </div>
          <div className="feature-card">
            <span className="num">02</span>
            <h3>Full profile control</h3>
            <p>Fetch, activate, or close your account at any time — your data stays exactly as you left it.</p>
          </div>
          <div className="feature-card">
            <span className="num">03</span>
            <h3>Companies &amp; jobs</h3>
            <p>Employers open companies and list roles; employees browse and leave remarks on both.</p>
          </div>
        </div>
      </section>
    </div>
  );
}
