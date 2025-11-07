package org.oscar.gym.repository.trainer;

import org.oscar.gym.dtos.ChangePassDTO;
import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerUpdateRequest;
import org.oscar.gym.entity.Trainer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TrainerRepository {
    Trainer saveEntity(Trainer entity);
    Trainer findEntity(String username );
    Trainer updateEntity(Trainer entity);
    void deleteEntity(String username);
    public List<Trainer> getTrainerWithoutTrainee(String username);
}
