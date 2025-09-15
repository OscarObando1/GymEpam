package org.oscar.model;

import java.time.LocalDate;
import java.util.Objects;

public class Trainee extends User{
    private Long id;
    private String address;
    private LocalDate dateOfBirth;

    public Trainee() {
    }

    public Trainee(Long id, String address, LocalDate dateOfBirth) {
        this.id = id;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


}
