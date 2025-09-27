package org.oscar.utils;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class GeneratorImp implements IGenerator {

    private static long auxCounter =1;

    private final EntityManager entityManager;

    public GeneratorImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public String createUser(String firstName, String lastName) {
        if(isOccupied(firstName,lastName)) return firstName+"."+lastName+auxCounter++;
        else return firstName+"."+lastName;
    }

    @Override
    public String generatePass() {
        return "probando";
    }

    @Override
    public boolean isOccupied(String firstName, String lastName) {
        String jpql = "SELECT COUNT(u) FROM User u WHERE u.firstName = :firstName AND u.lastName = :lastName";

        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getSingleResult();

        System.out.println(count);
        return count > 0;
    }
}