package com.tsunamicxde.crud_cinema.repository;

import com.tsunamicxde.crud_cinema.model.entities.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {
    Optional<Reviewer> findByName(String name);
}
