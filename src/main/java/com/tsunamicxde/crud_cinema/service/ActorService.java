package com.tsunamicxde.crud_cinema.service;

import com.tsunamicxde.crud_cinema.model.Actor;
import com.tsunamicxde.crud_cinema.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public Optional<Actor> getActorById(Long id) {
        return actorRepository.findById(id);
    }

    public Actor createActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public Actor updateActor(Long id, Actor actorDetails) {
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new RuntimeException("Actor not found"));
        actor.setName(actorDetails.getName());
        actor.setSurname(actorDetails.getSurname());
        actor.setBirthDate(actorDetails.getBirthDate());
        actor.setBirthPlace(actorDetails.getBirthPlace());
        actor.setMovies(actorDetails.getMovies());
        return actorRepository.save(actor);
    }

    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }
}
