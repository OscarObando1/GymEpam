package org.oscar.gym.dtos.response.trainee;

import lombok.Getter;
import lombok.Setter;
import org.oscar.gym.dtos.response.trainer.TrainerResponse;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TraineeResponse {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private boolean isActive;
    private List<TrainerResponse> listTrainers;
}
