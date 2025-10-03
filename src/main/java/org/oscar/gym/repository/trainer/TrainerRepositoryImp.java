package org.oscar.gym.repository.trainer;

import jakarta.persistence.EntityManager;


import org.oscar.gym.dtos.TrainerDTO;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.TrainingType;
import org.oscar.gym.entity.User;
import org.oscar.gym.utils.IGenerator;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;

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
    public Trainer saveEntity(TrainerDTO dto) {
        System.out.println("Specialization: " + dto.getSpecialization());


        TrainingType type = null;
        String jpql = "SELECT t FROM TrainingType t WHERE t.name = :name";


        type = entityManager.createQuery(jpql, TrainingType.class)
                .setParameter("name", dto.getSpecialization())
                .getSingleResult();

        Trainer trainer = mapper.mapTrainer(dto);
        trainer.setUsername(generator.createUser(dto.getFirstName(), dto.getLastName()));
        trainer.setPassword(generator.generatePass());
        trainer.setSpecialization(type);

            entityManager.getTransaction().begin();
            entityManager.persist(trainer);
            entityManager.getTransaction().commit();

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
            e.printStackTrace();
        }
        return trainer;
    }

    @Override
    public Trainer updateEntity(TrainerDTO dto, long id) {
        Trainer trainer = null;
        trainer = entityManager.find(Trainer.class,id);
        if(trainer==null){
            throw new RuntimeException("Not Found");
        }
        try {
            trainer.setFirstName(dto.getFirstName());
            trainer.setLastName(dto.getLastName());
            trainer.setUsername(generator.createUser(dto.getFirstName(),dto.getLastName()));
            entityManager.getTransaction().begin();
            entityManager.merge(trainer);
            entityManager.getTransaction().commit();

        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return trainer;
    }

    @Override
    public void deleteEntity(String username) {
        Trainer trainer = null;
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        try {

            trainer = (Trainer) entityManager.createQuery(jpql, User.class)
                    .setParameter("username", username )
                    .getSingleResult();
            entityManager.getTransaction().begin();
            entityManager.remove(trainer);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Does not found trainer with this username");
        }

    }

    @Override
    public void assignTraineeEntity(String userTrainer,String userTrainee){
        Trainer trainer = null;
        String jpql = "SELECT u FROM User u WHERE u.username = :username";

            trainer = (Trainer) entityManager.createQuery(jpql, User.class)
                    .setParameter("username", userTrainer )
                    .getSingleResult();
        Trainee trainee = null;
        String jpql2 = "SELECT u FROM User u WHERE u.username = :username";
            trainee = (Trainee) entityManager.createQuery(jpql, User.class)
                    .setParameter("username", userTrainee )
                    .getSingleResult();

        trainer.getTrainees().add(trainee);
        trainee.getTrainers().add(trainer);
        entityManager.getTransaction().begin();
        entityManager.merge(trainer);
        entityManager.merge(trainee);
        entityManager.getTransaction().commit();

    }
}
