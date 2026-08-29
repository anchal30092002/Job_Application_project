package com.Anchal.firstjobApp.Review;



import java.util.List;

public interface ReviewService {
    boolean createReview(Long companyId , Review review);
    List<Review> findAllReviews(Long companyId);
    Review FindReviewsById(Long companyId , Long reviewId);
    Boolean deleteReviewsById(Long companyId , Long reviewId);
    Review UpdateReviewById(Long companyId , Review reviews , Long reviewId);
}
