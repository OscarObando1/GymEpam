package org.oscar.gym.service.training;

import org.oscar.gym.dtos.request.training.TraineeTrainingsListResquest;
import org.oscar.gym.dtos.request.training.TrainerTrainingsListRequest;
import org.oscar.gym.dtos.request.training.TrainingDTO;
import org.oscar.gym.dtos.response.TrainingResponse;
import org.oscar.gym.dtos.response.training.TraineeTrainingsListResponse;
import org.oscar.gym.dtos.response.training.TrainerTrainingsListResponse;
import org.oscar.gym.dtos.response.training.TrainingResponseAll;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ITrainingService {
    void createTraining(TrainingDTO dto);
    List<TrainingResponseAll> listTypes();
    List<TraineeTrainingsListResponse> getTrainingListTrainee(TraineeTrainingsListResquest dto);
    List<TrainerTrainingsListResponse> getTrainingListTrainer(TrainerTrainingsListRequest dto);
}
