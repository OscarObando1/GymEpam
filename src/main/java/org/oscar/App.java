package org.oscar;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.oscar")
public class App {
    public static void main(String[] args) {

        ApplicationContext contex = new AnnotationConfigApplicationContext(App.class);
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("GymApp");
        EntityManager entityManager = managerFactory.createEntityManager();

    }

}
