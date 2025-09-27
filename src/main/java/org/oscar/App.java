package org.oscar;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.oscar.dtos.TraineeDTO;
import org.oscar.entity.Trainee;
import org.oscar.service.TraineeService;
import org.oscar.utils.Mapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;

@Configuration
@ComponentScan("org.oscar")
public class App {
    public static void main(String[] args) {

        ApplicationContext contex = new AnnotationConfigApplicationContext(App.class);

        TraineeDTO dto = new TraineeDTO("Oscar","Obando", LocalDate.parse("1991-03-30"),"calle falsa 123");
        Trainee trainee= contex.getBean(Mapper.class).mapTrainee(dto);


        contex.getBean(TraineeService.class).save(dto);
        TraineeDTO dto2 = new TraineeDTO("Oscar","Obando", LocalDate.parse("1991-03-30"),"calle falsa");
        contex.getBean(TraineeService.class).save(dto2);






    }

}
