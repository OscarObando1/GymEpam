package org.oscar;

import org.oscar.dtos.TraineeDTO;
import org.oscar.dtos.TrainerDTO;
import org.oscar.entity.Trainee;
import org.oscar.entity.TrainingType;
import org.oscar.enums.TypeTraining;
import org.oscar.service.TraineeService;
import org.oscar.service.TrainerSercice;
import org.oscar.utils.Mapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;


@Configuration
@ComponentScan("org.oscar")
public class App {
    public static void main(String[] args) {

        ApplicationContext contex = new AnnotationConfigApplicationContext(App.class);

        TraineeDTO dto = new TraineeDTO("Oscar","Obando", LocalDate.parse("1991-03-30"),"calle falsa 123");

        contex.getBean(TraineeService.class).saveTrainee(dto);

        TraineeDTO dto2 = new TraineeDTO("Oscar","Obando", LocalDate.parse("1991-03-30"),"calle falsa");
        contex.getBean(TraineeService.class).saveTrainee(dto2);

        TraineeDTO dto3 = new TraineeDTO("Oscar","Obando", LocalDate.parse("1991-03-30"),"esta no es falsa");
        contex.getBean(TraineeService.class).saveTrainee(dto3);

        contex.getBean(TraineeService.class).deleteTrainee("Oscar.Obando1");

        System.out.println(contex.getBean(TraineeService.class).findTrainee("Oscar.Obando"));

        TraineeDTO dto4 = new TraineeDTO("Pepe","Pepito", LocalDate.parse("1991-03-30"),"ver si funciona");
//        System.out.println(contex.getBean(TraineeService.class).updateTrainee(dto4,3));

        TrainerDTO dtoTrainer = new TrainerDTO("Arnold", "EldeLasPeliculas", TypeTraining.valueOf("LIFTING"));
        contex.getBean(TrainerSercice.class).saveTrainer(dtoTrainer);








    }

}
