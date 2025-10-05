package org.oscar.gym.service.trainer;

import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TrainerDTO;
import org.oscar.gym.dtos.response.TrainerResponse;
import org.oscar.gym.entity.Trainer;

import java.util.NoSuchElementException;

public interface ITrainerService {
    void saveTrainer(TrainerDTO dto);

    TrainerResponse findTrainer(LoginDTO dto,String username);

    TrainerResponse updateTrainer(LoginDTO dto,TrainerDTO trainerDTO, long id);

    void deleteTrainer(LoginDTO dto,String username);

    void assignTrainee(LoginDTO dto,String userTrainer, String userTrainee);
}
