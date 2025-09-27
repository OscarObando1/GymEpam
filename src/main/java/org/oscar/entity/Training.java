package org.oscar.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.oscar.enums.TrainingType;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Training {
    private Long Id;
    private Long trainerId;
    private Long traineeId;
    private String name;
    private TrainingType trainingType;
    private LocalDate trainingDate;
    private Integer durationTraining;


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

}
