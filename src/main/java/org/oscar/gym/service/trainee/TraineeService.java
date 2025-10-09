package org.oscar.gym.service.trainee;

import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.ChangePassDTO;
import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.dtos.response.TraineeResponse;
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

    public void saveTrainee(TraineeDTO dto){
        repository.saveEntity(dto);
    }

    public TraineeResponse findTrainee(LoginDTO loginDTO, String username){
        if(!authenticator.isAuthorized(loginDTO.getUsername(), loginDTO.getPassword())){
            throw new UnsupportedOperationException("Sorry user not authorized");
        }
        try {
            Trainee trainee = repository.findEntity(username);
            return mapper.mapTraineeResponse(trainee);
        }catch (Exception e) {
            log.info("Does not found trainee with this username "+username);
            }
        return null;

    }

    public TraineeResponse updateTrainee(LoginDTO loginDTO,TraineeDTO traineeDTO, long id) {
        if(!authenticator.isAuthorized(loginDTO.getUsername(), loginDTO.getPassword())){
            throw new UnsupportedOperationException("Sorry user not authorized");
        }
        try {
            Trainee trainee = repository.updateEntity(traineeDTO, id);
            return mapper.mapTraineeResponse(trainee);
        } catch (Exception e) {
            log.info("Trainee not found with id " + id);
        }
        return null;
    }

    public void deleteTrainee(LoginDTO loginDTO,String username){
        if(!authenticator.isAuthorized(loginDTO.getUsername(), loginDTO.getPassword())){
            throw new UnsupportedOperationException("Sorry user not authorized");
        }
        repository.deleteEntity(username);
    }

    @Override
    public TraineeResponse activeOrDeactivateTraine(long id) {
        try {
            Trainee trainee = repository.changeActive(id);
            return mapper.mapTraineeResponse(trainee);
        } catch (Exception e) {
            log.info("Trainee not found with id " + id);
        }
        return null;
    }

    @Override
    public void updatePassword(ChangePassDTO dto) {
        if(!authenticator.isAuthorized(dto.getUsername(), dto.getOldPass())){
            throw new UnsupportedOperationException("Sorry user not authorized");
        }
        repository.updatePass(dto);
    }


}
