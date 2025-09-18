package org.oscar.beansdb;

import jakarta.annotation.PostConstruct;
import org.oscar.model.Trainee;
import org.oscar.model.Training;
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
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class TrainingDB {
    public Map<Long, Training> trainingMap = new HashMap<>();
    private IGenerator generator;

    @Value("${Training.data}")
    private String filepath;

    public static long counterTrainingSimulatedIdAutoIncrement =1;

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
                if(data.length==6){
                    Training training = new Training();
                    training.setId(counterTrainingSimulatedIdAutoIncrement);
                    training.setTraineeId(Long.valueOf(data[0]));
                    training.setTrainerId(Long.valueOf(data[1]));
                    training.setName(data[2]);
                    training.setTrainingType(TrainingType.valueOf(data[3]));
                    training.setTrainingDate(LocalDate.parse(data[4]));
                    training.setDurationTraining(Duration.parse(data[5]));
                    trainingMap.put(counterTrainingSimulatedIdAutoIncrement,training);
                    counterTrainingSimulatedIdAutoIncrement++;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
