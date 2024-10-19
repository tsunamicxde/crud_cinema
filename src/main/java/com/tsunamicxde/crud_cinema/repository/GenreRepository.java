package com.tsunamicxde.crud_cinema.repository;

import com.tsunamicxde.crud_cinema.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}