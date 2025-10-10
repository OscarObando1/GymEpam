package org.oscar.gym.service.training;

import org.oscar.gym.dtos.TrainingDTO;
import org.oscar.gym.dtos.response.TraineeResponse;
import org.oscar.gym.dtos.response.TrainerResponse;
import org.oscar.gym.dtos.response.TrainingResponse;
import org.oscar.gym.entity.Trainee;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface ITrainingService {
    void createTraining(TrainingDTO dto);
    List<TrainingResponse> listTrainingOfTrainee(String username);
    List<TrainingResponse> listTrainingOfTrainer(String username);
}
