package com.tsunamicxde.crud_cinema.model.base;

import com.tsunamicxde.crud_cinema.model.base.BaseEntity;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class Person extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    public Person() {}

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
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
}
