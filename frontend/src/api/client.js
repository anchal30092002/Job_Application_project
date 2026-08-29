import axios from "axios";

// The Spring Boot backend's CorsConfig only allows requests from
// http://localhost:5173 (Vite's default dev port), so run this app with
// `npm run dev` on the default port, or update CorsConfig.java to match.
const BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

const client = axios.create({
  baseURL: BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Normalizes backend errors (which are sometimes plain strings, sometimes
// JSON) into a single readable message for the UI layer.
export function extractErrorMessage(error, fallback) {
  if (error?.response?.data) {
    const data = error.response.data;
    if (typeof data === "string") return data;
    if (data.message) return data.message;
  }
  if (error?.message) return error.message;
  return fallback || "Something went wrong. Please try again.";
}

export default client;
