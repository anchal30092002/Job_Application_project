package com.Anchal.firstjobApp.Review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review , Long> {
    List<Review> findByCompanyId(Long companyId);// we are defining this method here because for review we need to find only that review which is related to a specific company not  all review. and this method will do our work. since this is a interface doesn't need to give its implementation spring boot will provide its implementation by own according to method name.
    boolean existsByCompanyId(Long companyId);
    Long countReviewByCompanyId(Long companyId);

    void deleteByReviewIdAndCompanyId(Long reviewId , Long companyId);
}
