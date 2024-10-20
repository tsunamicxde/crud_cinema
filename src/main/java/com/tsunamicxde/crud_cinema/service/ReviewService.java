package com.tsunamicxde.crud_cinema.service;

import com.tsunamicxde.crud_cinema.model.Review;
import com.tsunamicxde.crud_cinema.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, Review reviewDetails) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
        review.setMessage(reviewDetails.getMessage());
        review.setRating(reviewDetails.getRating());
        review.setDateTime(LocalDateTime.now());
        review.setReviewer(reviewDetails.getReviewer());
        review.setMovie(reviewDetails.getMovie());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
