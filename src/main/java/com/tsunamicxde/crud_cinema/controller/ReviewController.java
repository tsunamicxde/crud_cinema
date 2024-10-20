package com.tsunamicxde.crud_cinema.controller;

import com.tsunamicxde.crud_cinema.dto.ReviewDTO;
import com.tsunamicxde.crud_cinema.model.Movie;
import com.tsunamicxde.crud_cinema.model.Review;
import com.tsunamicxde.crud_cinema.model.Reviewer;
import com.tsunamicxde.crud_cinema.response.ErrorResponse;
import com.tsunamicxde.crud_cinema.service.ReviewService;
import com.tsunamicxde.crud_cinema.service.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewerService reviewerService;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createReview(@RequestBody Review review) {
        try {
            Review createdReview = reviewService.createReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
        } catch (DataIntegrityViolationException e) {
            ErrorResponse errorResponse = new ErrorResponse("Data integrity violation - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReview(@PathVariable Long id, @RequestBody Review reviewDetails) {
        try {
            Review updatedReview = reviewService.updateReview(id, reviewDetails);
            return ResponseEntity.ok(updatedReview);
        } catch (DataIntegrityViolationException e) {
            ErrorResponse errorResponse = new ErrorResponse("Data integrity violation - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Review not found"));
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setMessage(review.getMessage());
        reviewDTO.setRating(review.getRating());

        Reviewer reviewer = review.getReviewer();
        if (reviewer != null) {
            reviewDTO.setReviewerName(reviewer.getName());
            reviewDTO.setReviewerSurname(reviewer.getSurname());
            reviewDTO.setReviewerInfo(reviewer.getInfo());
        } else {
            reviewDTO.setReviewerName(null);
            reviewDTO.setReviewerSurname(null);
            reviewDTO.setReviewerInfo(null);
        }

        reviewDTO.setMovieId(review.getMovie().getId());
        return reviewDTO;
    }

    private Review convertToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setMessage(reviewDTO.getMessage());
        review.setRating(reviewDTO.getRating());

        review.setMovie(new Movie(reviewDTO.getMovieId()));
        review.setReviewer(reviewerService.getReviewerByName(reviewDTO.getReviewerName()).orElse(null));
        return review;
    }
}