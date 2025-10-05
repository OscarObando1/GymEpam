package org.oscar.gym.service.trainee;

import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.dtos.response.TraineeResponse;
import org.oscar.gym.entity.Trainee;

public interface ITraineeService {
   void saveTrainee(TraineeDTO dto);

   TraineeResponse findTrainee(LoginDTO loginDTO, String username);

   TraineeResponse updateTrainee(LoginDTO loginDTO,TraineeDTO traineeDTO, long id);

   void deleteTrainee(LoginDTO loginDTO,String username);
}
