package org.oscar.gym.repository.trainee;

import org.oscar.gym.entity.Trainee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TraineeRepository {
    Trainee saveEntity(Trainee entity);
    Trainee findEntity(String username );
    Trainee updateEntity(Trainee entity);
    Trainee deleteEntity(String username);
}
