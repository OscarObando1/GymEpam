package org.oscar.gym.service.trainee;

import org.oscar.gym.dtos.ChangePassDTO;
import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateListTrainerRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.dtos.response.trainee.TraineeRegistrationResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponseExtend;
import org.oscar.gym.dtos.response.trainer.TrainerResponse;

import java.util.List;

public interface ITraineeService {
   TraineeRegistrationResponse saveTrainee(TraineeRegistrationRequest dto);

   TraineeResponseExtend findTrainee(String username);

   TraineeResponseExtend updateTrainee(TraineeUpdateRequest dto);

   void deleteTrainee(String username);

   void activeOrDeactivateTraine(UserActivateDeActivate dto);

   void updatePassword(ChangePassDTO dto);

   List<TrainerResponse> updateListOfTrainer(TraineeUpdateListTrainerRequest dto);
}
