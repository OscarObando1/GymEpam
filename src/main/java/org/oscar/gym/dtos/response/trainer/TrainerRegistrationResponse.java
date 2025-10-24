package org.oscar.gym.dtos.response.trainer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerRegistrationResponse {
    private String username;
    private String password;
}