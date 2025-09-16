package org.oscar;


import org.oscar.beansdb.TraineeDB;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.oscar")
@PropertySource("classpath:application.properties")
public class App {
    public static void main( String[] args ) {

        ApplicationContext contex = new AnnotationConfigApplicationContext(App.class);
        System.out.println(contex.getBean(TraineeDB.class).traineeMap.get(1L));
        System.out.println(contex.getBean(TraineeDB.class).isOccupied("Ana","Alvarez"));








    }

}
