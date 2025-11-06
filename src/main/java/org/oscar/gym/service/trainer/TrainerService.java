package org.oscar.gym.service.trainer;


import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.ChangePassDTO;

import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerUpdateRequest;
import org.oscar.gym.dtos.response.trainer.TrainerRegistrationResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponseExtend;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.exception.TraineeNotFoundException;
import org.oscar.gym.exception.TrainerNotFoundException;
import org.oscar.gym.repository.trainee.TraineeRepository;
import org.oscar.gym.repository.trainer.TrainerRepository;
import org.oscar.gym.repository.trainig_types.TrainingTypes;
import org.oscar.gym.security.IAuthenticator;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TrainerService implements ITrainerService{
    private final TrainingTypes typeRespository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository repository;
    private final Mapper mapper;


    public TrainerService(TrainingTypes typeRespository, TraineeRepository traineeRepository, TrainerRepository repository, Mapper mapper) {
        this.typeRespository = typeRespository;
        this.traineeRepository = traineeRepository;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TrainerRegistrationResponse saveTrainer(TrainerRegistrationRequest dto){
        Trainer trainer = mapper.mapTrainerEntity(dto);
        trainer.setSpecialization(typeRespository.findType(dto.getSpecialization()));
        repository.saveEntity(trainer);
        return mapper.mapTrainerResponse(trainer);
    }

    public TrainerResponseExtend findTrainer(String username){
        Trainer trainer = repository.findEntity(username);
        if(trainer==null){
            throw new TrainerNotFoundException("trainee not found with this username "+ username);
        }
        return mapper.mapTrainerResponseGetMethod(trainer);


    }
    @Override
    public TrainerResponseExtend updateTrainer(TrainerUpdateRequest dto) {
            Trainer trainer = repository.updateEntity(dto);
        if(trainer==null){
            throw new TrainerNotFoundException("trainee not found with this username "+ dto.getUsername());
        }
            return mapper.mapTrainerResponseGetMethod(trainer);

    }

//    @Override
//    public void deleteTrainer(LoginDTO dto,String username){
//        if(!authenticator.isAuthorized(dto.getUsername(), dto.getPassword())){
//            throw new UnsupportedOperationException("Sorry user not authorized");
//        }
//        repository.deleteEntity(username);
//    }
//
//    @Override
//    public void assignTrainee(LoginDTO dto,String userTrainer, String userTrainee){
//        repository.assignTraineeEntity(userTrainer,userTrainee);
//    }

    @Override
    public void activeOrDeactivateTrainer(UserActivateDeActivate dto) {
        try {
            repository.changeActive(dto);
        } catch (Exception e) {
            log.info("Trainer not found with username " + dto.getUsername());
        }
    }

    @Override
    public void updatePassword(ChangePassDTO dto) {
        repository.updatePass(dto);
    }

    @Override
    public List<TrainerResponse> trainerWithoutTrainee(String username) {
        Trainee trainee = traineeRepository.findEntity(username);
        if(trainee==null){
            throw new TraineeNotFoundException("trainee not found with this username "+username);
        }
        List<Trainer> list = null;
        list= repository.getTrainerWithoutTrainee(username);
        if(list==null){
            throw new TraineeNotFoundException("trainee not found with this username "+username);
        }

        return list.stream().map(e->mapper.mapTrainerResponseGet(e)).toList();
    }
}
