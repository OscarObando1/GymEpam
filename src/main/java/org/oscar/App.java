package org.oscar;

import org.oscar.beansdb.TraineeDB;
import org.oscar.beansdb.TrainerDB;
import org.oscar.beansdb.TrainingDB;
import org.oscar.dtos.TraineeDTO;
import org.oscar.dtos.TrainerDTO;
import org.oscar.model.TrainingType;
import org.oscar.service.TraineeService;
import org.oscar.service.TrainerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;

@Configuration
@ComponentScan("org.oscar")
@PropertySource("classpath:application.properties")
public class App {
    public static void main(String[] args) {

        ApplicationContext contex = new AnnotationConfigApplicationContext(App.class);
        System.out.println(contex.getBean(TraineeDB.class).traineeMap.get(1L));
        TraineeDTO traineeDTO = new TraineeDTO("Ana", "Alvarez", LocalDate.parse("2000-01-01"), "calle no falsa");
        System.out.println(contex.getBean(TraineeService.class).createTrainee(traineeDTO));
        System.out.println(contex.getBean(TraineeDB.class).traineeMap.get(2L));
        TraineeDTO traineeDTO2 = new TraineeDTO("Ana", "Alvarez", LocalDate.parse("2000-01-01"), "calle experimento");
        System.out.println(contex.getBean(TraineeService.class).createTrainee(traineeDTO2));
        contex.getBean(TraineeService.class).deleteTrainee(4l);
        contex.getBean(TraineeDB.class).traineeMap.entrySet().stream().forEach(System.out::println);
        System.out.println(contex.getBean(TraineeService.class).selectTrainee("carlos"));
        TraineeDTO traineeDTO3 = new TraineeDTO("Hola", "Yo", LocalDate.parse("2004-02-02"), "probando el upodate");
        contex.getBean(TraineeService.class).updateTrainee(traineeDTO3, 5l);
        System.out.println("=======================================================");
        contex.getBean(TraineeDB.class).traineeMap.entrySet().stream().forEach(System.out::println);
        System.out.println("========================================================");
        contex.getBean(TrainerDB.class).trainerMap.entrySet().stream().forEach(System.out::println);
        TrainerDTO trainerDTO = new TrainerDTO("James","Bond", TrainingType.CARDIO);
        System.out.println("===========================================================");
        System.out.println(contex.getBean(TrainerService.class).createTrainer(trainerDTO));
        contex.getBean(TrainerDB.class).trainerMap.entrySet().stream().forEach(System.out::println);
        System.out.println("=============================================================");
        contex.getBean(TrainingDB.class).trainingMap.entrySet().stream().forEach(System.out::println);




    }

}
