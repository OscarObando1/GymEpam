package org.oscar.service;

import org.oscar.beansdb.TraineeDB;
import org.oscar.dao.TraineeDAO;
import org.oscar.dtos.TraineeDTO;
import org.oscar.model.Trainee;
import org.oscar.utils.IGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TraineeService implements TraineeDAO {

    @Autowired
    TraineeDB traineeDB;

    private IGenerator generator;

    @Autowired
    public void setGenerator(IGenerator generator) {
        this.generator = generator;
    }

    @Override
    public Trainee createTrainee(TraineeDTO dto) {
        Trainee trainee = new Trainee();
        trainee.setFirstName(dto.getFirstName());
        trainee.setLastName(dto.getLastName());
        String username = generator.createUser(dto.getFirstName(), dto.getLastName(), traineeDB.traineeMap);
        trainee.setUsername(username);
        String pass = generator.generatePass();
        trainee.setPassword(pass);
        trainee.setActive(true);
        trainee.setDateOfBirth(dto.getDateOfBirth());
        trainee.setAddress(dto.getAddress());
        long id= TraineeDB.counterTraineeSimulatedIdAutoIncrement++;
        trainee.setId(id);
        traineeDB.traineeMap.put(id,trainee);
        return trainee;
    }

    @Override
    public Trainee updateTrainee(TraineeDTO dto,long id) {
        if (!traineeDB.traineeMap.containsKey(id)) {
            throw new NoSuchElementException("Not found with id selected");
        }
        Trainee traineeToUpdate = traineeDB.traineeMap.get(id);
        String username = generator.createUser(dto.getFirstName(), dto.getLastName(), traineeDB.traineeMap);
        traineeToUpdate.setFirstName(dto.getFirstName());
        traineeToUpdate.setLastName(dto.getLastName());

        traineeToUpdate.setUsername(username);
        traineeDB.traineeMap.put(id,traineeToUpdate);
        return traineeToUpdate;
    }

    @Override
    public void deleteTrainee(long id) {
        if (!traineeDB.traineeMap.containsKey(id)) {
            throw new NoSuchElementException("Not found with id selected");
        }
        traineeDB.traineeMap.remove(id);
    }

    @Override
    public Trainee selectTrainee(String name) {
        return traineeDB.traineeMap.values().stream().filter(user->user.getFirstName().equalsIgnoreCase(name)).findAny().orElseThrow(()-> new NoSuchElementException("Not found with name selected"));
    }
}
