package org.oscar.gym.dtos.response;

import lombok.Data;

@Data
public class TraineeResponse {

    private String firstName;
    private String lastName;
    private String username;

    @Override
    public String toString() {
        return "Trainee is " + firstName+" " + lastName+" "+username;
    }
}
