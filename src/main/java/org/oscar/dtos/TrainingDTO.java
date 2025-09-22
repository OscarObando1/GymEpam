package org.oscar.dtos;

import org.oscar.model.TrainingType;

import java.time.Duration;
import java.time.LocalDate;

public class TrainingDTO {
    private long trainerId;
    private long traineeId;
    private String name;
    private TrainingType trainingType;
    private LocalDate date;
    private Duration duration;

    public TrainingDTO() {
    }

    public TrainingDTO(long trainerId, long traineeId, String name, TrainingType trainingType, LocalDate date, Duration duration) {
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
