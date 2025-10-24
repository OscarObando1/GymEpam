package org.oscar.gym.service.trainee;

import org.oscar.gym.dtos.request.temp.ChangePassDTO;
import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.response.TraineeResponseExtend;
import org.oscar.gym.dtos.response.trainee.TraineeRegistrationResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponse;

public interface ITraineeService {
   TraineeRegistrationResponse saveTrainee(TraineeRegistrationRequest dto);

   TraineeResponse findTrainee(String username);

   TraineeResponseExtend updateTrainee(LoginDTO loginDTO, TraineeDTO traineeDTO, long id);

   void deleteTrainee(LoginDTO loginDTO,String username);

   TraineeResponseExtend activeOrDeactivateTraine(long id);

   void updatePassword(ChangePassDTO dto);
}
