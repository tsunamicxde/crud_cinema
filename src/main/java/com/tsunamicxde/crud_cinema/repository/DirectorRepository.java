package com.tsunamicxde.crud_cinema.repository;

import com.tsunamicxde.crud_cinema.model.entities.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

}