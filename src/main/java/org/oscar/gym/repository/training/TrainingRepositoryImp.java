package org.oscar.gym.repository.training;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.oscar.gym.dtos.TrainingDTO;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.Training;
import org.oscar.gym.repository.trainee.TraineeRepository;
import org.oscar.gym.repository.trainer.TrainerRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

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
        try {
            trainer=trainerRepository.findEntity(dto.getTrainerUsername());
        }catch (Exception e){
            throw new NoSuchElementException("trainer no encontrado user"+dto.getTrainerUsername());
        }
        Trainee trainee =null;
        try {
            trainee=traineeRepository.findEntity(dto.getTraineeUsername());
        }catch (Exception e){
            throw new NoSuchElementException("trainee no encontrado");
        }

        Training training = new Training();
        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setTrainingType(trainer.getSpecialization());
        training.setName(dto.getName());
        training.setTrainingDate(dto.getDate());
        training.setDurationTraining(dto.getDuration());
        entityManager.persist(training);

    }
}
