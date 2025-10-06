package org.oscar.gym.repository.trainee;


import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.entity.Trainee;
import org.springframework.stereotype.Component;

@Component
public interface TraineeRepository {
    Trainee saveEntity(TraineeDTO dto);
    Trainee findEntity(String username );
    Trainee updateEntity(TraineeDTO dto, long id);
    void deleteEntity(String username);
    Trainee changeActive(long id);


}
