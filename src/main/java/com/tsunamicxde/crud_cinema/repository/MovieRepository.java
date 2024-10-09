package com.tsunamicxde.crud_cinema.repository;

import com.tsunamicxde.crud_cinema.model.Movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> findAll();
    Movie findById(int id);
    int save(Movie movie);
    int update(Movie movie);
    int deleteById(int id);
}
