package com.tsunamicxde.crud_cinema.service;

import com.tsunamicxde.crud_cinema.model.entities.Reviewer;
import com.tsunamicxde.crud_cinema.repository.ReviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewerService {

    @Autowired
    private ReviewerRepository reviewerRepository;

    public List<Reviewer> getAllReviewers() {
        return reviewerRepository.findAll();
    }

    public Optional<Reviewer> getReviewerById(Long id) {
        return reviewerRepository.findById(id);
    }

    public Optional<Reviewer> getReviewerByName(String name) {
        return reviewerRepository.findByName(name);
    }

    public Reviewer createReviewer(Reviewer reviewer) {
        return reviewerRepository.save(reviewer);
    }

    public Reviewer updateReviewer(Long id, Reviewer reviewerDetails) {
        Reviewer reviewer = reviewerRepository.findById(id).orElseThrow(() -> new RuntimeException("Reviewer not found"));
        reviewer.setName(reviewerDetails.getName());
        reviewer.setSurname(reviewerDetails.getSurname());
        reviewer.setReviews(reviewerDetails.getReviews());
        return reviewerRepository.save(reviewer);
    }

    public void deleteReviewer(Long id) {
        reviewerRepository.deleteById(id);
    }
}
