package org.oscar.utils;

import org.oscar.dtos.TraineeDTO;
import org.oscar.entity.Trainee;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    private final IGenerator generator;

    public Mapper(IGenerator generator) {
        this.generator = generator;
    }


    public Trainee mapTrainee(TraineeDTO dto){
        Trainee trainee = new Trainee();
        trainee.setFirstName(dto.getFirstName());
        trainee.setLastName(dto.getLastName());
        String userame = generator.createUser(dto.getFirstName(), dto.getLastName());
        String pass = generator.generatePass();
        trainee.setUsername(userame);
        trainee.setPassword(pass);
        trainee.setAddress(dto.getAddress());
        trainee.setIsActive(true);
        return trainee;
    }

}
