package org.oscar.gym.dtos.request.trainer;

import lombok.Getter;
import lombok.Setter;
import org.oscar.gym.enums.TypeTraining;

@Setter
@Getter
public class TrainerRegistrationRequest {
    private String firstName;
    private String lastName;
    private TypeTraining specialization;
}