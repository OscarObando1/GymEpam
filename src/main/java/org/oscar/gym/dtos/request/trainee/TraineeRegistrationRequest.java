package org.oscar.gym.dtos.request.trainee;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

    @Setter
    @Getter
    public class TraineeRegistrationRequest{
        @NotEmpty(message = "firstname is mandatory")
        private String firstName;
        @NotEmpty(message = "lastname is mandatory")
        private String lastName;

        private LocalDate dateOfBirth;

        private String address;



}
