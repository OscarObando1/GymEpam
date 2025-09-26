package org.oscar.dtos;

import org.oscar.enums.TrainingType;

public class TrainerDTO {
    private String firstName;
    private String lastName;
    private TrainingType specialization;

    public TrainerDTO() {
    }

    public TrainerDTO(String fisrtName, String lastName, TrainingType specialization) {
        this.firstName = fisrtName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }
}
