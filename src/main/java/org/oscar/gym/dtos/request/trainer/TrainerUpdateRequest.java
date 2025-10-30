package org.oscar.gym.dtos.request.trainer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerUpdateRequest {

    @NotEmpty(message = "username is mandatory")
    private String username;
    @NotEmpty(message = "firstname is mandatory")
    private String firstName;
    @NotEmpty(message = "lastname is mandatory")
    private String lastName;
    private String specialization;
    @NotNull(message = "is active or nor is mandatory")
    private Boolean isActive;
}
