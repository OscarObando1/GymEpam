package org.oscar.gym.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
