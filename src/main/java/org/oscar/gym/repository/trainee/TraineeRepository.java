package org.oscar.gym.repository.trainee;


import org.oscar.gym.dtos.request.temp.ChangePassDTO;
import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.entity.Trainee;
import org.springframework.stereotype.Component;

@Component
public interface TraineeRepository {
    Trainee saveEntity(TraineeRegistrationRequest dto);
    Trainee findEntity(String username );
    Trainee updateEntity(TraineeUpdateRequest dto, long id);
    void deleteEntity(String username);
    Trainee changeActive(long id);
    void updatePass(ChangePassDTO dto);


}
