package org.oscar.gym.repository.training;

import org.oscar.gym.dtos.TrainingDTO;
import org.oscar.gym.entity.Training;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TrainingRepository {
    public void createTraining(TrainingDTO dto);
    public List<Training> getTraining(String username);
}
