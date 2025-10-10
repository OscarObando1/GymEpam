package org.oscar.gym.dtos.response;

import lombok.Data;

@Data
public class TrainingResponse {
    private String name;
    private String trainerUsername;
    private String traineeUsername;
    private String type;

    @Override
    public String toString() {
        return "Training:" +
                "name='" + name + '\'' +
                ", trainerUsername='" + trainerUsername + '\'' +
                ", traineeUsername='" + traineeUsername + '\'' +
                ", type='" + type + '\'';
    }
}
