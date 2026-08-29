import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { completeProfileEmployee } from "../../api/person";
import { extractErrorMessage } from "../../api/client";
import { useAuth } from "../../context/AuthContext";
import { Banner } from "../../components/Feedback";
import AddressFields from "../../components/AddressFields";

const emptyEducation = () => ({
  degree: "",
  university: "",
  college: "",
  stream: "",
  grade: "",
  startDate: "",
  endDate: "",
});

const emptyExperience = () => ({
  companyName: "",
  years: "",
  startDate: "",
  endDate: "",
  salary: "",
  expectedSalary: "",
  designation: "",
  location: "",
  work: "",
  skills: "",
});

export default function EmployeeCompleteProfile() {
  const { session } = useAuth();
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [phoneNo, setPhoneNo] = useState("");
  const [noticePeriod, setNoticePeriod] = useState("");
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
  const [education, setEducation] = useState([emptyEducation()]);
  const [experience, setExperience] = useState([emptyExperience()]);

  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const updateEducation = (idx, field, value) => {
    setEducation((prev) => prev.map((e, i) => (i === idx ? { ...e, [field]: value } : e)));
  };
  const updateExperience = (idx, field, value) => {
    setExperience((prev) => prev.map((e, i) => (i === idx ? { ...e, [field]: value } : e)));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSubmitting(true);

    const payload = {
      emailId: session.emailId,
      name,
      phoneNo: phoneNo ? Number(phoneNo) : null,
      noticePeriod: noticePeriod ? Number(noticePeriod) : 0,
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
      education: education
        .filter((ed) => ed.degree || ed.university || ed.college)
        .map((ed) => ({
          ...ed,
          grade: ed.grade ? Number(ed.grade) : null,
          startDate: ed.startDate || null,
          endDate: ed.endDate || null,
        })),
      experience: experience
        .filter((ex) => ex.companyName || ex.designation)
        .map((ex) => ({
          ...ex,
          years: ex.years ? Number(ex.years) : 0,
          salary: ex.salary ? Number(ex.salary) : null,
          expectedSalary: ex.expectedSalary ? Number(ex.expectedSalary) : null,
          startDate: ex.startDate || null,
          endDate: ex.endDate || null,
          skills: ex.skills
            ? ex.skills.split(",").map((s) => s.trim()).filter(Boolean)
            : [],
        })),
    };

    try {
      await completeProfileEmployee(payload);
      navigate("/employee/profile");
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
          <span className="page-eyebrow">Employee dossier</span>
          <h1 style={{ fontSize: "1.8rem" }}>Complete your profile</h1>
        </div>
      </div>

      <Banner type="info">
        This form can only be submitted once — the backend treats a completed profile as
        final and won't overwrite it on a second submission.
      </Banner>
      <Banner type="error">{error}</Banner>

      <form onSubmit={handleSubmit} className="stack">
        <div className="card">
          <h2 style={{ fontSize: "1.05rem" }}>Basics</h2>
          <div className="form-row">
            <div className="field">
              <label>Full name</label>
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
            <textarea className="textarea" value={summary} onChange={(e) => setSummary(e.target.value)} placeholder="A short professional summary employers will see." />
          </div>
          <div className="form-row">
            <div className="field">
              <label>Notice period (days)</label>
              <input className="input" type="number" value={noticePeriod} onChange={(e) => setNoticePeriod(e.target.value)} />
            </div>
            <div className="field">
              <label>PAN number</label>
              <input className="input" value={panNo} onChange={(e) => setPanNo(e.target.value)} />
            </div>
          </div>
          <div className="form-row">
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
        </div>

        <div className="card">
          <div className="repeat-block-head">
            <h2 style={{ fontSize: "1.05rem", margin: 0 }}>Education</h2>
            <button type="button" className="btn btn-ghost btn-sm" onClick={() => setEducation((p) => [...p, emptyEducation()])}>
              + Add education
            </button>
          </div>
          {education.map((ed, idx) => (
            <div className="repeat-block" key={idx}>
              <div className="repeat-block-head">
                <span>Entry {idx + 1}</span>
                {education.length > 1 && (
                  <button type="button" className="btn btn-danger btn-sm" onClick={() => setEducation((p) => p.filter((_, i) => i !== idx))}>
                    Remove
                  </button>
                )}
              </div>
              <div className="form-row">
                <div className="field">
                  <label>Degree</label>
                  <input className="input" value={ed.degree} onChange={(e) => updateEducation(idx, "degree", e.target.value)} />
                </div>
                <div className="field">
                  <label>Stream</label>
                  <input className="input" value={ed.stream} onChange={(e) => updateEducation(idx, "stream", e.target.value)} />
                </div>
              </div>
              <div className="form-row">
                <div className="field">
                  <label>University</label>
                  <input className="input" value={ed.university} onChange={(e) => updateEducation(idx, "university", e.target.value)} />
                </div>
                <div className="field">
                  <label>College</label>
                  <input className="input" value={ed.college} onChange={(e) => updateEducation(idx, "college", e.target.value)} />
                </div>
              </div>
              <div className="form-row">
                <div className="field">
                  <label>Grade</label>
                  <input className="input" type="number" step="0.01" value={ed.grade} onChange={(e) => updateEducation(idx, "grade", e.target.value)} />
                </div>
                <div className="field">
                  <label>Start date</label>
                  <input className="input" type="date" value={ed.startDate} onChange={(e) => updateEducation(idx, "startDate", e.target.value)} />
                </div>
                <div className="field">
                  <label>End date</label>
                  <input className="input" type="date" value={ed.endDate} onChange={(e) => updateEducation(idx, "endDate", e.target.value)} />
                </div>
              </div>
            </div>
          ))}
        </div>

        <div className="card">
          <div className="repeat-block-head">
            <h2 style={{ fontSize: "1.05rem", margin: 0 }}>Experience</h2>
            <button type="button" className="btn btn-ghost btn-sm" onClick={() => setExperience((p) => [...p, emptyExperience()])}>
              + Add experience
            </button>
          </div>
          {experience.map((ex, idx) => (
            <div className="repeat-block" key={idx}>
              <div className="repeat-block-head">
                <span>Entry {idx + 1}</span>
                {experience.length > 1 && (
                  <button type="button" className="btn btn-danger btn-sm" onClick={() => setExperience((p) => p.filter((_, i) => i !== idx))}>
                    Remove
                  </button>
                )}
              </div>
              <div className="form-row">
                <div className="field">
                  <label>Company name</label>
                  <input className="input" value={ex.companyName} onChange={(e) => updateExperience(idx, "companyName", e.target.value)} />
                </div>
                <div className="field">
                  <label>Designation</label>
                  <input className="input" value={ex.designation} onChange={(e) => updateExperience(idx, "designation", e.target.value)} />
                </div>
              </div>
              <div className="form-row">
                <div className="field">
                  <label>Location</label>
                  <input className="input" value={ex.location} onChange={(e) => updateExperience(idx, "location", e.target.value)} />
                </div>
                <div className="field">
                  <label>Years</label>
                  <input className="input" type="number" value={ex.years} onChange={(e) => updateExperience(idx, "years", e.target.value)} />
                </div>
              </div>
              <div className="form-row">
                <div className="field">
                  <label>Start date</label>
                  <input className="input" type="date" value={ex.startDate} onChange={(e) => updateExperience(idx, "startDate", e.target.value)} />
                </div>
                <div className="field">
                  <label>End date</label>
                  <input className="input" type="date" value={ex.endDate} onChange={(e) => updateExperience(idx, "endDate", e.target.value)} />
                </div>
              </div>
              <div className="form-row">
                <div className="field">
                  <label>Current salary</label>
                  <input className="input" type="number" value={ex.salary} onChange={(e) => updateExperience(idx, "salary", e.target.value)} />
                </div>
                <div className="field">
                  <label>Expected salary</label>
                  <input className="input" type="number" value={ex.expectedSalary} onChange={(e) => updateExperience(idx, "expectedSalary", e.target.value)} />
                </div>
              </div>
              <div className="field">
                <label>Work summary</label>
                <textarea className="textarea" value={ex.work} onChange={(e) => updateExperience(idx, "work", e.target.value)} />
              </div>
              <div className="field">
                <label>Skills</label>
                <input className="input" value={ex.skills} onChange={(e) => updateExperience(idx, "skills", e.target.value)} placeholder="Comma separated, e.g. Java, Spring Boot, SQL" />
                <span className="field-hint">Separate skills with commas.</span>
              </div>
            </div>
          ))}
        </div>

        <button className="btn btn-primary" type="submit" disabled={submitting}>
          {submitting ? "Saving…" : "Complete profile"}
        </button>
      </form>
    </div>
  );
}
