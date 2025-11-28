package org.oscar.gym.trainee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.dtos.response.trainee.TraineeRegistrationResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponseExtend;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.repository.trainee.TraineeRepositoryImp;
import org.oscar.gym.service.trainee.TraineeService;
import org.oscar.gym.utils.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TraineeTest {

    @Mock
    private TraineeRepositoryImp repository;

    @InjectMocks
    private TraineeService traineeService;

    @Mock
    private Mapper mapper;

    @Mock
    private PasswordEncoder encoder;

      private Trainee trainee;
      private TraineeRegistrationRequest requestSave;
      private TraineeUpdateRequest requestUpdate;
      private TraineeResponseExtend responseUpdate;
      private TraineeRegistrationResponse registrationResponse;

    @BeforeEach
    public void setUp() {
        trainee = new Trainee(1L, "calle falsa", LocalDate.parse("1990-03-30"), null, null);
        requestSave = new TraineeRegistrationRequest("Oscar", "Obando", LocalDate.parse("1990-03-30"), "calle falsa");
        requestUpdate = new TraineeUpdateRequest("Oscar.Obando","Oscar","Obando",LocalDate.parse("1990-03-30"), "calle falsa",true);
        responseUpdate = new TraineeResponseExtend("Oscar.Obando","Oscar","Obando",LocalDate.parse("1990-03-30"), "calle falsa",true,null);
        registrationResponse = new TraineeRegistrationResponse("Oscar.Obando","PASS123456");
    }

    @Test
    public void createTrainee() {
        when(mapper.mapTraineeEntity(requestSave)).thenReturn(trainee);
        when(repository.saveEntity(trainee)).thenReturn(trainee);
        when(encoder.encode(any())).thenReturn("HASH");
        traineeService.saveTrainee(requestSave);
        verify(repository, times(1)).saveEntity(any(Trainee.class));
    }

    @Test
    public void updateTrainee() {
        when(repository.findEntity(requestUpdate.getUsername())).thenReturn(trainee);
        when(mapper.mapTraineeResponseGet(trainee)).thenReturn(responseUpdate);

        TraineeResponseExtend result = traineeService.updateTrainee(requestUpdate);

        assertNotNull(result);

        verify(repository, times(1)).findEntity(requestUpdate.getUsername());
        verify(mapper, times(1)).mapTraineeResponseGet(trainee);
    }

    @Test
    public void findTrainee(){

        when(repository.findEntity("Oscar.Obando")).thenReturn(trainee);

        TraineeResponseExtend result = traineeService.findTrainee("Oscar.Obando");

        verify(repository, times(1)).findEntity("Oscar.Obando");
    }

    @Test
    public void deleteTrainee(){
        when(repository.deleteEntity("Oscar.Obando")).thenReturn(trainee);
        traineeService.deleteTrainee("Oscar.Obando");
        verify(repository, times(1)).deleteEntity("Oscar.Obando");

    }
}
