package org.oscar.gym.dtos.response.training;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerTrainingsListResponse {
    private String trainingName;
    private String date;
    private String type;
    private String duration;
    private String traineeName;
}
