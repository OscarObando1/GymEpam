package org.oscar.gym.utils;


import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.response.trainee.TraineeResponse;
import org.oscar.gym.dtos.response.TrainerResponsetemp;
import org.oscar.gym.dtos.response.TrainingResponse;
import org.oscar.gym.dtos.response.trainee.TraineeRegistrationResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponseExtend;
import org.oscar.gym.dtos.response.trainer.TrainerRegistrationResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponseExtend;
import org.oscar.gym.dtos.response.training.TrainingResponseAll;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.Training;
import org.oscar.gym.entity.TrainingType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapper {


    public Trainee mapTraineeExtend(TraineeDTO dto){
        Trainee trainee = new Trainee();
        trainee.setFirstName(dto.getFirstName());
        trainee.setLastName(dto.getLastName());
        trainee.setAddress(dto.getAddress());
        trainee.setDateOfBirth(dto.getDateOfBirth());
        trainee.setIsActive(true);
        return trainee;
    }

    public Trainee mapTrainee(TraineeRegistrationRequest dto){
        Trainee trainee = new Trainee();
        trainee.setFirstName(dto.getFirstName());
        trainee.setLastName(dto.getLastName());
        trainee.setAddress(dto.getAddress());
        trainee.setDateOfBirth(dto.getDateOfBirth());
        trainee.setIsActive(true);
        return trainee;
    }

    public TraineeRegistrationResponse mapTraineeResponseCreate(Trainee trainee){
        TraineeRegistrationResponse response = new TraineeRegistrationResponse();
        response.setUsername(trainee.getUsername());
        response.setPassword(trainee.getPassword());
        return response;
    }

    public TrainerResponse mapTrainerResponseGet(Trainer trainer){
        TrainerResponse response = new TrainerResponse();
        response.setUsername(trainer.getUsername());
        response.setFirstName(trainer.getFirstName());
        response.setLastName(trainer.getLastName());
        response.setSpecialization(trainer.getSpecialization().getName().name());
        return  response;
    }

    public TraineeResponseExtend mapTraineeResponseGet(Trainee trainee){
        TraineeResponseExtend response = new TraineeResponseExtend();
        response.setFirstName(trainee.getFirstName());
        response.setLastName(trainee.getLastName());
        response.setDateOfBirth(trainee.getDateOfBirth());
        response.setAddress(trainee.getAddress());
        response.setActive(trainee.getIsActive());
        List<TrainerResponse> list = trainee.getTrainers().stream().map(e->mapTrainerResponseGet(e)).toList();
        response.setListTrainers(list);
        return response;
    }


    public TraineeResponse mapTraineeResponseExtendGetMethod(Trainee trainee){
        TraineeResponse response = new TraineeResponse();
        response.setFirstName(trainee.getFirstName());
        response.setLastName(trainee.getLastName());
        response.setUsername(trainee.getUsername());
        return response;
    }

    public TrainerResponseExtend mapTrainerResponseGetMethod(Trainer trainer){
        TrainerResponseExtend response = new TrainerResponseExtend();
        response.setFirstName(trainer.getFirstName());
        response.setLastName(trainer.getLastName());
        response.setSpecialization(trainer.getSpecialization().getName().name());
        response.setActive(trainer.getIsActive());
        List<TraineeResponse> list = trainer.getTrainees().stream().map(e->mapTraineeResponseExtendGetMethod(e)).toList();
        response.setTraineeResponseList(list);
        return response;
    }

    public TrainerResponseExtend mapTrainerResponseUpdateMethod(Trainer trainer){
        TrainerResponseExtend response = new TrainerResponseExtend();
        response.setUsername(trainer.getUsername());
        response.setFirstName(trainer.getFirstName());
        response.setLastName(trainer.getLastName());
        response.setSpecialization(trainer.getSpecialization().getName().name());
        response.setActive(trainer.getIsActive());
        List<TraineeResponse> list = trainer.getTrainees().stream().map(e->mapTraineeResponseExtendGetMethod(e)).toList();
        response.setTraineeResponseList(list);
        return response;
    }

    public Trainer mapTrainer(TrainerRegistrationRequest dto){
        Trainer trainer = new Trainer();
        trainer.setFirstName(dto.getFirstName());
        trainer.setLastName(dto.getLastName());
        trainer.setIsActive(true);
        return trainer;

    }

    public TrainerRegistrationResponse mapTrainerResponse(Trainer trainer){
        TrainerRegistrationResponse response = new TrainerRegistrationResponse();
        response.setUsername(trainer.getUsername());
        response.setPassword(trainer.getPassword());
        return response;
    }

    public TrainerResponsetemp mapTrainerResponsetemp(Trainer trainer){
        TrainerResponsetemp response = new TrainerResponsetemp();
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

    public TrainingResponseAll mapTrainingResponseType(TrainingType trainingType){
        TrainingResponseAll response = new TrainingResponseAll();
        response.setTrainingType(trainingType.getName().name());
        response.setTrainingTypeId(trainingType.getId());
        return response;
    }

}
