package org.oscar.repository.trainer;

import jakarta.persistence.EntityManager;


import org.oscar.dtos.TrainerDTO;


import org.oscar.entity.Trainer;

import org.oscar.entity.TrainingType;
import org.oscar.enums.TypeTraining;
import org.oscar.utils.IGenerator;
import org.oscar.utils.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TrainerRepositoryImp  implements TrainerRepository{
    private final EntityManager entityManager;
    private final Mapper mapper;
    private final IGenerator generator;

    public TrainerRepositoryImp(EntityManager entityManager, Mapper mapper, IGenerator generator) {
        this.entityManager = entityManager;
        this.mapper = mapper;
        this.generator = generator;
    }


    @Override
    public Trainer saveEntity(TrainerDTO dto) {
        Trainer trainer = mapper.mapTrainer(dto);
        trainer.setUsername(generator.createUser(dto.getFirstName(), dto.getLastName()));
        trainer.setPassword(generator.generatePass());
        TrainingType trainingType = new TrainingType();
        trainingType.setName(TypeTraining.valueOf(dto.getSpecialization()));
        trainer.setSpecialization(trainingType);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(trainer);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        return trainer;
    }

    @Override
    public Trainer findEntity(String username) {
        return null;
    }

    @Override
    public Trainer updateEntity(TrainerDTO dto, long id) {
        return null;
    }

    @Override
    public void deleteEntity(String username) {

    }
}
