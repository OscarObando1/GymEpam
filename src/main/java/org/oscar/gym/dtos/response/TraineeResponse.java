package org.oscar.gym.dtos.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TraineeResponse {

    private String firstName;
    private String lastName;
    private String username;

    @Override
    public String toString() {
        return "Trainee is " + firstName+" " + lastName+" "+username;
    }
}
