package org.oscar.gym.trainer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TrainerDTO;
import org.oscar.gym.dtos.response.TrainerResponsetemp;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.TrainingType;
import org.oscar.gym.enums.TypeTraining;
import org.oscar.gym.repository.trainer.TrainerRepositoryImp;
import org.oscar.gym.security.Authenticator;
import org.oscar.gym.service.trainer.TrainerService;
import org.oscar.gym.utils.Mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainerTest {

//    @Mock
//    private TrainerRepositoryImp repository;
//
//    @Mock
//    private Authenticator authenticator;
//
//    @InjectMocks
//    private TrainerService service;
//
//    @Mock
//    private Mapper mapper;
//
//    private Trainer trainer;
//    private TrainerDTO dto;
//    private LoginDTO loginDTO;
//    private TrainerResponsetemp response;
//
//    @BeforeEach
//    public void setUp() {
//        trainer = new Trainer(2L,new TrainingType(),null,null);
//        dto = new TrainerDTO("Oscar", "Obando", TypeTraining.LIFTING);
//        loginDTO = new LoginDTO("Oscar.Obando", "PASS123456");
//        response = new TrainerResponsetemp("Arnold", "Terminator", "Arnold.Terminator");
//    }

//    @Test
//    public void createTrainer() {
//        when(repository.saveEntity(dto)).thenReturn(trainer);
//
//        service.saveTrainer(dto);
//
//        verify(repository, times(1)).saveEntity(dto);
//    }

//    @Test
//    public void updateTrainer() {
//        long id = 1L;
//        when(authenticator.isAuthorized(loginDTO.getUsername(), loginDTO.getPassword())).thenReturn(true);
//
//        when(repository.updateEntity(dto, id)).thenReturn(trainer);
//        when(mapper.mapTrainerResponse(trainer)).thenReturn(response);
//
//        TrainerResponsetemp result = service.updateTrainer(loginDTO, dto, id);
//
//        assertNotNull(result);
//
//        verify(authenticator, times(1)).isAuthorized(loginDTO.getUsername(), loginDTO.getPassword());
//        verify(repository, times(1)).updateEntity(dto, id);
//        verify(mapper, times(1)).mapTrainerResponse(trainer);
//    }
//
//    @Test
//    public void findTrainer(){
//
//        when(authenticator.isAuthorized(loginDTO.getUsername(), loginDTO.getPassword())).thenReturn(true);
//        when(repository.findEntity("Arnold.Terminator")).thenReturn(trainer);
//
//        TrainerResponsetemp result = service.findTrainer(loginDTO,"Arnold.Terminator");
//
//        verify(repository, times(1)).findEntity("Arnold.Terminator");
//
//
//    }
//
//    @Test
//    public void deleteTrainer(){
//        when(authenticator.isAuthorized(loginDTO.getUsername(), loginDTO.getPassword())).thenReturn(true);
//        service.deleteTrainer(loginDTO,"Arnold.Terminator");
//
//        verify(repository, times(1)).deleteEntity("Arnold.Terminator");
//
//    }
}