package com.tsunamicxde.crud_cinema.service;

import com.tsunamicxde.crud_cinema.model.Director;
import com.tsunamicxde.crud_cinema.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    public Optional<Director> getDirectorById(Long id) {
        return directorRepository.findById(id);
    }

    public Director createDirector(Director director) {
        return directorRepository.save(director);
    }

    public Director updateDirector(Long id, Director directorDetails) {
        Director director = directorRepository.findById(id).orElseThrow(() -> new RuntimeException("Director not found"));
        director.setName(directorDetails.getName());
        director.setSurname(directorDetails.getSurname());
        director.setBirthDate(directorDetails.getBirthDate());
        director.setBirthPlace(directorDetails.getBirthPlace());
        director.setMovies(directorDetails.getMovies());
        return directorRepository.save(director);
    }

    public void deleteDirector(Long id) {
        directorRepository.deleteById(id);
    }
}
