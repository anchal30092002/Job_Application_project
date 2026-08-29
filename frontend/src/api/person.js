import client from "./client";

// --- Registration & login (UserCreds) ---

export const registerUser = (emailId, password) =>
  client.post("/RegisterUser", { emailId, password });

export const loginEmployee = (emailId, password) =>
  client.get(`/loginEmployee/${encodeURIComponent(emailId)}/${encodeURIComponent(password)}`);

export const loginEmployer = (emailId, password) =>
  client.get(`/loginEmployer/${encodeURIComponent(emailId)}/${encodeURIComponent(password)}`);

// --- Profile completion (one-time; backend rejects a second submission) ---

export const completeProfileEmployee = (employee) =>
  client.post("/completeProfileEmployee", employee);

export const completeProfileEmployer = (employer) =>
  client.post("/completeProfileEmployer", employer);

// --- Fetch profile ---

export const getEmployeeProfile = (emailId, password) =>
  client.get(`/myProfileEmployee/${encodeURIComponent(emailId)}/${encodeURIComponent(password)}`);

export const getEmployerProfile = (emailId, password) =>
  client.get(`/myProfileEmployer/${encodeURIComponent(emailId)}/${encodeURIComponent(password)}`);

// --- Activate account ---

export const activateEmployee = (emailId, password) =>
  client.patch(`/activateEmployee/${encodeURIComponent(emailId)}/${encodeURIComponent(password)}`);

export const activateEmployer = (emailId, password) =>
  client.patch(`/activateEmployer/${encodeURIComponent(emailId)}/${encodeURIComponent(password)}`);

// --- Delete (deactivate) account ---

export const deleteEmployee = (emailId, password) =>
  client.delete(`/deleteEmployee/${encodeURIComponent(emailId)}/${encodeURIComponent(password)}`);

export const deleteEmployer = (emailId, password) =>
  client.delete(`/deleteEmployer/${encodeURIComponent(emailId)}/${encodeURIComponent(password)}`);
