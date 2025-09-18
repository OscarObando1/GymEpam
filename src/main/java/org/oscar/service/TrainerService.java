package org.oscar.service;

import org.oscar.beansdb.TrainerDB;
import org.oscar.dao.TrainerDAO;
import org.oscar.dtos.TrainerDTO;
import org.oscar.model.Trainer;
import org.oscar.utils.IGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class TrainerService implements TrainerDAO {

    @Autowired
    TrainerDB trainerDB;

    private IGenerator generator;

    @Autowired
    public void setGenerator(IGenerator generator) {
        this.generator = generator;
    }

    @Override
    public Trainer createTrainer(TrainerDTO dto) {
        Trainer trainer = new Trainer();
        long id = TrainerDB.counterTrainerSimulatedIdAutoIncrement++;
        trainer.setId(id);
        trainer.setFirstName(dto.getFisrtName());
        trainer.setLastName(dto.getLastName());
        String username = generator.createUser(dto.getFisrtName(), dto.getLastName(), trainerDB.trainerMap);
        trainer.setUsername(username);
        String pass = generator.generatePass();
        trainer.setPassword(pass);
        trainer.setActive(true);
        trainer.setSpecialization(dto.getSpecialzation());
        trainerDB.trainerMap.put(id,trainer);
        return  trainer;
    }

    @Override
    public Trainer updateTrainer(TrainerDTO dto, long id) {
        if (!trainerDB.trainerMap.containsKey(id)) {
            throw new NoSuchElementException("Not found with id selected");
        }
        String username = generator.createUser(dto.getFisrtName(), dto.getLastName(), trainerDB.trainerMap);
        String pass = generator.generatePass();
        Trainer toUpdateTrainer = trainerDB.trainerMap.get(id);
        toUpdateTrainer.setSpecialization(dto.getSpecialzation());
        toUpdateTrainer.setFirstName(dto.getFisrtName());
        toUpdateTrainer.setLastName(dto.getLastName());
        toUpdateTrainer.setUsername(username);
        toUpdateTrainer.setPassword(pass);
        trainerDB.trainerMap.put(id,toUpdateTrainer);
        return toUpdateTrainer;
    }

    @Override
    public void deleteTrainer(long id) {
        if (!trainerDB.trainerMap.containsKey(id)) {
            throw new NoSuchElementException("Not found with id selected");
        }
        trainerDB.trainerMap.remove(id);

    }

    @Override
    public Trainer selectTrainer(String name) {
        return trainerDB.trainerMap.values().stream().filter(user->user.getFirstName().equalsIgnoreCase(name)).findAny().orElseThrow(()-> new NoSuchElementException("Not found with name selected"));
    }
}
