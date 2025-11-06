package org.oscar.gym.repository.trainee;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.ChangePassDTO;
import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateListTrainerRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.User;
import org.oscar.gym.exception.TraineeNotFoundException;
import org.oscar.gym.utils.IGenerator;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TraineeRepositoryImp implements TraineeRepository {
    private final EntityManager entityManager;

    public TraineeRepositoryImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public Trainee saveEntity(Trainee entity) {
        entityManager.persist(entity);
        log.info("trainee created "+entity.getUsername());
        return entity;
    }

    @Override
    public Trainee findEntity(String username) {
        Trainee trainee = null;
        try {
            String jpql = "SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username)";
            trainee = (Trainee) entityManager.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();

        }catch (Exception e){
            log.info("trainee not found with this username"+username);
        }
        return trainee;
    }

    @Override
    @Transactional
    public Trainee updateEntity(Trainee entity) {
        entityManager.merge(entity);
        log.info("trainee updated");
        return entity;
    }

    @Override
    @Transactional
    public Trainee deleteEntity(String username) {
        Trainee trainee = null;
        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :username";
            trainee = (Trainee) entityManager.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            entityManager.remove(trainee);
            log.info("Trainee deleted successfully");
        } catch (Exception e) {
            log.debug("No trainee found with username: " + username, e);
        }
       return trainee;
    }
}
