package org.oscar.gym.dtos;


import lombok.Getter;
import lombok.Setter;
import org.oscar.gym.enums.TypeTraining;


@Setter
@Getter
public class TrainerDTO {
    private String firstName;
    private String lastName;
    private TypeTraining specialization;

    public TrainerDTO() {
    }

    public TrainerDTO(String firstName, String lastName, TypeTraining specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }
}
