package org.oscar.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

public class Training {
    private Long Id;
    private Long trainerId;
    private Long traineeId;
    private String name;
    private TrainingType trainingType;
    private LocalDate trainingDate;
    private Duration durationTraining;

    public Training() {
    }

    public Training(Long id, Long trainerId, Long traineeId, String name, TrainingType trainingType, LocalDate trainingDate, Duration durationTraining) {
        Id = id;
        this.trainerId = trainerId;
        this.traineeId = traineeId;
        this.name = name;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.durationTraining = durationTraining;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public Long getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Long traineeId) {
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

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    public Duration getDurationTraining() {
        return durationTraining;
    }

    public void setDurationTraining(Duration durationTraining) {
        this.durationTraining = durationTraining;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Training training = (Training) o;
        return Objects.equals(Id, training.Id) && Objects.equals(trainerId, training.trainerId) && Objects.equals(traineeId, training.traineeId) && Objects.equals(name, training.name) && trainingType == training.trainingType && Objects.equals(trainingDate, training.trainingDate) && Objects.equals(durationTraining, training.durationTraining);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(Id);
        result = 31 * result + Objects.hashCode(trainerId);
        result = 31 * result + Objects.hashCode(traineeId);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(trainingType);
        result = 31 * result + Objects.hashCode(trainingDate);
        result = 31 * result + Objects.hashCode(durationTraining);
        return result;
    }

    @Override
    public String toString() {
        return "Training{" +
                "Id=" + Id +
                ", trainerId=" + trainerId +
                ", traineeId=" + traineeId +
                ", name='" + name + '\'' +
                ", trainingType=" + trainingType +
                ", trainingDate=" + trainingDate +
                ", durationTraining=" + durationTraining +
                '}';
    }
}
