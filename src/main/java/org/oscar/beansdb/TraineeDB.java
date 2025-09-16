package org.oscar.beansdb;

import jakarta.annotation.PostConstruct;
import org.oscar.model.Trainee;
import org.oscar.utils.IGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class TraineeDB {

    public Map<Long, Trainee> traineeMap = new HashMap<>();
    private IGenerator generator;

    @Value("${Trainee.data}")
    private String filepath;

    public static long counterTraineeSimulatedIdAutoIncrement =1;

    @Autowired
    public void setGenerator(IGenerator generator) {
        this.generator = generator;
    }

    @PostConstruct
    public void init() {
        loadDataCSV(this.filepath);
    }

    private void loadDataCSV(String filepath) {
        Path rutaArchivo = Paths.get(filepath);

        try (BufferedReader br = Files.newBufferedReader(rutaArchivo)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] data = linea.split(",");
                if(data.length==4){
                    Trainee trainee = new Trainee();
                    trainee.setId(counterTraineeSimulatedIdAutoIncrement);
                    trainee.setFirstName(data[0]);
                    trainee.setLastName(data[1]);
                    String username = generator.createUser(data[0],data[1],traineeMap);
                    trainee.setUsername(username);
                    String pass = generator.generatePass();
                    trainee.setPassword(pass);
                    trainee.setActive(true);
                    LocalDate birth = LocalDate.parse(data[2]);
                    trainee.setDateOfBirth(birth);
                    trainee.setAddress(data[3]);

                    traineeMap.put(counterTraineeSimulatedIdAutoIncrement,trainee);
                    counterTraineeSimulatedIdAutoIncrement++;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
