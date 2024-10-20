package com.tsunamicxde.crud_cinema.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @Column(nullable = false)
    private int year;

    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties("movie")
    private Set<Review> reviews = new HashSet<>();

    @Transient
    private double averageRating;

    public Movie() {
    }

    public Movie(Long id) {
        this.id = id;
    }

    public Movie(String name, int year) {
        this.name = name;
        this.year = year;
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

    public Set<Review> getReviews() {
        return reviews;
    }

    public double getAverageRating() {
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
