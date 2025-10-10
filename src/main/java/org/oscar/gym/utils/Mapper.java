package org.oscar.gym.utils;


import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.dtos.TrainerDTO;
import org.oscar.gym.dtos.response.TraineeResponse;
import org.oscar.gym.dtos.response.TrainerResponse;
import org.oscar.gym.dtos.response.TrainingResponse;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.Training;
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
        response.setUsername(trainee.getUsername());
        return response;
    }

    public Trainer mapTrainer(TrainerDTO dto){
        Trainer trainer = new Trainer();
        trainer.setFirstName(dto.getFirstName());
        trainer.setLastName(dto.getLastName());
        trainer.setIsActive(true);
        return trainer;
    }

    public TrainerResponse mapTrainerResponse(Trainer trainer){
        TrainerResponse response = new TrainerResponse();
        response.setFirstName(trainer.getFirstName());
        response.setLastName(trainer.getLastName());
        response.setSpecialization(trainer.getSpecialization().getName().name());
        return response;
    }

    public TrainingResponse mapTrainingResponse(Training training){
        TrainingResponse response = new TrainingResponse();
        response.setName(training.getName());
        response.setTrainerUsername(training.getTrainer().getUsername());
        response.setTraineeUsername(training.getTrainee().getUsername());
        response.setType(training.getTrainingType().getName().toString());
        return response;
    }

}
