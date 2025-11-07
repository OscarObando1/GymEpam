package org.oscar.gym.service.training;

import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.request.training.TraineeTrainingsListResquest;
import org.oscar.gym.dtos.request.training.TrainerTrainingsListRequest;
import org.oscar.gym.dtos.request.training.TrainingDTO;
import org.oscar.gym.dtos.response.TrainingResponse;
import org.oscar.gym.dtos.response.training.TraineeTrainingsListResponse;
import org.oscar.gym.dtos.response.training.TrainerTrainingsListResponse;
import org.oscar.gym.dtos.response.training.TrainingResponseAll;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.Training;
import org.oscar.gym.entity.TrainingType;
import org.oscar.gym.exception.TraineeNotFoundException;
import org.oscar.gym.exception.TrainerNotFoundException;
import org.oscar.gym.repository.trainee.TraineeRepository;
import org.oscar.gym.repository.trainer.TrainerRepository;
import org.oscar.gym.repository.training.TrainingRepository;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class TrainigService implements ITrainingService{

    private final TrainingRepository repository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final Mapper mapper;

    public TrainigService(TrainingRepository repository, TraineeRepository traineeRepository, TrainerRepository trainerRepository, Mapper mapper) {
        this.repository = repository;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.mapper = mapper;
    }

    @Override
    public void createTraining(TrainingDTO dto) {
        Training entity = null;
        Trainer trainer = trainerRepository.findEntity(dto.getTrainerUsername());
        if(trainer==null){
            throw new TrainerNotFoundException("trainer not found with this username "+ dto.getTrainerUsername());
        }
        Trainee trainee = traineeRepository.findEntity(dto.getTraineeUsername());
        if(trainee==null){
            throw new TraineeNotFoundException("trainee not found with this username "+ dto.getTraineeUsername());
        }
        entity = mapper.mapTrainingEntity(dto);
        entity.setTrainee(trainee);
        entity.setTrainer(trainer);
        entity.setTrainingType(trainer.getSpecialization());
        repository.createTraining(entity);
    }


    @Override
    public List<TrainingResponseAll> listTypes() {
        List<TrainingResponseAll> list = null;
        List<TrainingType> trainingType = null;
        trainingType = repository.getTypes();
        list = trainingType.stream().map(e->mapper.mapTrainingResponseType(e)).toList();
        return list;
    }

    @Override
    public List<TraineeTrainingsListResponse> getTrainingListTrainee(TraineeTrainingsListResquest dto) {
        Trainee trainee = traineeRepository.findEntity(dto.getUsername());
        if(trainee==null){
            throw new TraineeNotFoundException("trainee not found with this username "+ dto.getUsername());
        }
        List<Training> list = null;
        list=repository.getTraineeTrainings(dto);
        if(list==null){
            throw new TraineeNotFoundException("trainee not found with this username "+ dto.getUsername());
        }
        return list.stream().map(e->mapper.mapTrainigListTrainee(e)).toList();
    }

    @Override
    public List<TrainerTrainingsListResponse> getTrainingListTrainer(TrainerTrainingsListRequest dto) {
        Trainer trainer = trainerRepository.findEntity(dto.getUsername());
        if(trainer==null){
            throw new TrainerNotFoundException("trainer not found with this username "+ dto.getUsername());
        }
        List<Training> list = null;
        list=repository.getTrainerTrainings(dto);
        if(list==null){
            throw new TrainerNotFoundException("trainer not found with this username "+ dto.getUsername());
        }
        return list.stream().map(e->mapper.mapTrainigListTrainer(e)).toList();
    }


}
