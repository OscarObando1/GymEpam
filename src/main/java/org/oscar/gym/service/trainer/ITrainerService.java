package org.oscar.gym.service.trainer;

import org.oscar.gym.dtos.ChangePassDTO;

import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerUpdateRequest;
import org.oscar.gym.dtos.response.trainer.TrainerRegistrationResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponseExtend;

import java.util.List;

public interface ITrainerService {
    TrainerRegistrationResponse saveTrainer(TrainerRegistrationRequest dto);

    TrainerResponseExtend findTrainer(String username);

    TrainerResponseExtend updateTrainer(TrainerUpdateRequest dto, long id);

//    void deleteTrainer(LoginDTO dto,String username);
//
//    void assignTrainee(LoginDTO dto,String userTrainer, String userTrainee);

    void activeOrDeactivateTrainer(UserActivateDeActivate dto);

    void updatePassword(ChangePassDTO dto);

   List<TrainerResponse> trainerWithoutTrainee(String username);

}
