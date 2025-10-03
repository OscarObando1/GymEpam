package org.oscar.gym.service;


import org.oscar.gym.dtos.TrainerDTO;
import org.oscar.gym.dtos.response.TrainerResponse;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.repository.trainer.TrainerRepository;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class TrainerSercice{
    private final TrainerRepository repository;
    private final Mapper mapper;


    public TrainerSercice(TrainerRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void saveTrainer(TrainerDTO dto){
        repository.saveEntity(dto);
    }

    public TrainerResponse findTrainer(String username){
        Trainer trainer = repository.findEntity(username);
        if(trainer==null){
            throw new NoSuchElementException("Does not found trainer with this username");
        }
        return mapper.mapTrainerResponse(trainer);
    }

    public TrainerResponse updateTrainer(TrainerDTO trainerDTO, long id) {
        try {
            Trainer trainer = repository.updateEntity(trainerDTO, id);
            return mapper.mapTrainerResponse(trainer);
        } catch (Exception e) {
            System.out.println("Trainer not found with id " + id);
        }
        return null;
    }

    public void deleteTrainer(String username){
        repository.deleteEntity(username);
    }

    public void asignTrainee(String userTrainer, String userTrainee){
        repository.assignTraineeEntity(userTrainer,userTrainee);
    }
}
