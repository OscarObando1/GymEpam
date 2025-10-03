package org.oscar.gym.dtos.response;

import lombok.Data;

@Data
public class TraineeResponse {

    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "Trainee is " + firstName+" " + lastName;
    }
}
