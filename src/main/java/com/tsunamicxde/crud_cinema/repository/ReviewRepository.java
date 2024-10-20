package com.tsunamicxde.crud_cinema.repository;

import com.tsunamicxde.crud_cinema.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
