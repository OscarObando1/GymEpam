package org.oscar.service;

import jakarta.persistence.EntityManager;
import org.oscar.dtos.TraineeDTO;
import org.oscar.entity.Trainee;
import org.oscar.repository.TraineeRepository;
import org.oscar.utils.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TraineeService implements TraineeRepository {

   private final EntityManager entityManager;
   private final Mapper mapper;

    public TraineeService(EntityManager entityManager, Mapper mapper) {
        this.entityManager = entityManager;
        this.mapper = mapper;
    }


    @Override
    public Trainee save(TraineeDTO dto) {
        Trainee trainee = mapper.mapTrainee(dto);
        entityManager.getTransaction().begin();
        entityManager.persist(trainee);
        entityManager.getTransaction().commit();
        return trainee;
    }
}
