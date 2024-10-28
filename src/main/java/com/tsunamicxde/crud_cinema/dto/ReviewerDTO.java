package com.tsunamicxde.crud_cinema.dto;

import java.util.List;

public class ReviewerDTO {
    private Long id;
    private String name;
    private String surname;
    private List<Long> reviewIds;

    public ReviewerDTO() {
    }

    public ReviewerDTO(Long id, String name, String surname, List<Long> reviewIds) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.reviewIds = reviewIds;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Long> getReviewIds() {
        return reviewIds;
    }

    public void setReviewIds(List<Long> reviewIds) {
        this.reviewIds = reviewIds;
    }
}
