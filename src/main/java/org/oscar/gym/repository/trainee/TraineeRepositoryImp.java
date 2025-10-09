package org.oscar.gym.repository.trainee;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.User;
import org.oscar.gym.utils.IGenerator;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;

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
    public Trainee saveEntity(TraineeDTO dto) {
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
            log.info("trainee not found with this "+username);
        }
        return trainee;
    }

    @Override
    @Transactional
    public Trainee updateEntity(TraineeDTO dto, long id) {
        Trainee trainee = null;
        trainee = entityManager.find(Trainee.class,id);
        if(trainee==null){
            throw new RuntimeException("Trainee not found with this id "+id);
        }
        trainee.setFirstName(dto.getFirstName());
        trainee.setLastName(dto.getLastName());
        trainee.setUsername(generator.createUser(dto.getFirstName(),dto.getLastName()));
        trainee.setAddress(dto.getAddress());
        trainee.setDateOfBirth(dto.getDateOfBirth());
        try {
            entityManager.merge(trainee);
        }catch (Exception e){
            e.printStackTrace();
            log.debug("something during update happend");
        }
          log.info("trainee updated");
          return trainee;
    }

    @Override
    @Transactional
    public void deleteEntity(String username) {
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

    }

    @Override
    @Transactional
    public Trainee changeActive(long id) {
        Trainee trainee = null;
        trainee = entityManager.find(Trainee.class,id);
        if(trainee==null){
            throw new RuntimeException("trainee not found with id "+id);
        }
        try {
           if(trainee.getIsActive()){
               trainee.setIsActive(false);
               log.info("trainee inactive");
           }else {
               trainee.setIsActive(true);
               log.info("trainee activated");
           }
            entityManager.merge(trainee);
        }catch (Exception e){
            e.printStackTrace();
            log.debug("something happened during change activity");
        }
        return trainee;

    }


}
