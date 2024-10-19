package com.tsunamicxde.crud_cinema.service;

import com.tsunamicxde.crud_cinema.model.Movie;
import com.tsunamicxde.crud_cinema.model.Genre;
import com.tsunamicxde.crud_cinema.repository.MovieRepository;
import com.tsunamicxde.crud_cinema.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setName(movieDetails.getName());
        movie.setGenres(movieDetails.getGenres());
        movie.setYear(movieDetails.getYear());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public List<Movie> getMoviesByGenre(Long genreId) {
        return movieRepository.findByGenresId(genreId);
    }

    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
}
