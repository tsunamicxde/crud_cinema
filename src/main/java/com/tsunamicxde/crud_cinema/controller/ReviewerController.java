package com.tsunamicxde.crud_cinema.controller;

import com.tsunamicxde.crud_cinema.dto.MovieDTO;
import com.tsunamicxde.crud_cinema.dto.ReviewDTO;
import com.tsunamicxde.crud_cinema.dto.ReviewerDTO;
import com.tsunamicxde.crud_cinema.model.entities.Genre;
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
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviewers")
public class ReviewerController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewerService reviewerService;

    @GetMapping
    public List<ReviewerDTO> getAllReviewers() {
        return reviewerService.getAllReviewers().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewerDTO> getReviewerById(@PathVariable Long id) {
        Optional<Reviewer> reviewer = reviewerService.getReviewerById(id);
        return reviewer.map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createReviewer(@RequestBody ReviewerDTO reviewerDTO) {
        try {
            Reviewer createdReviewer = reviewerService.createReviewer(convertToEntity(reviewerDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdReviewer));
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
    public ResponseEntity<Object> updateReviewer(@PathVariable Long id, @RequestBody ReviewerDTO reviewerDTO) {
        try {
            Reviewer updatedReviewer = reviewerService.updateReviewer(id, convertToEntity(reviewerDTO));
            return ResponseEntity.ok(convertToDTO(updatedReviewer));
        } catch (DataIntegrityViolationException e) {
            ErrorResponse errorResponse = new ErrorResponse("Data integrity violation - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Reviewer not found"));
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewer(@PathVariable Long id) {
        reviewerService.deleteReviewer(id);
        return ResponseEntity.noContent().build();
    }

    private ReviewerDTO convertToDTO(Reviewer reviewer) {
        ReviewerDTO reviewerDTO = new ReviewerDTO();
        reviewerDTO.setId(reviewer.getId());
        reviewerDTO.setName(reviewer.getName());
        reviewerDTO.setSurname(reviewer.getSurname());
        List<Long> reviewIds = reviewer.getReviews().stream()
                .map(Review::getId)
                .collect(Collectors.toList());
        reviewerDTO.setReviewIds(reviewIds);
        return reviewerDTO;
    }

    private Reviewer convertToEntity(ReviewerDTO reviewerDTO) {
        Reviewer reviewer = new Reviewer();
        reviewer.setId(reviewerDTO.getId());
        reviewer.setName(reviewerDTO.getName());
        reviewer.setSurname(reviewerDTO.getSurname());
        return reviewer;
    }
}