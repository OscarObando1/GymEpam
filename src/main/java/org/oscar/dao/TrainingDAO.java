package org.oscar.dao;

import org.oscar.dtos.TrainingDTO;
import org.oscar.model.Training;

public interface TrainingDAO {
    Training createTraining(TrainingDTO dto);
    Training selectTraining(String name);
}
