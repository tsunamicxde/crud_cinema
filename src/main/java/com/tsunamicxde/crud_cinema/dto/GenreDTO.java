package com.tsunamicxde.crud_cinema.dto;

import java.util.List;

public class GenreDTO {
    private Long id;
    private String name;
    private String description;
    private List<Long> movieIds;

    public GenreDTO() {
    }

    public GenreDTO(Long id, String name, String description, List<Long> movieIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.movieIds = movieIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(List<Long> movieIds) {
        this.movieIds = movieIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
