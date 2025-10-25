package org.oscar.gym.service.trainee;

import org.oscar.gym.dtos.request.temp.ChangePassDTO;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.dtos.response.trainee.TraineeResponse;
import org.oscar.gym.dtos.response.trainee.TraineeRegistrationResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponseExtend;

public interface ITraineeService {
   TraineeRegistrationResponse saveTrainee(TraineeRegistrationRequest dto);

   TraineeResponseExtend findTrainee(String username);

   TraineeResponseExtend updateTrainee(TraineeUpdateRequest dto, long id);

   void deleteTrainee(String username);

   TraineeResponse activeOrDeactivateTraine(long id);

   void updatePassword(ChangePassDTO dto);
}
