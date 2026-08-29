package com.Anchal.firstjobApp.Review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company/{companyId}")
public class ReviewController {
    ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/Review")
    public ResponseEntity<String> createReview(@PathVariable Long companyId ,@RequestBody Review review)
    {
        Boolean newreview = reviewService.createReview(companyId , review);
        if(newreview)
        {
            return new ResponseEntity<>("Review created successfully" , HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("company is not present" , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/Review")
    public ResponseEntity<List<Review>> findAllReviews(@PathVariable Long companyId)
    {
        List<Review> reviews = reviewService.findAllReviews(companyId);
        if(reviews.size()==0)
        {
            return new ResponseEntity<>(reviews , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reviews , HttpStatus.FOUND);
    }

    @GetMapping("/Review/{reviewId}")
    public ResponseEntity<Review> FindReviewsById(@PathVariable Long companyId ,@PathVariable Long reviewId)
    {
        Review review = reviewService.FindReviewsById(companyId , reviewId);
        if(review==null)
        {
            return new ResponseEntity<>(review , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review , HttpStatus.FOUND);
    }

    @DeleteMapping("Review/{reviewId}")
    public ResponseEntity<String> deleteReviewsById(@PathVariable Long companyId , @PathVariable Long reviewId)
    {
        Boolean deleted = reviewService.deleteReviewsById(companyId , reviewId);
        if(deleted)
        {
            return new ResponseEntity<>("review deleted successfully!!!" , HttpStatus.OK);
        }
        return new ResponseEntity<>("review not found to be deleted" , HttpStatus.NOT_FOUND);
    }

    @PutMapping("/Review/{reviewId}")
    public ResponseEntity<Review> UpdateCompanyById(@PathVariable Long companyId ,@RequestBody Review reviews , @PathVariable Long reviewId)
    {
        Review review = reviewService.UpdateReviewById(companyId , reviews, reviewId);
        if(review==null)
        {
            return new ResponseEntity<>(review , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review , HttpStatus.OK);
    }
}
