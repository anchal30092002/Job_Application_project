import { Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import ProtectedRoute from "./components/ProtectedRoute";

import Home from "./pages/Home";
import Register from "./pages/Register";
import Login from "./pages/Login";

import EmployeeCompleteProfile from "./pages/employee/CompleteProfile";
import EmployeeProfile from "./pages/employee/Profile";
import BrowseCompanies from "./pages/employee/BrowseCompanies";
import CompanyDetail from "./pages/employee/CompanyDetail";

import EmployerCompleteProfile from "./pages/employer/CompleteProfile";
import EmployerProfile from "./pages/employer/Profile";
import EmployerCompanies from "./pages/employer/Companies";
import EmployerJobs from "./pages/employer/Jobs";

export default function App() {
  return (
    <div className="app-shell">
      <Navbar />
      <main className="app-main">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />

          <Route
            path="/employee/complete-profile"
            element={
              <ProtectedRoute role="EMPLOYEE">
                <EmployeeCompleteProfile />
              </ProtectedRoute>
            }
          />
          <Route
            path="/employee/profile"
            element={
              <ProtectedRoute role="EMPLOYEE">
                <EmployeeProfile />
              </ProtectedRoute>
            }
          />
          <Route
            path="/employee/companies"
            element={
              <ProtectedRoute role="EMPLOYEE">
                <BrowseCompanies />
              </ProtectedRoute>
            }
          />
          <Route
            path="/employee/companies/:id"
            element={
              <ProtectedRoute role="EMPLOYEE">
                <CompanyDetail />
              </ProtectedRoute>
            }
          />

          <Route
            path="/employer/complete-profile"
            element={
              <ProtectedRoute role="EMPLOYER">
                <EmployerCompleteProfile />
              </ProtectedRoute>
            }
          />
          <Route
            path="/employer/profile"
            element={
              <ProtectedRoute role="EMPLOYER">
                <EmployerProfile />
              </ProtectedRoute>
            }
          />
          <Route
            path="/employer/companies"
            element={
              <ProtectedRoute role="EMPLOYER">
                <EmployerCompanies />
              </ProtectedRoute>
            }
          />
          <Route
            path="/employer/jobs"
            element={
              <ProtectedRoute role="EMPLOYER">
                <EmployerJobs />
              </ProtectedRoute>
            }
          />

          <Route path="*" element={<Home />} />
        </Routes>
      </main>
      <p className="footer-note">Dossier · a personal project on top of a Spring Boot API</p>
    </div>
  );
}
