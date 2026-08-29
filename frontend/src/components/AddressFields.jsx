export default function AddressFields({ address, onChange }) {
  const set = (field) => (e) => {
    const value = e.target.value;
    onChange({ ...address, [field]: field === "houseno" || field === "pincode" ? value : value });
  };

  return (
    <fieldset className="fieldset">
      <legend>Address</legend>
      <div className="form-row">
        <div className="field">
          <label>House no.</label>
          <input className="input" type="number" value={address.houseno} onChange={set("houseno")} />
        </div>
        <div className="field">
          <label>Street</label>
          <input className="input" value={address.street} onChange={set("street")} />
        </div>
      </div>
      <div className="form-row">
        <div className="field">
          <label>City</label>
          <input className="input" value={address.city} onChange={set("city")} />
        </div>
        <div className="field">
          <label>State</label>
          <input className="input" value={address.state} onChange={set("state")} />
        </div>
      </div>
      <div className="form-row">
        <div className="field">
          <label>Country</label>
          <input className="input" value={address.country} onChange={set("country")} />
        </div>
        <div className="field">
          <label>Pincode</label>
          <input className="input" type="number" value={address.pincode} onChange={set("pincode")} />
        </div>
      </div>
    </fieldset>
  );
}
