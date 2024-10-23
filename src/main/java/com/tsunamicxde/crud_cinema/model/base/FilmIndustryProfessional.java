package com.tsunamicxde.crud_cinema.model.base;

import jakarta.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class FilmIndustryProfessional extends Person {

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String birthPlace;

    public FilmIndustryProfessional() {}

    public FilmIndustryProfessional(String name, String surname, LocalDate birthDate, String birthPlace) {
        super(name, surname);
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
}
