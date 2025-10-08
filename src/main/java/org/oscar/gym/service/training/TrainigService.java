package org.oscar.gym.service.training;

import org.oscar.gym.dtos.TrainingDTO;
import org.oscar.gym.repository.training.TrainingRepository;
import org.springframework.stereotype.Service;

@Service
public class TrainigService implements ITrainingService{

    private final TrainingRepository repository;

    public TrainigService(TrainingRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createTraining(TrainingDTO dto) {
        repository.createTraining(dto);
    }
}
