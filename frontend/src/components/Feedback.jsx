export function Banner({ type = "info", children }) {
  if (!children) return null;
  return <div className={`banner banner-${type}`}>{children}</div>;
}

export function Spinner({ label = "Loading…" }) {
  return (
    <div className="spinner-row">
      <span className="spinner" />
      {label}
    </div>
  );
}

export function EmptyState({ children }) {
  return <div className="empty-state">{children}</div>;
}
