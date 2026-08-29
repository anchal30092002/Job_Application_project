package com.Anchal.firstjobApp.Review.impl;

import com.Anchal.firstjobApp.Company.Company;
import com.Anchal.firstjobApp.Company.CompanyRepository;
import com.Anchal.firstjobApp.Exception.ResourceNotFoundException;
import com.Anchal.firstjobApp.Review.Review;
import com.Anchal.firstjobApp.Review.ReviewRepository;
import com.Anchal.firstjobApp.Review.ReviewService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional //it is a annotation that tells spring about a transaction . needed for JPA . with this annotation if anything is gone wrong in a method present in this class then whole process get rollback. required for db operation like insert , delete , update . without this TransactionRequiredException this error may occur. It ensures that a set of database operations execute in a single transaction, maintaining data consistency by committing on success and rolling back on failure.Always use in Service layer, not Controller
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private CompanyRepository companyRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository , CompanyRepository companyRepository) {
        this.reviewRepository = reviewRepository;
        this.companyRepository= companyRepository;
    }

    @Override
    public boolean createReview(Long companyId , Review review) {
        Company company = companyRepository.findById(companyId).orElse(null);// here instead of companyRepository we can also use companyService because we have method defined in companyService also.
        if(company==null)
        {
            return false;
        }
        Long count = reviewRepository.countReviewByCompanyId(companyId);
        review.setReviewId(count+1);
        review.setCompany(company);
        reviewRepository.save(review);
        return true;
    }

    @Override
    public List<Review> findAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Review FindReviewsById(Long companyId ,Long reviewId) {
        List<Review> reviews=reviewRepository.findByCompanyId(companyId);
        if(reviews.isEmpty())
        {
            throw new ResourceNotFoundException("Company not found");
        }
        for(Review review:reviews)
        {
            if(review.getReviewId()== reviewId) {
                return review;
            }
        }
        return null;
    }

    @Override
    public Boolean deleteReviewsById(Long companyId ,Long reviewId) {
        List<Review> reviews=reviewRepository.findByCompanyId(companyId);
        if(reviews.size()==0)
        {
            throw new ResourceNotFoundException("Company not found");
        }
        for(Review review:reviews)
        {
            if(review.getReviewId()== reviewId) {
                reviewRepository.deleteByReviewIdAndCompanyId(review.getReviewId() , companyId);
                return true;
            }
        }
        return false;
    }

    @Override
    public Review UpdateReviewById(Long companyId ,Review reviews, Long reviewId) {
        List<Review> reviewsList=reviewRepository.findByCompanyId(companyId);
        Review updatedReview = null;
        if(reviewsList.isEmpty())
        {
            throw new ResourceNotFoundException("Company not found");
        }
        for(Review review:reviewsList)
        {
            if(review.getReviewId()== reviewId) {
                updatedReview =  review;
            }
        }
        if(updatedReview!=null)
        {
            updatedReview.setTitle(reviews.getTitle());
            updatedReview.setDescription(reviews.getDescription());
            updatedReview.setRating(reviews.getRating());
            reviewRepository.save(updatedReview);
            return updatedReview;
        }
        return null;

    }
}
