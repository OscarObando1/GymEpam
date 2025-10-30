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
    private final Mapper mapper;
    private final IGenerator generator;

    public TraineeRepositoryImp(EntityManager entityManager, Mapper mapper, IGenerator generator) {
        this.entityManager = entityManager;
        this.mapper = mapper;
        this.generator = generator;
    }


    @Override
    @Transactional
    public Trainee saveEntity(TraineeRegistrationRequest dto) {
        Trainee trainee = mapper.mapTrainee(dto);
        trainee.setUsername(generator.createUser(dto.getFirstName(), dto.getLastName()));
        trainee.setPassword(generator.generatePass());
        entityManager.persist(trainee);
        log.info("trainee created "+trainee.getUsername());
        return trainee;
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
    public Trainee updateEntity(TraineeUpdateRequest dto) {
        Trainee trainee = null;
        trainee = findEntity(dto.getUsername());
        if(trainee==null){
            throw  new TraineeNotFoundException("trainee not found with this username "+ dto.getUsername());
        }
        trainee.setFirstName(dto.getFirstName());
        trainee.setLastName(dto.getLastName());
        trainee.setUsername(generator.createUser(dto.getFirstName(),dto.getLastName()));
        trainee.setAddress(dto.getAddress());
        trainee.setDateOfBirth(dto.getDateOfBirth());
        trainee.setIsActive(dto.getIsActive());
        try {
            entityManager.merge(trainee);
        }catch (Exception e){
            e.printStackTrace();
            log.debug("something during update happened");
        }
          log.info("trainee updated");
          return trainee;
    }

    @Override
    @Transactional
    public Trainee deleteEntity(String username) {
        Trainee trainee = null;
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        try {

            trainee = (Trainee) entityManager.createQuery(jpql, User.class)
                    .setParameter("username", username )
                    .getSingleResult();
                entityManager.remove(trainee);
                log.info("trainee deleted");
        }catch (Exception e){
            log.info("Does not found trainee with this username "+username);
        }
       return trainee;
    }

    @Override
    @Transactional
    public void changeActive(UserActivateDeActivate dto) {
        Trainee trainee = null;
        trainee = findEntity(dto.getUsername());
        if(trainee==null){
            throw new RuntimeException("trainee not found with username "+dto.getUsername());
        }
        try {
           if(trainee.getIsActive()&&dto.getIsActive()){
               log.info("trainee is already active");
           }
           if(trainee.getIsActive()&& !dto.getIsActive()){
               trainee.setIsActive(false);
               entityManager.merge(trainee);
               log.info("trainee is deactivated");
           }
            if(!trainee.getIsActive()&&dto.getIsActive()){
                trainee.setIsActive(true);
                entityManager.merge(trainee);
                log.info("trainee is active");
            }
            if(!trainee.getIsActive()&& !dto.getIsActive()){
                log.info("trainee is already deactivated");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.debug("something happened during change activity");
        }

    }

    @Override
    @Transactional
    public void updatePass(ChangePassDTO dto) {
        Trainee trainee = null;
        trainee= findEntity(dto.getUsername());
        trainee.setPassword(dto.getNewPass());
        entityManager.merge(trainee);


    }
    @Override
    @Transactional
    public Trainee updateListTrainer(String username, List<Trainer> list){
        Trainee trainee = null;
        trainee = findEntity(username);

        for (Trainer oldTrainer : new ArrayList<>(trainee.getTrainers())) {
            oldTrainer.getTrainees().remove(trainee);
        }

        trainee.getTrainers().clear();

        for (Trainer newTrainer : list) {
            trainee.getTrainers().add(newTrainer);
            newTrainer.getTrainees().add(trainee);
        }
        try {
            entityManager.merge(trainee);
        }catch (Exception e){
            e.printStackTrace();
            log.debug("something during update happened");
        }
        return trainee;
    }


}
