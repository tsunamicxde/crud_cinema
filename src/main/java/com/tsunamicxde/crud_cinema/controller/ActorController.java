package com.tsunamicxde.crud_cinema.controller;

import com.tsunamicxde.crud_cinema.dto.ActorDTO;
import com.tsunamicxde.crud_cinema.model.entities.Actor;
import com.tsunamicxde.crud_cinema.model.entities.Movie;
import com.tsunamicxde.crud_cinema.response.ErrorResponse;
import com.tsunamicxde.crud_cinema.service.ActorService;
import com.tsunamicxde.crud_cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ActorService actorService;

    @GetMapping
    public List<ActorDTO> getAllActors() {
        return actorService.getAllActors().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDTO> getActorById(@PathVariable Long id) {
        Optional<Actor> actor = actorService.getActorById(id);
        return actor.map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createActor(@RequestBody ActorDTO actorDTO) {
        try {
            Actor createdActor = actorService.createActor(convertToEntity(actorDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdActor);
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
    public ResponseEntity<Object> updateActor(@PathVariable Long id, @RequestBody ActorDTO actorDTO) {
        try {
            Actor updatedActor = actorService.updateActor(id, convertToEntity(actorDTO));
            return ResponseEntity.ok(updatedActor);
        } catch (DataIntegrityViolationException e) {
            ErrorResponse errorResponse = new ErrorResponse("Data integrity violation - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Actor not found"));
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return ResponseEntity.noContent().build();
    }

    private ActorDTO convertToDTO(Actor actor) {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(actor.getId());
        actorDTO.setName(actor.getName());
        actorDTO.setSurname(actor.getSurname());
        actorDTO.setBirthDate(actor.getBirthDate());
        actorDTO.setBirthPlace(actor.getBirthPlace());

        List<Long> movieIds = actor.getMovies().stream()
                .map(Movie::getId)
                .collect(Collectors.toList());
        actorDTO.setMovieIds(movieIds);
        return actorDTO;
    }

    private Actor convertToEntity(ActorDTO actorDTO) {
        Actor actor = new Actor();
        actor.setId(actorDTO.getId());
        actor.setName(actorDTO.getName());
        actor.setSurname(actorDTO.getSurname());
        actor.setBirthDate(actorDTO.getBirthDate());
        actor.setBirthPlace(actorDTO.getBirthPlace());
        return actor;
    }
}