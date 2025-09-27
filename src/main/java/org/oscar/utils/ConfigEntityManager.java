package org.oscar.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigEntityManager {
    @Bean
    public EntityManagerFactory managerFactory(){
       return Persistence.createEntityManagerFactory("GymApp");
    }

    @Bean
    public EntityManager entityManager(){
        return managerFactory().createEntityManager();
    }
}
