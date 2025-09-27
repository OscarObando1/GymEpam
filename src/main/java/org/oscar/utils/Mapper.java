package org.oscar.utils;

import org.oscar.dtos.TraineeDTO;
import org.oscar.entity.Trainee;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Trainee mapTrainee(TraineeDTO dto){
        Trainee trainee = new Trainee();
        trainee.setFirstName(dto.getFirstName());
        trainee.setLastName(dto.getLastName());
        trainee.setAddress(dto.getAddress());
        trainee.setIsActive(true);
        return trainee;
    }

}
