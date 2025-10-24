package org.oscar.gym.dtos.request.trainee;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

    @Setter
    @Getter
    public class TraineeRegistrationRequest{
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private String address;



}
