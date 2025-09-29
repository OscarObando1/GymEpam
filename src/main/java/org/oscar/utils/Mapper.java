package org.oscar.utils;

import org.oscar.dtos.TraineeDTO;
import org.oscar.dtos.response.TraineeResponse;
import org.oscar.entity.Trainee;
import org.springframework.stereotype.Component;

@Component
public class Mapper {


    public Trainee mapTrainee(TraineeDTO dto){
        Trainee trainee = new Trainee();
        trainee.setFirstName(dto.getFirstName());
        trainee.setLastName(dto.getLastName());
        trainee.setAddress(dto.getAddress());
        trainee.setDateOfBirth(dto.getDateOfBirth());
        trainee.setIsActive(true);
        return trainee;
    }

    public TraineeResponse mapTraineeResponse(Trainee trainee){
        TraineeResponse response = new TraineeResponse();
        response.setFirstName(trainee.getFirstName());
        response.setLastName(trainee.getLastName());
        return response;
    }

}
