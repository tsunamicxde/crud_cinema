package com.tsunamicxde.crud_cinema.dto;

import java.util.List;

public class ReviewDTO {
    private Long id;
    private String message;
    private Double rating;
    private ReviewerDTO reviewer;
    private Long movieId;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, String message, Double rating, ReviewerDTO reviewer, Long movieId) {
        this.id = id;
        this.message = message;
        this.rating = rating;
        this.reviewer = reviewer;
        this.movieId = movieId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public ReviewerDTO getReviewer() {
        return reviewer;
    }

    public void setReviewer(ReviewerDTO reviewer) {
        this.reviewer = reviewer;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
