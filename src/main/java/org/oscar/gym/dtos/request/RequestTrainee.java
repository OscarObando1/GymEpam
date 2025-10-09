package org.oscar.gym.dtos.request;

import lombok.Getter;
import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TraineeDTO;

@Getter
public class RequestTrainee {
    private LoginDTO loginDTO;
    private TraineeDTO traineeDTO;
}
