package org.oscar.gym.dtos.request.training;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraineeTrainingsListResquest {
    @NotEmpty
    private String username;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String trainerName;
}
