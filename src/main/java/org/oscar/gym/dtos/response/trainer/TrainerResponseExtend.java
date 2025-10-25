package org.oscar.gym.dtos.response.trainer;

import lombok.Getter;
import lombok.Setter;
import org.oscar.gym.dtos.response.trainee.TraineeResponse;

import java.util.List;

@Getter
@Setter
public class TrainerResponseExtend {
    private String username;
    private String firstName;
    private String lastName;
    private String specialization;
    private boolean isActive;
    private List<TraineeResponse> traineeResponseList;
}
