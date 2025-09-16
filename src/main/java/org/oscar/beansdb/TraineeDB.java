package org.oscar.beansdb;

import jakarta.annotation.PostConstruct;
import org.oscar.model.Trainee;
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
import java.util.Random;

@Component
public class TraineeDB {

    public Map<Long, Trainee> traineeMap = new HashMap<>();

    @Value("${Trainee.data}")
    private String filepath;

    public static long counterSimulatedIdAutoIncrement =1;


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
                    trainee.setId(counterSimulatedIdAutoIncrement);
                    trainee.setFirstName(data[0]);
                    trainee.setLastName(data[1]);
                    String username = createUser(data[0],data[1]);
                    trainee.setUsername(username);
                    String pass = generatePass();
                    trainee.setPassword(pass);
                    trainee.setActive(true);
                    LocalDate birth = LocalDate.parse(data[2]);
                    trainee.setDateOfBirth(birth);
                    trainee.setAddress(data[3]);

                    traineeMap.put(counterSimulatedIdAutoIncrement,trainee);
                    counterSimulatedIdAutoIncrement++;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createUser(String firstName, String lastName){
        String username = firstName+"."+lastName;
        return username;
    }

    private String generatePass(){
        Random random = new Random();
        String password ="";
        for (int i = 0; i < 10; i++) {
            char temp =(char) (65 + random.nextInt(90 - 65 + 1));
            password+=temp;
        }
        return password;
    }

    public  Boolean isOccupied(String firstName, String lastName){
        boolean isOcu = traineeMap.values().stream().anyMatch(trainee -> trainee.getFirstName().equals(firstName)&&

                trainee.getLastName().equals(lastName));
        return isOcu;
    }



}
