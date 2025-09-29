package org.oscar.repository.trainee;

import org.oscar.dtos.TraineeDTO;
import org.oscar.entity.Trainee;
import org.springframework.stereotype.Component;

@Component
public interface TraineeRepository {
    Trainee saveEntity(TraineeDTO dto);
    Trainee findEntity(String username );
    Trainee updateEntity(TraineeDTO dto,long id);
    void deleteEntity(String username);


}
