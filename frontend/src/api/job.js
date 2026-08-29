import client from "./client";

export const getAllJobs = () => client.get("/jobs");

export const createJob = (job) => client.post("/jobs", job);

export const getJobById = (id) => client.get(`/job/${id}`);

export const updateJob = (id, job) => client.put(`/job/${id}`, job);

export const deleteJob = (id) => client.delete(`/job/${id}`);
