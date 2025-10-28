package org.oscar.gym.dtos.request.training;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TraineeTrainingsListResquest {
    private String username;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String trainerName;
}
