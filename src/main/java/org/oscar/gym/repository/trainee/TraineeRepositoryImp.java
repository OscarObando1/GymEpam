package org.oscar.gym.repository.trainee;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.User;
import org.oscar.gym.utils.IGenerator;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;


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

        return trainee;
    }

    @Override
    public Trainee findEntity(String username) {
        Trainee trainee = null;
        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :username";
            trainee = (Trainee) entityManager.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();

        }catch (Exception e){
            System.out.println("Not found with this "+username);
        }
        return trainee;
    }

    @Override
    @Transactional
    public Trainee updateEntity(TraineeDTO dto, long id) {
        Trainee trainee = null;
        trainee = entityManager.find(Trainee.class,id);
        if(trainee==null){
            throw new RuntimeException("Not Found");
        }
        try {
            trainee.setFirstName(dto.getFirstName());
            trainee.setLastName(dto.getLastName());
            trainee.setUsername(generator.createUser(dto.getFirstName(),dto.getLastName()));
            trainee.setAddress(dto.getAddress());
            trainee.setDateOfBirth(dto.getDateOfBirth());
            entityManager.merge(trainee);


        }catch (Exception e){
            e.printStackTrace();
        }
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
        }catch (Exception e){
            System.out.println("Does not found trainee with this username");
        }
    }

    @Override
    @Transactional
    public Trainee changeActive(long id) {
        Trainee trainee = null;
        trainee = entityManager.find(Trainee.class,id);
        if(trainee==null){
            throw new RuntimeException("Not Found");
        }
        try {
           if(trainee.getIsActive()){
               trainee.setIsActive(false);
           }else {
               trainee.setIsActive(true);
           }
            entityManager.merge(trainee);
        }catch (Exception e){
            e.printStackTrace();
        }
        return trainee;

    }


}
