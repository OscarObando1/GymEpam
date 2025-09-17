package org.oscar.dtos;

import org.oscar.model.TrainingType;

public class TrainerDTO {
    private String fisrtName;
    private String lastName;
    private TrainingType specialzation;

    public TrainerDTO() {
    }

    public TrainerDTO(String fisrtName, String lastName, TrainingType specialzation) {
        this.fisrtName = fisrtName;
        this.lastName = lastName;
        this.specialzation = specialzation;
    }

    public String getFisrtName() {
        return fisrtName;
    }

    public void setFisrtName(String fisrtName) {
        this.fisrtName = fisrtName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public TrainingType getSpecialzation() {
        return specialzation;
    }

    public void setSpecialzation(TrainingType specialzation) {
        this.specialzation = specialzation;
    }
}
