import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getAllCompanies } from "../../api/company";
import { extractErrorMessage } from "../../api/client";
import { Banner, Spinner, EmptyState } from "../../components/Feedback";

export default function BrowseCompanies() {
  const [companies, setCompanies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    (async () => {
      try {
        const res = await getAllCompanies();
        setCompanies(res.data);
      } catch (err) {
        if (err?.response?.status === 404) {
          setCompanies([]);
        } else {
          setError(extractErrorMessage(err, "Could not load companies."));
        }
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  return (
    <div>
      <div className="page-header">
        <div>
          <span className="page-eyebrow">Employee · Directory</span>
          <h1 style={{ fontSize: "1.8rem" }}>Browse companies</h1>
        </div>
      </div>

      <Banner type="error">{error}</Banner>

      {loading ? (
        <Spinner label="Loading companies…" />
      ) : companies.length === 0 ? (
        <EmptyState>No companies have been listed yet — check back soon.</EmptyState>
      ) : (
        <div className="grid-2">
          {companies.map((c) => (
            <Link to={`/employee/companies/${c.id}`} key={c.id} style={{ textDecoration: "none" }}>
              <div className="index-card">
                <div className="index-card-head">
                  <h3 className="index-card-title">{c.name}</h3>
                  <span className="index-card-meta">
                    {c.jobs?.length ? `${c.jobs.length} job${c.jobs.length === 1 ? "" : "s"}` : ""}
                  </span>
                </div>
                <p className="index-card-body">{c.description || "No description yet."}</p>
              </div>
            </Link>
          ))}
        </div>
      )}
    </div>
  );
}
