package org.oscar.gym.repository.trainer;

import jakarta.persistence.EntityManager;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.TrainerDTO;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.TrainingType;
import org.oscar.gym.entity.User;
import org.oscar.gym.utils.IGenerator;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;

@Slf4j
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
    @Transactional
    public Trainer saveEntity(TrainerDTO dto) {

        TrainingType type = null;
        String jpql = "SELECT t FROM TrainingType t WHERE t.name = :name";


        type = entityManager.createQuery(jpql, TrainingType.class)
                .setParameter("name", dto.getSpecialization())
                .getSingleResult();

        Trainer trainer = mapper.mapTrainer(dto);
        trainer.setUsername(generator.createUser(dto.getFirstName(), dto.getLastName()));
        trainer.setPassword(generator.generatePass());
        trainer.setSpecialization(type);


            entityManager.persist(trainer);
            log.info("Trainer saved "+trainer.getUsername());
        return trainer;
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
        return trainer;
    }

    @Override
    @Transactional
    public Trainer updateEntity(TrainerDTO dto, long id) {
        Trainer trainer = null;
        trainer = entityManager.find(Trainer.class,id);
        if(trainer==null){
            throw new RuntimeException("Trainee not found with this id "+id);
        }
        trainer.setFirstName(dto.getFirstName());
        trainer.setLastName(dto.getLastName());
        trainer.setUsername(generator.createUser(dto.getFirstName(),dto.getLastName()));
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
    public Trainer changeActive(long id) {
        Trainer trainer = null;
        trainer = entityManager.find(Trainer.class,id);
        if(trainer==null){
            throw new RuntimeException("Not Found with this id "+id);
        }
        try {
            if(trainer.getIsActive()){
                trainer.setIsActive(false);
                log.info("trainer inactive");
            }else {
                trainer.setIsActive(true);
                log.info("trainer active");
            }
            entityManager.merge(trainer);
        }catch (Exception e){
            e.printStackTrace();
        }
        return trainer;
    }
}
