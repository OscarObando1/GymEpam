package org.oscar.repository.trainee;

import jakarta.persistence.EntityManager;
import org.oscar.dtos.TraineeDTO;
import org.oscar.entity.Trainee;
import org.oscar.entity.User;
import org.oscar.utils.IGenerator;
import org.oscar.utils.Mapper;
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
    public Trainee saveEntity(TraineeDTO dto) {
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

    @Override
    public Trainee findEntity(String username) {
        Trainee trainee = null;
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        try {

            trainee = (Trainee) entityManager.createQuery(jpql, User.class)
                    .setParameter("username", username )
                    .getSingleResult();
            if(trainee!=null){
                return trainee;
            }
        }catch (Exception e){
            System.out.println("Does not found trainee with this username");
        }
        return trainee;
    }

    @Override
    public Trainee updateEntity(TraineeDTO dto, long id) {
        Trainee trainee = null;
        trainee = entityManager.find(Trainee.class,id);
        try {
            trainee.setFirstName(dto.getFirstName());
            trainee.setLastName(dto.getLastName());
            trainee.setUsername(generator.createUser(dto.getFirstName(),dto.getLastName()));
            trainee.setAddress(dto.getAddress());
            trainee.setDateOfBirth(dto.getDateOfBirth());
            entityManager.getTransaction().begin();
            entityManager.merge(trainee);
            entityManager.getTransaction().commit();

        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
          return trainee;
    }

    @Override
    public void deleteEntity(String username) {
        Trainee trainee = null;
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        try {

            trainee = (Trainee) entityManager.createQuery(jpql, User.class)
                    .setParameter("username", username )
                    .getSingleResult();
                entityManager.getTransaction().begin();
                entityManager.remove(trainee);
                entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Does not found trainee with this username");
        }
    }


}
