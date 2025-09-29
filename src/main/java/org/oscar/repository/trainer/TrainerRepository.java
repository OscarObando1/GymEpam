package org.oscar.repository.trainer;


import org.oscar.dtos.TrainerDTO;
import org.oscar.entity.Trainer;
import org.springframework.stereotype.Component;

@Component
public interface TrainerRepository {
    Trainer saveEntity(TrainerDTO dto);
    Trainer findEntity(String username );
    Trainer updateEntity(TrainerDTO dto,long id);
    void deleteEntity(String username);
}
