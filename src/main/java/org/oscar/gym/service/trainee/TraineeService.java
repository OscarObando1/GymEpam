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
import org.oscar.gym.security.IAuthenticator;
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
    private final IAuthenticator authenticator;
    private final TrainerRepository repoTrainer;

    public TraineeService(TraineeRepository repository, Mapper mapper, IAuthenticator authenticator, TrainerRepository repoTrainer) {
        this.repository = repository;
        this.mapper = mapper;
        this.authenticator = authenticator;
        this.repoTrainer = repoTrainer;
    }

    public TraineeRegistrationResponse saveTrainee(TraineeRegistrationRequest dto){
        Trainee trainee= repository.saveEntity(dto);
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
        Trainee trainee = repository.updateEntity(dto);
        if(trainee==null){
            throw  new TraineeNotFoundException("trainee not found with this username "+ dto.getUsername());
        }
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
        try {
            repository.changeActive(dto);
        } catch (Exception e) {
            log.info("Trainee not found with username " + dto.getUsername());
        }
    }

    @Override
    public void updatePassword(ChangePassDTO dto) {
        if(!authenticator.isAuthorized(dto.getUsername(), dto.getOldPass())){
            throw new UnsupportedOperationException("Sorry user not authorized");
        }
        repository.updatePass(dto);
    }

    public List<TrainerResponse> updateListOfTrainer(TraineeUpdateListTrainerRequest dto){
        List<Trainer> list = null;
        Trainee trainee =null;
        list = dto.getListUsernameTrainer().stream().map(e->repoTrainer.findEntity(e)).collect(Collectors.toCollection(ArrayList::new));
        trainee= repository.updateListTrainer(dto.getUsername(), list);
        return trainee.getTrainers().stream().map(e->mapper.mapTrainerResponseGet(e)).collect(Collectors.toCollection(ArrayList::new));
    }


}
