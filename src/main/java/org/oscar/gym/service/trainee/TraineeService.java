package org.oscar.gym.service.trainee;

import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.request.temp.ChangePassDTO;
import org.oscar.gym.dtos.request.temp.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.dtos.response.trainee.TraineeResponse;
import org.oscar.gym.dtos.response.trainee.TraineeRegistrationResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponseExtend;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.repository.trainee.TraineeRepository;
import org.oscar.gym.security.IAuthenticator;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TraineeService implements ITraineeService  {

    private final TraineeRepository repository;
    private final Mapper mapper;
    private final IAuthenticator authenticator;

    public TraineeService(TraineeRepository repository, Mapper mapper, IAuthenticator authenticator) {
        this.repository = repository;
        this.mapper = mapper;
        this.authenticator = authenticator;
    }

    public TraineeRegistrationResponse saveTrainee(TraineeRegistrationRequest dto){
        Trainee trainee= repository.saveEntity(dto);
        return mapper.mapTraineeResponseCreate(trainee);
    }

    public TraineeResponseExtend findTrainee(String username){
        try {
            Trainee trainee = repository.findEntity(username);
            return mapper.mapTraineeResponseGet(trainee);
        }catch (Exception e) {
            log.info("Does not found trainee with this username "+username);
            }
        return null;

    }

    public TraineeResponseExtend updateTrainee(TraineeUpdateRequest dto, long id) {
        try {
            Trainee trainee = repository.updateEntity(dto, id);
            return mapper.mapTraineeResponseGet(trainee);
        } catch (Exception e) {
            log.info("Trainee not found with id " + id);
        }
        return null;
    }

    public void deleteTrainee(String username){
        repository.deleteEntity(username);
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


}
