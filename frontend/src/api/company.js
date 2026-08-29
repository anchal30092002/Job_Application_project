import client from "./client";

export const createCompany = (company) => client.post("/Company", company);

export const getAllCompanies = () => client.get("/Company");

export const getCompanyById = (id) => client.get(`/Company/${id}`);

export const updateCompany = (id, company) => client.put(`/Company/${id}`, company);

export const deleteCompany = (id) => client.delete(`/Company/${id}`);
