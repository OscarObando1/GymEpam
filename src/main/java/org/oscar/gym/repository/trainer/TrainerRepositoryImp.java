package org.oscar.gym.repository.trainer;

import jakarta.persistence.EntityManager;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.ChangePassDTO;
import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerUpdateRequest;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.TrainingType;
import org.oscar.gym.entity.User;
import org.oscar.gym.exception.TraineeNotFoundException;
import org.oscar.gym.utils.IGenerator;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TrainerRepositoryImp  implements TrainerRepository{
    private final EntityManager entityManager;
    private static final String queryTrainerWithtrianee = """
                            SELECT t
                            FROM Trainer t
                            WHERE t.id NOT IN (
                                SELECT tr.id
                                FROM Trainee tn
                                JOIN tn.trainers tr
                                WHERE tn.username = :username
                            )
                        """;

    public TrainerRepositoryImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public Trainer saveEntity(Trainer entity) {
            entityManager.persist(entity);
            log.info("Trainer saved "+entity.getUsername());
        return entity;
    }

    @Override
    public Trainer findEntity(String username) {
        Trainer trainer = null;
        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :username";
            trainer = (Trainer) entityManager.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }catch (Exception e){
            log.info("trainer not found with this "+username);
        }
        return trainer;
    }

    @Override
    @Transactional
    public Trainer updateEntity(Trainer entity) {
        entityManager.merge(entity);
        log.info("trainer updated");
        return entity;
    }

    @Override
    @Transactional
    public void deleteEntity(String username) {
        Trainer trainer = null;
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        try {

            trainer = (Trainer) entityManager.createQuery(jpql, User.class)
                    .setParameter("username", username )
                    .getSingleResult();
            entityManager.remove(trainer);
            log.info("trainer deleted");
        }catch (Exception e){
            log.info("Does not found trainer with this username "+username);
        }

    }


    @Override
    @Transactional
    public List<Trainer> getTrainerWithoutTrainee(String username) {
        return entityManager.createQuery(queryTrainerWithtrianee, Trainer.class)
                .setParameter("username", username)
                .getResultList();
    }
}
