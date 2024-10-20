package com.tsunamicxde.crud_cinema.controller;

import com.tsunamicxde.crud_cinema.model.Reviewer;
import com.tsunamicxde.crud_cinema.response.ErrorResponse;
import com.tsunamicxde.crud_cinema.service.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviewers")
public class ReviewerController {

    @Autowired
    private ReviewerService reviewerService;

    @GetMapping
    public List<Reviewer> getAllReviewers() {
        return reviewerService.getAllReviewers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reviewer> getReviewerById(@PathVariable Long id) {
        Optional<Reviewer> reviewer = reviewerService.getReviewerById(id);
        return reviewer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createReviewer(@RequestBody Reviewer reviewer) {
        try {
            Reviewer createdReviewer = reviewerService.createReviewer(reviewer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReviewer);
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
    public ResponseEntity<Object> updateReviewer(@PathVariable Long id, @RequestBody Reviewer reviewerDetails) {
        try {
            Reviewer updatedReviewer = reviewerService.updateReviewer(id, reviewerDetails);
            return ResponseEntity.ok(updatedReviewer);
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
}