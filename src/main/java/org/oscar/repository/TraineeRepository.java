package org.oscar.repository;

import org.oscar.dtos.TraineeDTO;
import org.oscar.entity.Trainee;

public interface TraineeRepository {
    Trainee save(TraineeDTO dto);

}
