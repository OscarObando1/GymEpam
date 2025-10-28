package org.oscar.gym.repository.trainer;

import org.oscar.gym.dtos.ChangePassDTO;
import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerUpdateRequest;
import org.oscar.gym.entity.Trainer;
import org.springframework.stereotype.Component;

@Component
public interface TrainerRepository {
    Trainer saveEntity(TrainerRegistrationRequest dto);
    Trainer findEntity(String username );
    Trainer updateEntity(TrainerUpdateRequest dto, long id);
    void deleteEntity(String username);
    void assignTraineeEntity(String userTrainer,String userTrainee);
    void changeActive(UserActivateDeActivate dto);
    void updatePass(ChangePassDTO dto);
}
