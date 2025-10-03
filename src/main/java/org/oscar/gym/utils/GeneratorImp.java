package org.oscar.gym.utils;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GeneratorImp implements IGenerator {

    private final EntityManager entityManager;

    public GeneratorImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public String createUser(String firstName, String lastName) {
        long idAux = isOccupied(firstName,lastName);
        if(idAux>0) return firstName+"."+lastName+idAux;
        else return firstName+"."+lastName;
    }

    @Override
    public String generatePass() {
        Random random = new Random();
        String password ="";
        for (int i = 0; i < 10; i++) {
            char temp =(char) (65 + random.nextInt(90 - 65 + 1));
            password+=temp;
        }
        return password;
    }

    @Override
    public long isOccupied(String firstName, String lastName) {
        String jpql = "SELECT COUNT(u) FROM User u WHERE LOWER(u.firstName) = LOWER(:firstName) AND LOWER(u.lastName) LIKE LOWER(:lastName)";

        long count = (long) entityManager.createQuery(jpql, Long.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName+"%")
                .getSingleResult();
        System.out.println(count);
        return count;
    }
}