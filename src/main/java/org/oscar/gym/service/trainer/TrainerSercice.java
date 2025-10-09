package org.oscar.gym.service.trainer;


import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TrainerDTO;
import org.oscar.gym.dtos.response.TrainerResponse;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.repository.trainer.TrainerRepository;
import org.oscar.gym.security.IAuthenticator;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Slf4j
@Component
public class TrainerSercice implements ITrainerService{
    private final TrainerRepository repository;
    private final Mapper mapper;
    private final IAuthenticator authenticator;


    public TrainerSercice(TrainerRepository repository, Mapper mapper, IAuthenticator authenticator) {
        this.repository = repository;
        this.mapper = mapper;
        this.authenticator = authenticator;
    }

    @Override
    public void saveTrainer(TrainerDTO dto){
        repository.saveEntity(dto);
    }

    public TrainerResponse findTrainer(LoginDTO dto, String username){
        if(!authenticator.isAuthorized(dto.getUsername(), dto.getPassword())){
            throw new UnsupportedOperationException("Sorry user not authorized");
        }
         try {
                    Trainer trainer = repository.findEntity(username);
                    return mapper.mapTrainerResponse(trainer);
                }catch (Exception e) {
                    log.info("Does not found trainer with this username "+username);
                    }
                return null;

    }
    @Override
    public TrainerResponse updateTrainer(LoginDTO dto,TrainerDTO trainerDTO, long id) {
        if(!authenticator.isAuthorized(dto.getUsername(), dto.getPassword())){
            throw new UnsupportedOperationException("Sorry user not authorized");
        }
        try {
            Trainer trainer = repository.updateEntity(trainerDTO, id);
            return mapper.mapTrainerResponse(trainer);
        } catch (Exception e) {
            log.info("Trainer not found with id " + id);
        }
        return null;
    }

    @Override
    public void deleteTrainer(LoginDTO dto,String username){
        if(!authenticator.isAuthorized(dto.getUsername(), dto.getPassword())){
            throw new UnsupportedOperationException("Sorry user not authorized");
        }
        repository.deleteEntity(username);
    }

    @Override
    public void assignTrainee(LoginDTO dto,String userTrainer, String userTrainee){
        repository.assignTraineeEntity(userTrainer,userTrainee);
    }

    @Override
    public TrainerResponse activeOrDeactivateTrainer(long id) {
        try {
            Trainer trainer = repository.changeActive(id);
            return mapper.mapTrainerResponse(trainer);
        } catch (Exception e) {
            log.info("Trainer not found with id " + id);
        }
        return null;
    }
}
