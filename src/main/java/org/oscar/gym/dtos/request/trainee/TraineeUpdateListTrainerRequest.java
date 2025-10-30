package org.oscar.gym.dtos.request.trainee;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TraineeUpdateListTrainerRequest {
    private String username;
    private List<String> listUsernameTrainer;
}
