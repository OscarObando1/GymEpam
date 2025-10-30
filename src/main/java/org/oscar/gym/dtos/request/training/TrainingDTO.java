package org.oscar.gym.dtos.request.training;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TrainingDTO {
    @NotEmpty
    private String trainerUsername;
    @NotEmpty
    private String traineeUsername;
    @NotEmpty
    private String name;
    @NotNull
    private LocalDate date;
    @NotNull
    private Integer duration;
}
