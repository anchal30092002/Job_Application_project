import client from "./client";

export const getAllReviews = (companyId) => client.get(`/company/${companyId}/Review`);

export const getReviewById = (companyId, reviewId) =>
  client.get(`/company/${companyId}/Review/${reviewId}`);

export const createReview = (companyId, review) =>
  client.post(`/company/${companyId}/Review`, review);

export const updateReview = (companyId, reviewId, review) =>
  client.put(`/company/${companyId}/Review/${reviewId}`, review);

export const deleteReview = (companyId, reviewId) =>
  client.delete(`/company/${companyId}/Review/${reviewId}`);
