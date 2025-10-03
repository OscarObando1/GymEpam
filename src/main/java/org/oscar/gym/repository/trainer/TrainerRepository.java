package org.oscar.gym.repository.trainer;

import org.oscar.gym.dtos.TrainerDTO;
import org.oscar.gym.entity.Trainer;
import org.springframework.stereotype.Component;

@Component
public interface TrainerRepository {
    Trainer saveEntity(TrainerDTO dto);
    Trainer findEntity(String username );
    Trainer updateEntity(TrainerDTO dto, long id);
    void deleteEntity(String username);
    void assignTraineeEntity(String userTrainer,String userTrainee);
}
