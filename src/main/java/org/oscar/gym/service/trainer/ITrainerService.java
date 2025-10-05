package org.oscar.gym.service.trainer;

import org.oscar.gym.dtos.TrainerDTO;
import org.oscar.gym.dtos.response.TrainerResponse;
import org.oscar.gym.entity.Trainer;

import java.util.NoSuchElementException;

public interface ITrainerService {
    void saveTrainer(TrainerDTO dto);

    TrainerResponse findTrainer(String username);

    TrainerResponse updateTrainer(TrainerDTO trainerDTO, long id);

    void deleteTrainer(String username);

    void assignTrainee(String userTrainer, String userTrainee);
}
