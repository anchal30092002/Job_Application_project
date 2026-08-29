import { NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Navbar() {
  const { session, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <header className="navbar">
      <div className="navbar-inner">
        <NavLink to="/" className="brand">
          <span className="brand-mark">DL</span>
          Dossier
        </NavLink>

        <nav className="nav-links">
          {session?.role === "EMPLOYEE" && (
            <>
              <NavLink to="/employee/profile" className={({ isActive }) => `nav-link ${isActive ? "active" : ""}`}>
                My profile
              </NavLink>
              <NavLink to="/employee/companies" className={({ isActive }) => `nav-link ${isActive ? "active" : ""}`}>
                Browse companies
              </NavLink>
            </>
          )}
          {session?.role === "EMPLOYER" && (
            <>
              <NavLink to="/employer/profile" className={({ isActive }) => `nav-link ${isActive ? "active" : ""}`}>
                My profile
              </NavLink>
              <NavLink to="/employer/companies" className={({ isActive }) => `nav-link ${isActive ? "active" : ""}`}>
                My companies
              </NavLink>
              <NavLink to="/employer/jobs" className={({ isActive }) => `nav-link ${isActive ? "active" : ""}`}>
                Manage jobs
              </NavLink>
            </>
          )}
        </nav>

        <div className="nav-right">
          {session ? (
            <>
              <span className={`nav-role-chip ${session.role.toLowerCase()}`}>
                {session.role === "EMPLOYEE" ? "Employee" : "Employer"}
              </span>
              <span style={{ fontSize: "0.85rem", color: "var(--ink-soft)" }}>{session.emailId}</span>
              <button className="btn btn-ghost btn-sm" onClick={handleLogout}>
                Log out
              </button>
            </>
          ) : (
            <>
              <NavLink to="/login" className="btn btn-ghost btn-sm">
                Log in
              </NavLink>
              <NavLink to="/register" className="btn btn-primary btn-sm">
                Register
              </NavLink>
            </>
          )}
        </div>
      </div>
    </header>
  );
}
