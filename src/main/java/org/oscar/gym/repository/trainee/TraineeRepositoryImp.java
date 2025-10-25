package org.oscar.gym.repository.trainee;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.request.temp.ChangePassDTO;
import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.dtos.request.temp.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
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
            log.info("trainee not found with this "+username);
        }
        return trainee;
    }

    @Override
    @Transactional
    public Trainee updateEntity(TraineeUpdateRequest dto, long id) {
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
        trainee.setIsActive(dto.isActive());
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
    public void changeActive(UserActivateDeActivate dto) {
        Trainee trainee = null;
        trainee = findEntity(dto.getUsername());
        if(trainee==null){
            throw new RuntimeException("trainee not found with username "+dto.getUsername());
        }
        try {
           if(trainee.getIsActive()&&dto.isActive()){
               log.info("trainee is already active");
           }
           if(trainee.getIsActive()&& !dto.isActive()){
               entityManager.merge(trainee);
               log.info("trainee is deactivated");
           }
            if(!trainee.getIsActive()&&dto.isActive()){
                entityManager.merge(trainee);
                log.info("trainee is active");
            }
            if(!trainee.getIsActive()&& !dto.isActive()){
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


}
