package org.oscar.gym.security;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class Authenticator implements IAuthenticator {

    private final EntityManager entityManager;

    public Authenticator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean isAuthorized(String username, String password) {
        String jpql = "SELECT COUNT(u) FROM User u WHERE LOWER(u.username) = LOWER(:username) AND LOWER(u.password) = LOWER(:password)";
        long count = (long) entityManager.createQuery(jpql, Long.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
        return count>0;
    }
}
