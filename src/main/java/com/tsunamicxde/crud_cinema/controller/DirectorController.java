package com.tsunamicxde.crud_cinema.controller;

import com.tsunamicxde.crud_cinema.dto.DirectorDTO;
import com.tsunamicxde.crud_cinema.model.Director;
import com.tsunamicxde.crud_cinema.model.Movie;
import com.tsunamicxde.crud_cinema.response.ErrorResponse;
import com.tsunamicxde.crud_cinema.service.DirectorService;
import com.tsunamicxde.crud_cinema.service.MovieService;
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
@RequestMapping("/api/directors")
public class DirectorController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private DirectorService directorService;

    @GetMapping
    public List<Director> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Director> getDirectorById(@PathVariable Long id) {
        Optional<Director> director = directorService.getDirectorById(id);
        return director.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createDirector(@RequestBody Director director) {
        try {
            Director createdDirector = directorService.createDirector(director);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDirector);
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
    public ResponseEntity<Object> updateDirector(@PathVariable Long id, @RequestBody Director directorDetails) {
        try {
            Director updatedDirector = directorService.updateDirector(id, directorDetails);
            return ResponseEntity.ok(updatedDirector);
        } catch (DataIntegrityViolationException e) {
            ErrorResponse errorResponse = new ErrorResponse("Data integrity violation - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Director not found"));
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();
    }

    private DirectorDTO convertToDTO(Director director) {
        DirectorDTO directorDTO = new DirectorDTO();
        directorDTO.setId(director.getId());
        directorDTO.setName(director.getName());
        directorDTO.setSurname(director.getSurname());
        directorDTO.setBirthDate(director.getBirthDate());
        directorDTO.setBirthPlace(director.getBirthPlace());

        List<Long> movieIds = director.getMovies().stream()
                .map(Movie::getId)
                .collect(Collectors.toList());
        directorDTO.setMovieIds(movieIds);
        return directorDTO;
    }

    private Director convertToEntity(DirectorDTO directorDTO) {
        Director director = new Director();
        director.setId(directorDTO.getId());
        director.setName(directorDTO.getName());
        director.setSurname(directorDTO.getSurname());
        director.setBirthDate(directorDTO.getBirthDate());
        director.setBirthPlace(directorDTO.getBirthPlace());

        List<Long> movieIds = directorDTO.getMovieIds();
        Set<Movie> movies = movieIds.stream()
                .map(movieId -> movieService.getMovieById(movieId).orElse(null))
                .filter(currentMovie -> currentMovie != null)
                .collect(Collectors.toSet());

        director.setMovies(movies);
        return director;
    }
}