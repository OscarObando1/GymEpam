package org.oscar.gym.repository.trainig_types;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.oscar.gym.entity.TrainingType;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingTypesImp implements TrainingTypes{

    private final EntityManager entityManager;

    public TrainingTypesImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public TrainingType findType(String name) {
        TrainingType type = null;
        String jpql = "SELECT t FROM TrainingType t WHERE LOWER(t.name) = LOWER(:name)";
        type = (TrainingType) entityManager.createQuery(jpql, TrainingType.class)
                .setParameter("name", name)
                .getSingleResult();
        return type;
    }
}
