package org.oscar.gym.dtos.response;

import lombok.Data;

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
