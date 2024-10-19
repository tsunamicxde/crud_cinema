package com.tsunamicxde.crud_cinema.controller;

import com.tsunamicxde.crud_cinema.model.Genre;
import com.tsunamicxde.crud_cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Genre> getAllGenres() {
        return movieService.getAllGenres();
    }

    @PostMapping
    public Genre createGenre(@RequestBody Genre genre) {
        return movieService.createGenre(genre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @RequestBody Genre genreDetails) {
        Genre genre = movieService.getAllGenres()
                .stream()
                .filter(g -> g.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (genre == null) {
            return ResponseEntity.notFound().build();
        }

        genre.setName(genreDetails.getName());
        Genre updatedGenre = movieService.createGenre(genre); // Используем createGenre, чтобы сохранить изменения
        return ResponseEntity.ok(updatedGenre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        Genre genre = movieService.getAllGenres()
                .stream()
                .filter(g -> g.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (genre == null) {
            return ResponseEntity.notFound().build();
        }

        // Если у вас есть зависимость между Movie и Genre, убедитесь, что вы сначала удаляете связи
        movieService.deleteGenre(id); // Предположим, что у вас есть метод deleteGenre в MovieService
        return ResponseEntity.noContent().build();
    }
}
