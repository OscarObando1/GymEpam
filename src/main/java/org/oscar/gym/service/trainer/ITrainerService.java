package org.oscar.gym.service.trainer;

import org.oscar.gym.dtos.request.temp.ChangePassDTO;
import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TrainerDTO;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.response.TrainerResponseExtend;
import org.oscar.gym.dtos.response.trainer.TrainerRegistrationResponse;

public interface ITrainerService {
    TrainerRegistrationResponse saveTrainer(TrainerRegistrationRequest dto);

    TrainerResponseExtend findTrainer(LoginDTO dto, String username);

    TrainerResponseExtend updateTrainer(LoginDTO dto, TrainerDTO trainerDTO, long id);

    void deleteTrainer(LoginDTO dto,String username);

    void assignTrainee(LoginDTO dto,String userTrainer, String userTrainee);

    TrainerResponseExtend activeOrDeactivateTrainer(long id);

    void updatePassword(ChangePassDTO dto);
}
