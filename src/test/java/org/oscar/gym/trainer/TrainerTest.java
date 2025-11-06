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
import org.oscar.gym.security.Authenticator;
import org.oscar.gym.service.trainer.TrainerService;
import org.oscar.gym.utils.Mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TrainerTest {

    @Mock
    private TrainerRepositoryImp repository;

    @Mock
    private Authenticator authenticator;

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
        when(repository.saveEntity(trainer)).thenReturn(trainer);

        service.saveTrainer(requestSave);

        verify(repository, times(1)).saveEntity(trainer);
    }

    @Test
    public void updateTrainer() {
        when(repository.updateEntity(requestUpdate)).thenReturn(trainer);
        when(mapper.mapTrainerResponseGetMethod(trainer)).thenReturn(responseUpdate);

        TrainerResponseExtend result = service.updateTrainer(requestUpdate);

        assertNotNull(result);

        verify(repository, times(1)).updateEntity(requestUpdate);
    }

    @Test
    public void findTrainer(){

        when(repository.findEntity("Arnold.Terminator")).thenReturn(trainer);

        TrainerResponseExtend result = service.findTrainer("Arnold.Terminator");

        verify(repository, times(1)).findEntity("Arnold.Terminator");


    }

}