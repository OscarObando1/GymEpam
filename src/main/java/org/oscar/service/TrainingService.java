package org.oscar.service;

import org.oscar.beansdb.TraineeDB;
import org.oscar.beansdb.TrainerDB;
import org.oscar.beansdb.TrainingDB;
import org.oscar.dao.TrainingDAO;
import org.oscar.dtos.TrainingDTO;
import org.oscar.model.Training;
import org.oscar.model.TrainingType;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class TrainingService implements TrainingDAO {

    private final TraineeDB traineeDB;
    private final TrainerDB trainerDB;
    private final TrainingDB trainingDB;

    public TrainingService(TraineeDB traineeDB, TrainerDB trainerDB, TrainingDB trainiingDB) {
        this.traineeDB = traineeDB;
        this.trainerDB = trainerDB;
        this.trainingDB = trainiingDB;
    }


    @Override
    public Training createTraining(TrainingDTO dto) {
        long idTrainer = dto.getTrainerId();
        if(!trainerDB.trainerMap.containsKey(idTrainer)){
            throw new NoSuchElementException("trainer selected no found");
        }
        long idTrainee = dto.getTraineeId();
        if(!traineeDB.traineeMap.containsKey(idTrainee)){
            throw  new NoSuchElementException("trainee selected not found");
        }
        Training training = new Training();
        training.setTrainerId(idTrainer);
        training.setTraineeId(idTrainee);
        training.setName(dto.getName());
        TrainingType trainingType = trainerDB.trainerMap.get(idTrainer).getSpecialization();
        training.setTrainingType(trainingType);
        training.setTrainingDate(dto.getDate());
        training.setDurationTraining(dto.getDuration());
        long trainingId = TrainingDB.counterTrainingSimulatedIdAutoIncrement;
        TrainingDB.counterTrainingSimulatedIdAutoIncrement++;
        trainingDB.trainingMap.put(trainingId,training);
        return training;
    }

    @Override
    public Training selectTraining(String name) {
        return trainingDB.trainingMap.values().stream().filter(training -> training.getName().
                equalsIgnoreCase(name)).findAny().
                orElseThrow(()->new NoSuchElementException("selected trainig name not found"));
    }
}
