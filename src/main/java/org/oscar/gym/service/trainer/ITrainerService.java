package org.oscar.gym.service.trainer;

import org.oscar.gym.dtos.request.temp.ChangePassDTO;
import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TrainerDTO;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerUpdateRequest;
import org.oscar.gym.dtos.response.TrainerResponsetemp;
import org.oscar.gym.dtos.response.trainer.TrainerRegistrationResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponseExtend;

public interface ITrainerService {
    TrainerRegistrationResponse saveTrainer(TrainerRegistrationRequest dto);

    TrainerResponseExtend findTrainer(String username);

    TrainerResponseExtend updateTrainer(TrainerUpdateRequest dto, long id);

    void deleteTrainer(LoginDTO dto,String username);

    void assignTrainee(LoginDTO dto,String userTrainer, String userTrainee);

    TrainerResponsetemp activeOrDeactivateTrainer(long id);

    void updatePassword(ChangePassDTO dto);
}
