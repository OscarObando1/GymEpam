package org.oscar.gym.repository.training;

import org.oscar.gym.dtos.request.training.TraineeTrainingsListResquest;
import org.oscar.gym.dtos.request.training.TrainerTrainingsListRequest;
import org.oscar.gym.dtos.request.training.TrainingDTO;
import org.oscar.gym.entity.Training;
import org.oscar.gym.entity.TrainingType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TrainingRepository {
    public void createTraining(Training entity);
    public List<TrainingType> getTypes();
    List<Training> getTraineeTrainings(TraineeTrainingsListResquest dto);
    List<Training> getTrainerTrainings(TrainerTrainingsListRequest dto);
}
