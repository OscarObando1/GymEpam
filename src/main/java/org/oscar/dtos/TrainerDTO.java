package org.oscar.dtos;


import lombok.Getter;
import lombok.Setter;
import org.oscar.entity.TrainingType;
import org.oscar.enums.TypeTraining;

@Setter
@Getter
public class TrainerDTO {
    private String firstName;
    private String lastName;
    private String specialization;

    public TrainerDTO() {
    }

    public TrainerDTO(String firstName, String lastName, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }
}
