package org.oscar.beansdb;

import jakarta.annotation.PostConstruct;

import org.oscar.model.Trainer;
import org.oscar.model.TrainingType;
import org.oscar.utils.IGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class TrainerDB {
    public Map<Long, Trainer> trainerMap = new HashMap<>();
    private IGenerator generator;

    @Value("${Trainer.data}")
    private String filepath;

    public static long counterTrainerSimulatedIdAutoIncrement =1;

    @Autowired
    public void setGenerator(IGenerator generator) {
        this.generator = generator;
    }

    @PostConstruct
    public void init() {
        loadDataCSV(this.filepath);
    }

    private void loadDataCSV(String filepath) {
        Path file = Paths.get(filepath);

        try (BufferedReader br = Files.newBufferedReader(file)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] data = linea.split(",");
                if(data.length==3){
                   Trainer trainer = new Trainer();
                   trainer.setId(counterTrainerSimulatedIdAutoIncrement);
                   trainer.setFirstName(data[0]);
                   trainer.setLastName(data[1]);
                   String username = generator.createUser(data[0],data[1],trainerMap);
                   trainer.setUsername(username);
                   String pass = generator.generatePass();
                   trainer.setPassword(pass);
                   trainer.setActive(true);
                   trainer.setSpecialization(TrainingType.valueOf(data[2]));
                   trainerMap.put(counterTrainerSimulatedIdAutoIncrement,trainer);
                   counterTrainerSimulatedIdAutoIncrement++;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
