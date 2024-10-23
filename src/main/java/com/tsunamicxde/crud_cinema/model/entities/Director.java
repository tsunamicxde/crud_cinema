package com.tsunamicxde.crud_cinema.model.entities;

import com.fasterxml.jackson.annotation.*;
import com.tsunamicxde.crud_cinema.model.base.FilmIndustryProfessional;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "directors")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Director extends FilmIndustryProfessional {

    @OneToMany(mappedBy = "director")
    private Set<Movie> movies = new HashSet<>();

    public Director() {}

    public Director(String name, String surname, LocalDate birthDate, String birthPlace) {
        super(name, surname, birthDate, birthPlace);
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
