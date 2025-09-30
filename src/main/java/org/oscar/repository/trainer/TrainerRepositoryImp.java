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

        TrainingType type = null;
        String jpql = "SELECT t FROM TrainingType t WHERE t.name = :name";


        type = entityManager.createQuery(jpql, TrainingType.class)
                .setParameter("name", dto.getSpecialization())
                .getSingleResult();
        System.out.println(type);

        Trainer trainer = mapper.mapTrainer(dto);
        trainer.setUsername(generator.createUser(dto.getFirstName(), dto.getLastName()));
        trainer.setPassword(generator.generatePass());
        trainer.setSpecialization(type);

            entityManager.getTransaction().begin();
            entityManager.persist(trainer);
            entityManager.getTransaction().commit();

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
