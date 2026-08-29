import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { completeProfileEmployer } from "../../api/person";
import { extractErrorMessage } from "../../api/client";
import { useAuth } from "../../context/AuthContext";
import { Banner } from "../../components/Feedback";
import AddressFields from "../../components/AddressFields";

export default function EmployerCompleteProfile() {
  const { session } = useAuth();
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [phoneNo, setPhoneNo] = useState("");
  const [summary, setSummary] = useState("");
  const [panNo, setPanNo] = useState("");
  const [citizen, setCitizen] = useState("");
  const [passport, setPassport] = useState("");
  const [gender, setGender] = useState("");
  const [dob, setDob] = useState("");
  const [address, setAddress] = useState({
    houseno: "",
    street: "",
    city: "",
    state: "",
    country: "",
    pincode: "",
  });

  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSubmitting(true);

    const payload = {
      emailId: session.emailId,
      name,
      phoneNo: phoneNo ? Number(phoneNo) : null,
      summary,
      panNo,
      citizen,
      passport,
      gender,
      dob: dob || null,
      address: {
        houseno: address.houseno ? Number(address.houseno) : 0,
        street: address.street,
        city: address.city,
        state: address.state,
        country: address.country,
        pincode: address.pincode ? Number(address.pincode) : null,
      },
    };

    try {
      await completeProfileEmployer(payload);
      navigate("/employer/profile");
    } catch (err) {
      setError(extractErrorMessage(err, "Could not complete your profile."));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      <div className="page-header">
        <div>
          <span className="page-eyebrow">Employer dossier</span>
          <h1 style={{ fontSize: "1.8rem" }}>Complete your profile</h1>
        </div>
      </div>

      <Banner type="info">
        This form can only be submitted once — the backend treats a completed profile as
        final and won't overwrite it on a second submission.
      </Banner>
      <Banner type="error">{error}</Banner>

      <form onSubmit={handleSubmit} className="card stack">
        <div className="form-row">
          <div className="field">
            <label>Contact name</label>
            <input className="input" value={name} onChange={(e) => setName(e.target.value)} required />
          </div>
          <div className="field">
            <label>Phone number</label>
            <input className="input" type="tel" value={phoneNo} onChange={(e) => setPhoneNo(e.target.value)} />
          </div>
        </div>
        <div className="form-row">
          <div className="field">
            <label>Gender</label>
            <select className="select" value={gender} onChange={(e) => setGender(e.target.value)}>
              <option value="">Prefer not to say</option>
              <option value="Female">Female</option>
              <option value="Male">Male</option>
              <option value="Non-binary">Non-binary</option>
              <option value="Other">Other</option>
            </select>
          </div>
          <div className="field">
            <label>Date of birth</label>
            <input className="input" type="date" value={dob} onChange={(e) => setDob(e.target.value)} />
          </div>
        </div>
        <div className="field">
          <label>Summary</label>
          <textarea className="textarea" value={summary} onChange={(e) => setSummary(e.target.value)} placeholder="A short line about you or your hiring role." />
        </div>
        <div className="form-row">
          <div className="field">
            <label>PAN number</label>
            <input className="input" value={panNo} onChange={(e) => setPanNo(e.target.value)} />
          </div>
          <div className="field">
            <label>Citizenship</label>
            <input className="input" value={citizen} onChange={(e) => setCitizen(e.target.value)} />
          </div>
          <div className="field">
            <label>Passport number</label>
            <input className="input" value={passport} onChange={(e) => setPassport(e.target.value)} />
          </div>
        </div>

        <AddressFields address={address} onChange={setAddress} />

        <button className="btn btn-indigo" type="submit" disabled={submitting}>
          {submitting ? "Saving…" : "Complete profile"}
        </button>
      </form>
    </div>
  );
}
