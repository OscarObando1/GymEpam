package org.oscar.dtos;


import lombok.Getter;
import lombok.Setter;
import org.oscar.entity.TrainingType;

@Setter
@Getter
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

}
