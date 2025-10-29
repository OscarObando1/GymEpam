package org.oscar.gym.dtos.request.trainer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.oscar.gym.enums.TypeTraining;

@Setter
@Getter
public class TrainerRegistrationRequest {
    @NotEmpty(message = "firstname is mandatory")
    private String firstName;
    @NotEmpty(message = "lastname is mandatory")
    private String lastName;
    @Pattern(
            regexp = "LIFTING|CARDIO|CROSSFIT",
            message = "only valid: LIFTING, CARDIO, CROSSFIT"
    )
    private String specialization;
}