package org.oscar.gym.repository.training;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.request.training.TraineeTrainingsListResquest;
import org.oscar.gym.dtos.request.training.TrainerTrainingsListRequest;
import org.oscar.gym.dtos.request.training.TrainingDTO;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.Training;
import org.oscar.gym.entity.TrainingType;
import org.oscar.gym.exception.TraineeNotFoundException;
import org.oscar.gym.exception.TrainerNotFoundException;
import org.oscar.gym.repository.trainee.TraineeRepository;
import org.oscar.gym.repository.trainer.TrainerRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Component
public class TrainingRepositoryImp implements TrainingRepository{

    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final EntityManager entityManager;

    public TrainingRepositoryImp(TrainerRepository trainerRepository, TraineeRepository traineeRepository, EntityManager entityManager) {
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public void createTraining(TrainingDTO dto) {
        Trainer trainer= null;
        trainer=trainerRepository.findEntity(dto.getTrainerUsername());
        if(trainer==null){
            throw new TrainerNotFoundException("trainer not found with this username "+ dto.getTrainerUsername());
        }
        Trainee trainee =null;
        trainee=traineeRepository.findEntity(dto.getTraineeUsername());
        if(trainee==null){
            throw new TraineeNotFoundException("trainee not found with this username "+ dto.getTraineeUsername());
        }

        trainer.getTrainees().add(trainee);
        trainee.getTrainers().add(trainer);


        Training training = new Training();
        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setTrainingType(trainer.getSpecialization());
        training.setName(dto.getName());
        training.setTrainingDate(dto.getDate());
        training.setDurationTraining(dto.getDuration());
        entityManager.persist(training);
        log.info("training was created");
    }

    @Override
    public List<Training> getTrainingWithTrainee(String username) {
        Trainee trainee =null;
        List<Training> list = null;
        try {
            trainee=traineeRepository.findEntity(username);
        }catch (Exception e){
            throw new NoSuchElementException("trainee not found with the user " +username);
        }
        long id = trainee.getId();

        try {
            String jpql = "SELECT t FROM Training t WHERE t.trainee.id = :id";
            list = entityManager.createQuery(jpql, Training.class)
                    .setParameter("id", id)
                    .getResultList();

        }catch (Exception e){
            log.info("trainings not found with this "+username);
        }


        return list;
    }

    @Override
    @Transactional
    public List<Training> getTrainingWithTraineer(String username) {
        Trainer trainer =null;
        List<Training> list = null;
        try {
            trainer=trainerRepository.findEntity(username);
        }catch (Exception e){
            throw new NoSuchElementException("trainer not found with the user " +username);
        }
        long id = trainer.getId();

        try {
            String jpql = "SELECT t FROM Training t WHERE t.trainer.id = :id";
            list = entityManager.createQuery(jpql, Training.class)
                    .setParameter("id", id)
                    .getResultList();

        }catch (Exception e){
            log.info("trainings not found with this "+username);
        }


        return list;
    }

    @Override
    public List<TrainingType> getTypes() {
        List<TrainingType> list = null;
        try {
            list = entityManager.createQuery("SELECT t FROM TrainingType t", TrainingType.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Training> getTraineeTrainings(TraineeTrainingsListResquest dto) {
        Trainee trainee = traineeRepository.findEntity(dto.getUsername());
        if(trainee==null){
            throw new TraineeNotFoundException("trainee not found with this username "+ dto.getUsername());
        }

        StringBuilder jpql = new StringBuilder(
                "SELECT tr FROM Training tr WHERE tr.trainee.username = :username"
        );

        Map<String, Object> params = new HashMap<>();
        params.put("username", dto.getUsername());


        if (dto.getDateFrom() != null) {
            jpql.append(" AND tr.trainingDate >= :dateFrom");
            params.put("dateFrom", dto.getDateFrom());
        }


        if (dto.getDateTo() != null) {
            jpql.append(" AND tr.trainingDate <= :dateTo");
            params.put("dateTo", dto.getDateTo());
        }


        if (dto.getTrainerName() != null && !dto.getTrainerName().isBlank()) {
            jpql.append(" AND LOWER(CONCAT(tr.trainer.firstName, ' ', tr.trainer.lastName)) LIKE LOWER(CONCAT('%', :trainerName, '%'))");
            params.put("trainerName", dto.getTrainerName());
        }

        jpql.append(" ORDER BY tr.trainingDate DESC");

        TypedQuery<Training> query = entityManager.createQuery(jpql.toString(), Training.class);
        params.forEach(query::setParameter);

        return query.getResultList();
    }

    public List<Training> getTrainerTrainings(TrainerTrainingsListRequest dto) {
        Trainer trainer = trainerRepository.findEntity(dto.getUsername());
        if(trainer==null){
            throw new TrainerNotFoundException("trainer not found with this username "+ dto.getUsername());
        }

        StringBuilder jpql = new StringBuilder(
                "SELECT tr FROM Training tr WHERE tr.trainer.username = :username"
        );

        Map<String, Object> params = new HashMap<>();
        params.put("username", dto.getUsername());


        if (dto.getDateFrom() != null) {
            jpql.append(" AND tr.trainingDate >= :dateFrom");
            params.put("dateFrom", dto.getDateFrom());
        }


        if (dto.getDateTo() != null) {
            jpql.append(" AND tr.trainingDate <= :dateTo");
            params.put("dateTo", dto.getDateTo());
        }


        if (dto.getTraineeName() != null && !dto.getTraineeName().isBlank()) {
            jpql.append(" AND LOWER(CONCAT(tr.trainee.firstName, ' ', tr.trainee.lastName)) LIKE LOWER(CONCAT('%', :traineeName, '%'))");
            params.put("traineeName", dto.getTraineeName());
        }

        jpql.append(" ORDER BY tr.trainingDate DESC");

        TypedQuery<Training> query = entityManager.createQuery(jpql.toString(), Training.class);
        params.forEach(query::setParameter);

        return query.getResultList();
    }

}
