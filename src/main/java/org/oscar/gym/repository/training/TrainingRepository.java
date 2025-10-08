package org.oscar.gym.repository.training;

import org.oscar.gym.dtos.TrainingDTO;
import org.springframework.stereotype.Component;

@Component
public interface TrainingRepository {
    public void createTraining(TrainingDTO dto);
}
