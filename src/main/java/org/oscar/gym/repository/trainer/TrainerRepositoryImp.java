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
            log.info("trainee not found with this "+username);
        }
        if(trainer==null){
            throw new TraineeNotFoundException("Trainer not found with this username "+username);
        }
        return trainer;
    }

    @Override
    @Transactional
    public Trainer updateEntity(TrainerUpdateRequest dto) {
        Trainer trainer = null;
        trainer = findEntity(dto.getUsername());
        if(trainer==null){
            throw new TraineeNotFoundException("Trainer not found with this username "+dto.getUsername());
        }
        trainer.setFirstName(dto.getFirstName());
        trainer.setLastName(dto.getLastName());
        trainer.setUsername(dto.getUsername());
        try {
            entityManager.merge(trainer);
        }catch (Exception e){
            e.printStackTrace();
            log.debug("something during update happend");
        }
        log.info("trainer updated");
        return trainer;
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
    public void assignTraineeEntity(String userTrainer,String userTrainee){
        Trainer trainer = null;
        String jpql = "SELECT u FROM User u WHERE u.username = :username";

        try {
            trainer = (Trainer) entityManager.createQuery(jpql, User.class)
                    .setParameter("username", userTrainer )
                    .getSingleResult();
        } catch (Exception e) {
            log.info("trainer not found with this username "+ userTrainer);
        }
        Trainee trainee = null;
        try {
            String jpql2 = "SELECT u FROM User u WHERE u.username = :username";
            trainee = (Trainee) entityManager.createQuery(jpql, User.class)
                    .setParameter("username", userTrainee )
                    .getSingleResult();
        } catch (Exception e) {
            log.info("trainee not found with this username "+userTrainee);
        }

        trainer.getTrainees().add(trainee);
        trainee.getTrainers().add(trainer);
        entityManager.merge(trainer);
        entityManager.merge(trainee);
        log.info("The trainer "+trainer.getUsername()+ " selected the trainee " +trainee.getUsername());
    }

    @Override
    public void changeActive(UserActivateDeActivate dto) {
        Trainer trainer = null;
        trainer = findEntity(dto.getUsername());
        if(trainer==null){
            throw new RuntimeException("trainee not found with username "+dto.getUsername());
        }
        try {
            if(trainer.getIsActive()&&dto.getIsActive()){
                log.info("trainer is already active");
            }
            if(trainer.getIsActive()&& !dto.getIsActive()){
                trainer.setIsActive(false);
                entityManager.merge(trainer);
                log.info("trainer is deactivated");
            }
            if(!trainer.getIsActive()&&dto.getIsActive()){
                trainer.setIsActive(true);
                entityManager.merge(trainer);
                log.info("trainer is active");
            }
            if(!trainer.getIsActive()&& !dto.getIsActive()){
                log.info("trainer is already deactivated");
            }

        }catch (Exception e){
            log.debug("something happened during change activity {}", e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void updatePass(ChangePassDTO dto) {
        Trainer trainer = null;
        trainer= findEntity(dto.getUsername());
        trainer.setPassword(dto.getNewPass());
        entityManager.merge(trainer);
    }

    @Override
    @Transactional
    public List<Trainer> getTrainerWithoutTrainee(String username) {
        return entityManager.createQuery(queryTrainerWithtrianee, Trainer.class)
                .setParameter("username", username)
                .getResultList();
    }
}
