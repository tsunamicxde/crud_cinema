package com.tsunamicxde.crud_cinema.controller;

import com.tsunamicxde.crud_cinema.model.Movie;
import com.tsunamicxde.crud_cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable int id) {
        return movieRepository.findById(id);
    }

    @PostMapping
    public String createMovie(@RequestBody Movie movie) {
        movieRepository.save(movie);
        return "Movie created successfully";
    }

    @PutMapping("/{id}")
    public String updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        movie.setId(id);
        movieRepository.update(movie);
        return "Movie updated successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable int id) {
        movieRepository.deleteById(id);
        return "Movie deleted successfully";
    }
}
