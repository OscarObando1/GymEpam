package org.oscar.gym.repository.trainee;


import org.oscar.gym.dtos.ChangePassDTO;
import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.entity.Trainee;
import org.springframework.stereotype.Component;

@Component
public interface TraineeRepository {
    Trainee saveEntity(TraineeRegistrationRequest dto);
    Trainee findEntity(String username );
    Trainee updateEntity(TraineeUpdateRequest dto);
    void deleteEntity(String username);
    void changeActive(UserActivateDeActivate dto);
    void updatePass(ChangePassDTO dto);


}
