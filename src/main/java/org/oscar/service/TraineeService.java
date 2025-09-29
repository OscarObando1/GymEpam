package org.oscar.service;

import jakarta.persistence.EntityManager;
import org.oscar.dtos.TraineeDTO;
import org.oscar.entity.Trainee;
import org.oscar.repository.TraineeRepository;
import org.oscar.utils.IGenerator;
import org.oscar.utils.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TraineeService implements TraineeRepository {

   private final EntityManager entityManager;
   private final Mapper mapper;
   private final IGenerator generator;

    public TraineeService(EntityManager entityManager, Mapper mapper, IGenerator generator) {
        this.entityManager = entityManager;
        this.mapper = mapper;
        this.generator = generator;
    }


    @Override
    public Trainee save(TraineeDTO dto) {
        Trainee trainee = mapper.mapTrainee(dto);
        trainee.setUsername(generator.createUser(dto.getFirstName(), dto.getLastName()));
        trainee.setPassword(generator.generatePass());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(trainee);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        return trainee;
    }
}
