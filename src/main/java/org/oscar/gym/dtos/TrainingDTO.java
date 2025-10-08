package org.oscar.gym.dtos;





import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TrainingDTO {
    private String trainerUsername;
    private String traineeUsername;
    private String name;
    private LocalDate date;
    private Integer duration;
}
