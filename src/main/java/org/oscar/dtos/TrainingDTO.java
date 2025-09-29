package org.oscar.dtos;


import lombok.Getter;
import lombok.Setter;
import org.oscar.entity.TrainingType;

import java.time.LocalDate;

@Setter
@Getter
public class TrainingDTO {
    private long trainerId;
    private long traineeId;
    private String name;
    private TrainingType trainingType;
    private LocalDate date;
    private Integer duration;

    public TrainingDTO() {
    }

    public TrainingDTO(long trainerId, long traineeId, String name, TrainingType trainingType, LocalDate date, Integer duration) {
        this.trainerId = trainerId;
        this.traineeId = traineeId;
        this.name = name;
        this.trainingType = trainingType;
        this.date = date;
        this.duration = duration;
    }

}
