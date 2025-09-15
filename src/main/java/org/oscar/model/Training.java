package org.oscar.model;

import java.time.Duration;
import java.time.LocalDate;

public class Training {
    private Long Id;
    private Long trainerId;
    private Long traineeId;
    private String name;
    private TrainingType trainingType;
    private LocalDate trainingDate;
    private Duration durationTraining;

}
