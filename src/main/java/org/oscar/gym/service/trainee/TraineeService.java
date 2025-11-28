package org.oscar.gym.service.trainee;

import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.ChangePassDTO;
import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateListTrainerRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.dtos.response.trainee.TraineeRegistrationResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponseExtend;
import org.oscar.gym.dtos.response.trainer.TrainerResponse;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.exception.TraineeNotFoundException;
import org.oscar.gym.repository.trainee.TraineeRepository;
import org.oscar.gym.repository.trainer.TrainerRepository;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TraineeService implements ITraineeService  {

    private final TraineeRepository repository;
    private final Mapper mapper;
    private final TrainerRepository repoTrainer;

    public TraineeService(TraineeRepository repository, Mapper mapper,TrainerRepository repoTrainer) {
        this.repository = repository;
        this.mapper = mapper;
        this.repoTrainer = repoTrainer;
    }

    public TraineeRegistrationResponse saveTrainee(TraineeRegistrationRequest dto){
        Trainee trainee= mapper.mapTraineeEntity(dto);
        repository.saveEntity(trainee);
        return mapper.mapTraineeResponseCreate(trainee);
    }

    public TraineeResponseExtend findTrainee(String username){
            Trainee trainee = repository.findEntity(username);
            if(trainee==null){
                throw  new TraineeNotFoundException("trainee not found with this username "+ username);
            }
            return mapper.mapTraineeResponseGet(trainee);
    }

    public TraineeResponseExtend updateTrainee(TraineeUpdateRequest dto) {
        Trainee trainee = repository.findEntity(dto.getUsername());
        if(trainee==null){
            throw  new TraineeNotFoundException("trainee not found with this username "+ dto.getUsername());
        }
        trainee.setFirstName(dto.getFirstName());
        trainee.setLastName(dto.getLastName());
        trainee.setAddress(dto.getAddress());
        trainee.setDateOfBirth(dto.getDateOfBirth());
        trainee.setIsActive(dto.getIsActive());
        repository.updateEntity(trainee);
        return mapper.mapTraineeResponseGet(trainee);
    }

    public void deleteTrainee(String username){
        Trainee trainee= repository.deleteEntity(username);
        if(trainee==null){
            throw new TraineeNotFoundException("trainee not found with this username "+ username);
        }
    }

    @Override
    public void activeOrDeactivateTraine(UserActivateDeActivate dto) {
        Trainee trainee= repository.findEntity(dto.getUsername());
        if(trainee==null){
            throw new TraineeNotFoundException("trainee not found with this username "+ dto.getUsername());
        }
        if(trainee.getIsActive()&&dto.getIsActive()){
            log.info("trainee is already active");
        }
        if(trainee.getIsActive()&& !dto.getIsActive()){
            trainee.setIsActive(false);
            repository.updateEntity(trainee);
            log.info("trainee is deactivated");
        }
        if(!trainee.getIsActive()&&dto.getIsActive()){
            trainee.setIsActive(true);
            repository.updateEntity(trainee);
            log.info("trainee is active");
        }
        if(!trainee.getIsActive()&& !dto.getIsActive()){
            log.info("trainee is already deactivated");
        }
    }

    @Override
    public void updatePassword(ChangePassDTO dto) {
        Trainee trainee= repository.findEntity(dto.getUsername());
        if(trainee==null){
            throw new TraineeNotFoundException("trainee not found with this username "+ dto.getUsername());
        }
        trainee.setPassword(dto.getNewPass());
        repository.updateEntity(trainee);
    }

    public List<TrainerResponse> updateListOfTrainer(TraineeUpdateListTrainerRequest dto){
        List<Trainer> list = null;
        Trainee trainee= repository.findEntity(dto.getUsername());
        if(trainee==null){
            throw new TraineeNotFoundException("trainee not found with this username "+ dto.getUsername());
        }

        list = dto.getListUsernameTrainer().stream().map(e->repoTrainer.findEntity(e)).collect(Collectors.toCollection(ArrayList::new));

        for (Trainer oldTrainer : new ArrayList<>(trainee.getTrainers())) {
            oldTrainer.getTrainees().remove(trainee);
        }

        trainee.getTrainers().clear();

        for (Trainer newTrainer : list) {
            trainee.getTrainers().add(newTrainer);
            newTrainer.getTrainees().add(trainee);
        }
        repository.updateEntity(trainee);

        return trainee.getTrainers().stream().map(e->mapper.mapTrainerResponseGet(e)).collect(Collectors.toCollection(ArrayList::new));
    }


}
