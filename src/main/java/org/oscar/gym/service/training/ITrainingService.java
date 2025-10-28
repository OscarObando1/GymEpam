package org.oscar.gym.service.training;

import org.oscar.gym.dtos.request.training.TrainingDTO;
import org.oscar.gym.dtos.response.TrainingResponse;
import org.oscar.gym.dtos.response.training.TrainingResponseAll;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ITrainingService {
    void createTraining(TrainingDTO dto);
    List<TrainingResponse> listTrainingOfTrainee(String username);
    List<TrainingResponse> listTrainingOfTrainer(String username);
    List<TrainingResponseAll> listTypes();
}
