package com.tsunamicxde.crud_cinema.controller;

import com.tsunamicxde.crud_cinema.dto.GenreDTO;
import com.tsunamicxde.crud_cinema.dto.MovieDTO;
import com.tsunamicxde.crud_cinema.dto.ReviewDTO;
import com.tsunamicxde.crud_cinema.model.Genre;
import com.tsunamicxde.crud_cinema.model.Movie;
import com.tsunamicxde.crud_cinema.model.Review;
import com.tsunamicxde.crud_cinema.model.Reviewer;
import com.tsunamicxde.crud_cinema.response.ErrorResponse;
import com.tsunamicxde.crud_cinema.service.GenreService;
import com.tsunamicxde.crud_cinema.service.MovieService;
import com.tsunamicxde.crud_cinema.service.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ReviewerService reviewerService;

    @GetMapping
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        Optional<Movie> movie = movieService.getMovieById(id);
        return movie.map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createMovie(@RequestBody MovieDTO movieDTO) {
        try {
            Movie createdMovie = movieService.createMovie(convertToEntity(movieDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdMovie));
        } catch (DataIntegrityViolationException e) {
            ErrorResponse errorResponse = new ErrorResponse("Data integrity violation - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        try {
            Movie updatedMovie = movieService.updateMovie(id, convertToEntity(movieDTO));
            return ResponseEntity.ok(convertToDTO(updatedMovie));
        } catch (DataIntegrityViolationException e) {
            ErrorResponse errorResponse = new ErrorResponse("Data integrity violation - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Movie or genre not found"));
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    protected MovieDTO convertToDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setName(movie.getName());
        movieDTO.setYear(movie.getYear());

        List<GenreDTO> genreDTOs = movie.getGenres().stream()
                .map(genre -> {
                    GenreDTO genreDTO = new GenreDTO();
                    genreDTO.setId(genre.getId());
                    genreDTO.setName(genre.getName());
                    genreDTO.setDescription(genre.getDescription());

                    List<Long> movieIds = genre.getMovies().stream()
                            .map(Movie::getId)
                            .collect(Collectors.toList());
                    genreDTO.setMovieIds(movieIds);

                    return genreDTO;
                })
                .collect(Collectors.toList());
        movieDTO.setGenres(genreDTOs);

        List<ReviewDTO> reviewDTOs = movie.getReviews().stream()
                .map(review -> {
                    ReviewDTO reviewDTO = new ReviewDTO();
                    reviewDTO.setId(review.getId());
                    reviewDTO.setMessage(review.getMessage());
                    reviewDTO.setRating(review.getRating());

                    Reviewer reviewer = review.getReviewer();
                    if (reviewer != null) {
                        reviewDTO.setReviewerName(reviewer.getName());
                        reviewDTO.setReviewerSurname(reviewer.getSurname());
                        reviewDTO.setReviewerInfo(reviewer.getInfo());
                    }

                    reviewDTO.setMovieId(movie.getId());
                    return reviewDTO;
                })
                .collect(Collectors.toList());
        movieDTO.setReviews(reviewDTOs);

        double averageRating = movie.getAverageRating();
        movieDTO.setAverageRating(averageRating);

        return movieDTO;
    }

    private Movie convertToEntity(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setId(movieDTO.getId());
        movie.setName(movieDTO.getName());
        movie.setYear(movieDTO.getYear());

        if (movieDTO.getGenres() != null) {
            Set<Genre> genres = movieDTO.getGenres().stream()
                    .map(genreDTO -> genreService.getGenreById(genreDTO.getId()).orElse(null))
                    .filter(genre -> genre != null)
                    .collect(Collectors.toSet());
            movie.setGenres(genres);
        }

        if (movieDTO.getReviews() != null) {
            Set<Review> reviews = movieDTO.getReviews().stream()
                    .map(reviewDTO -> {
                        Review review = new Review();
                        review.setId(reviewDTO.getId());
                        review.setMessage(reviewDTO.getMessage());
                        review.setRating(reviewDTO.getRating());

                        Reviewer reviewer = reviewerService.getReviewerByName(reviewDTO.getReviewerName()).orElse(null);
                        review.setReviewer(reviewer);
                        review.setMovie(movie);

                        return review;
                    })
                    .filter(review -> review.getReviewer() != null)
                    .collect(Collectors.toSet());
            movie.setReviews(reviews);
        }
        return movie;
    }
}
