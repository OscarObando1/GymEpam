package org.oscar.gym.dtos.request;

import lombok.Getter;
import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TrainerDTO;

@Getter
public class RequestTrainer {
    private LoginDTO loginDTO;
    private TrainerDTO trainerDTO;

}
