package org.oscar.gym.dtos.request.trainee;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TraineeUpdateRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    @NotNull
    private Boolean isActive;
}
