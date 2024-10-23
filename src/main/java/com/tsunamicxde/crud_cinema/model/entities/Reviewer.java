package com.tsunamicxde.crud_cinema.model.entities;

import com.fasterxml.jackson.annotation.*;
import com.tsunamicxde.crud_cinema.model.base.Person;
import com.tsunamicxde.crud_cinema.model.entities.Review;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reviewers")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Reviewer extends Person {

    @OneToMany(mappedBy = "reviewer")
    private Set<Review> reviews = new HashSet<>();

    public Reviewer() {
    }

    public Reviewer(String name, String surname) {
        super(name, surname);
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}
