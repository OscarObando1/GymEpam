package org.oscar.dtos;

import org.oscar.enums.TrainingType;
import java.time.LocalDate;

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

    public long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(long trainerId) {
        this.trainerId = trainerId;
    }

    public long getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(long traineeId) {
        this.traineeId = traineeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
