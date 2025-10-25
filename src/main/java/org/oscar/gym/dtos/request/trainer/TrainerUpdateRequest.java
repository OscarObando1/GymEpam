package org.oscar.gym.dtos.request.trainer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerUpdateRequest {

    private String username;
    private String firstName;
    private String lastName;
    private String specialization;
    private boolean isActive;
}
