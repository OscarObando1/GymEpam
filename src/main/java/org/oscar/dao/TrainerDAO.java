package org.oscar.dao;


import org.oscar.dtos.TrainerDTO;

import org.oscar.model.Trainer;

public interface TrainerDAO {
    Trainer createTrainer(TrainerDTO dto);
    Trainer updateTrainer(TrainerDTO dto, long id);
    void deleteTrainer(long id);
    Trainer selectTrainer(String name);
}
