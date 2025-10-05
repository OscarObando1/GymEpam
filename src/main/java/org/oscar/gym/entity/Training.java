package org.oscar.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainerId;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee traineeId;
    private String name;

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;

    private LocalDate trainingDate;
    private Integer durationTraining;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(Id, training.Id) && Objects.equals(trainerId, training.trainerId) && Objects.equals(traineeId, training.traineeId) && Objects.equals(name, training.name) && Objects.equals(trainingType, training.trainingType) && Objects.equals(trainingDate, training.trainingDate) && Objects.equals(durationTraining, training.durationTraining);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, trainerId, traineeId, name, trainingType, trainingDate, durationTraining);
    }
}
