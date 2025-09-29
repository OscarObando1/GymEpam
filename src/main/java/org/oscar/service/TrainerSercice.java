package org.oscar.service;

import org.oscar.dtos.TrainerDTO;
import org.oscar.repository.trainer.TrainerRepository;
import org.oscar.utils.Mapper;
import org.springframework.stereotype.Component;

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
}
