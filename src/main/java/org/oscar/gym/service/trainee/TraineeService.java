package org.oscar.gym.service.trainee;

import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.dtos.response.TraineeResponse;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.repository.trainee.TraineeRepository;
import org.oscar.gym.security.IAuthenticator;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;

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
            System.out.println("Does not found trainee with this username");
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
            System.out.println("Trainee not found with id " + id);
        }
        return null;
    }

    public void deleteTrainee(LoginDTO loginDTO,String username){
        if(!authenticator.isAuthorized(loginDTO.getUsername(), loginDTO.getPassword())){
            throw new UnsupportedOperationException("Sorry user not authorized");
        }
        repository.deleteEntity(username);
    }


}
