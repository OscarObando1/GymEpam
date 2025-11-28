package org.oscar.gym.trainer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerUpdateRequest;
import org.oscar.gym.dtos.response.trainer.TrainerResponseExtend;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.TrainingType;
import org.oscar.gym.repository.trainer.TrainerRepositoryImp;
import org.oscar.gym.repository.trainig_types.TrainingTypesImp;
import org.oscar.gym.service.trainer.TrainerService;
import org.oscar.gym.utils.Mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TrainerTest {

    @Mock
    private TrainerRepositoryImp repository;

    @Mock
    private TrainingTypesImp repoTypes;

    @InjectMocks
    private TrainerService service;

    @Mock
    private Mapper mapper;

    private Trainer trainer;
    private TrainerRegistrationRequest requestSave;
    private TrainerUpdateRequest requestUpdate;
    private TrainerResponseExtend responseUpdate;

    @BeforeEach
    public void setUp() {
        trainer = new Trainer(2L,new TrainingType(),null,null);
        requestSave = new TrainerRegistrationRequest("Jet","Li","LIFTING");
        requestUpdate = new TrainerUpdateRequest("Jet.Li","Jet","Li","LIFTING",true);
        responseUpdate = new TrainerResponseExtend("Jet.Li","Jet","Li","LIFTING",true,null);
    }

    @Test
    public void createTrainer() {
        when(mapper.mapTrainerEntity(requestSave)).thenReturn(trainer);
        when(repository.saveEntity(trainer)).thenReturn(trainer);

        service.saveTrainer(requestSave);

        verify(repository, times(1)).saveEntity(trainer);
    }

    @Test
    public void updateTrainer() {
        when(repository.findEntity(requestUpdate.getUsername())).thenReturn(trainer);
        when(repository.updateEntity(trainer)).thenReturn(trainer);
        when(mapper.mapTrainerResponseGetMethod(trainer)).thenReturn(responseUpdate);
        TrainerResponseExtend result = service.updateTrainer(requestUpdate);
        verify(repository, times(1)).findEntity(requestUpdate.getUsername());
        verify(repository, times(1)).updateEntity(trainer);
        verify(mapper, times(1)).mapTrainerResponseGetMethod(trainer);
        assertEquals(responseUpdate, result);
    }

    @Test
    public void findTrainer(){

        when(repository.findEntity("Arnold.Terminator")).thenReturn(trainer);

        TrainerResponseExtend result = service.findTrainer("Arnold.Terminator");

        verify(repository, times(1)).findEntity("Arnold.Terminator");


    }

}