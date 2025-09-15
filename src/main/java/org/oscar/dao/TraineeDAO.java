package org.oscar.dao;

import org.oscar.dtos.TraineeDTO;
import org.oscar.model.Trainee;

public interface TraineeDAO {

    Trainee createTrainee(TraineeDTO dto);
    Trainee updateTrainee(TraineeDTO dto);
    void deleteTrainee(long id);
    Trainee selectTrainee(String name);

}
