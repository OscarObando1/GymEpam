package org.oscar.dtos.response;

import lombok.Data;
import org.oscar.entity.TrainingType;

@Data
public class TrainerResponse {
    private String firstName;
    private String lastName;
    private String specialization;

    @Override
    public String toString() {
        return "Trainer"+
                firstName + ' ' + lastName + ' ' + specialization;
    }
}
