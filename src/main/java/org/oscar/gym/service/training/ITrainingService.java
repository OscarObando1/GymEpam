package org.oscar.gym.service.training;

import org.oscar.gym.dtos.TrainingDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface ITrainingService {
    void createTraining(TrainingDTO dto);
}
