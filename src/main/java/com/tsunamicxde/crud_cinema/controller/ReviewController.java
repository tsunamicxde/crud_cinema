package com.tsunamicxde.crud_cinema.controller;

import com.tsunamicxde.crud_cinema.dto.MovieDTO;
import com.tsunamicxde.crud_cinema.dto.ReviewDTO;
import com.tsunamicxde.crud_cinema.dto.ReviewerDTO;
import com.tsunamicxde.crud_cinema.model.entities.Director;
import com.tsunamicxde.crud_cinema.model.entities.Movie;
import com.tsunamicxde.crud_cinema.model.entities.Review;
import com.tsunamicxde.crud_cinema.model.entities.Reviewer;
import com.tsunamicxde.crud_cinema.response.ErrorResponse;
import com.tsunamicxde.crud_cinema.service.MovieService;
import com.tsunamicxde.crud_cinema.service.ReviewService;
import com.tsunamicxde.crud_cinema.service.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewerService reviewerService;

    @GetMapping
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            Review createdReview = reviewService.createReview(convertToEntity(reviewDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdReview));
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
    public ResponseEntity<Object> updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        try {
            Review updatedReview = reviewService.updateReview(id, convertToEntity(reviewDTO));
            return ResponseEntity.ok(convertToDTO(updatedReview));
        } catch (DataIntegrityViolationException e) {
            ErrorResponse errorResponse = new ErrorResponse("Data integrity violation - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Movie or genre not found"));
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
            ReviewerDTO reviewerDTO = new ReviewerDTO();
            reviewerDTO.setId(reviewDTO.getId());
            reviewerDTO.setName(reviewer.getName());
            reviewerDTO.setSurname(reviewer.getSurname());
            List<Long> reviewIds = reviewer.getReviews().stream()
                    .map(Review::getId)
                    .collect(Collectors.toList());
            reviewerDTO.setReviewIds(reviewIds);
            reviewDTO.setReviewer(reviewerDTO);
        } else {
            reviewDTO.setReviewer(null);
        }

        reviewDTO.setMovieId(review.getMovie().getId());
        return reviewDTO;
    }

    private Review convertToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setMessage(reviewDTO.getMessage());
        review.setRating(reviewDTO.getRating());

        Optional<Reviewer> reviewerById = reviewerService.getReviewerById(reviewDTO.getReviewer().getId());
        Reviewer reviewer = reviewerById.orElseThrow(() -> new RuntimeException("Reviewer not found"));
        review.setReviewer(reviewer);

        Optional<Movie> movieById = movieService.getMovieById(reviewDTO.getMovieId());
        Movie movie = movieById.orElseThrow(() -> new RuntimeException("Movie not found"));
        review.setMovie(movie);
        return review;
    }
}