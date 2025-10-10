package org.oscar.gym.service.training;

import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.TrainingDTO;
import org.oscar.gym.dtos.response.TraineeResponse;
import org.oscar.gym.dtos.response.TrainerResponse;
import org.oscar.gym.dtos.response.TrainingResponse;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.Training;
import org.oscar.gym.repository.training.TrainingRepository;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Component
public class TrainigService implements ITrainingService{

    private final TrainingRepository repository;
    private final Mapper mapper;

    public TrainigService(TrainingRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void createTraining(TrainingDTO dto) {
        repository.createTraining(dto);
    }

    @Override
    public List<TrainingResponse> listTrainingOfTrainee(String username) {
        List<Training> temp = null;
        try {
            temp = repository.getTrainingWithTrainee(username);
            return temp.stream().map(mapper::mapTrainingResponse).toList();
        } catch (Exception e) {
            log.info("Trainee not found with username " + username);
        }
        return null;
    }

    @Override
    public List<TrainingResponse> listTrainingOfTrainer(String username) {
        List<Training> temp = null;
        try {
            temp = repository.getTrainingWithTraineer(username);
            return temp.stream().map(mapper::mapTrainingResponse).toList();
        } catch (Exception e) {
            log.info("Trainer not found with username " + username);
        }
        return null;
    }


}
