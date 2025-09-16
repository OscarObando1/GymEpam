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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Trainee trainee = (Trainee) o;
        return Objects.equals(id, trainee.id) && Objects.equals(address, trainee.address) && Objects.equals(dateOfBirth, trainee.dateOfBirth);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(address);
        result = 31 * result + Objects.hashCode(dateOfBirth);
        return result;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
