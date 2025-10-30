package org.oscar.gym.dtos.request.training;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TrainerTrainingsListRequest {
    @NotEmpty
    private String username;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String traineeName;
}
