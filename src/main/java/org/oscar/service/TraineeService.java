package org.oscar.service;

import org.oscar.dtos.TraineeDTO;
import org.oscar.dtos.response.TraineeResponse;
import org.oscar.entity.Trainee;
import org.oscar.repository.trainee.TraineeRepository;
import org.oscar.utils.Mapper;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class TraineeService  {

    private final TraineeRepository repository;
    private final Mapper mapper;

    public TraineeService(TraineeRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void saveTrainee(TraineeDTO dto){
        repository.saveEntity(dto);
    }

    public TraineeResponse findTrainee(String username){
        Trainee trainee = repository.findEntity(username);
        if(trainee==null){
            throw new NoSuchElementException("Does not found trainee with this username");
        }
        return mapper.mapTraineeResponse(trainee);
    }

    public TraineeResponse updateTrainee(TraineeDTO traineeDTO,long id){
        Trainee trainee = repository.updateEntity(traineeDTO, id);
        return mapper.mapTraineeResponse(trainee);
    }

    public void deleteTrainee(String username){
        repository.deleteEntity(username);
    }


}
