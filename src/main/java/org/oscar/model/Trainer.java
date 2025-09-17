package org.oscar.model;

import java.util.Objects;

public class Trainer extends User{
    private Long id;
    private TrainingType specialization;

    public Trainer() {
    }

    public Trainer(Long id, TrainingType specialization) {
        this.id = id;
        this.specialization = specialization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Trainer trainer = (Trainer) o;
        return Objects.equals(id, trainer.id) && specialization == trainer.specialization;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(specialization);
        return result;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", specialization=" + specialization +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
