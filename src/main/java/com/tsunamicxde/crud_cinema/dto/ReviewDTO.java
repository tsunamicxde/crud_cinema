package com.tsunamicxde.crud_cinema.dto;

public class ReviewDTO {
    private Long id;
    private String message;
    private Double rating;
    private String reviewerName;
    private String reviewerSurname;
    private String reviewerInfo;
    private Long movieId;

    public ReviewDTO() {
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public ReviewDTO(Long id, String message, Double rating, String reviewerName, String reviewerSurname, String reviewerInfo, Long movieId) {
        this.id = id;
        this.message = message;
        this.rating = rating;
        this.reviewerName = reviewerName;
        this.reviewerSurname = reviewerSurname;
        this.reviewerInfo = reviewerInfo;
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

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewerSurname() {
        return reviewerSurname;
    }

    public void setReviewerSurname(String reviewerSurname) {
        this.reviewerSurname = reviewerSurname;
    }

    public String getReviewerInfo() {
        return reviewerInfo;
    }

    public void setReviewerInfo(String reviewerInfo) {
        this.reviewerInfo = reviewerInfo;
    }
}
