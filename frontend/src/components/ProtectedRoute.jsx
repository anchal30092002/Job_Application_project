import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function ProtectedRoute({ role, children }) {
  const { session } = useAuth();

  if (!session) {
    return <Navigate to="/login" replace />;
  }

  if (role && session.role !== role) {
    const fallback = session.role === "EMPLOYEE" ? "/employee/profile" : "/employer/profile";
    return <Navigate to={fallback} replace />;
  }

  return children;
}
