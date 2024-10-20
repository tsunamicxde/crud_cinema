package com.tsunamicxde.crud_cinema.dto;

import java.util.List;
import java.util.Set;

public class MovieDTO {
    private Long id;
    private String name;
    private int year;
    private List<GenreDTO> genres;
    private List<ReviewDTO> reviews;
    private double averageRating;


    public MovieDTO() {
    }

    public MovieDTO(Long id, String name, int year, List<GenreDTO> genres, List<ReviewDTO> reviews) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.genres = genres;
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}