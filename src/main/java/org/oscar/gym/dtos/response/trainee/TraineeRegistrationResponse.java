package org.oscar.gym.dtos.response.trainee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraineeRegistrationResponse {
    private String username;
    private String password;
}
