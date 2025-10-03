package org.oscar.gym.service.trainee;

import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.dtos.response.TraineeResponse;
import org.oscar.gym.entity.Trainee;

public interface ITraineeService {
   void saveTrainee(TraineeDTO dto);

   TraineeResponse findTrainee(String username);

   TraineeResponse updateTrainee(TraineeDTO traineeDTO, long id);

   void deleteTrainee(String username);
}
