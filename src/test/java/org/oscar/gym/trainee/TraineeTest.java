package org.oscar.gym.trainee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.repository.trainee.TraineeRepositoryImp;
import org.oscar.gym.service.trainee.ITraineeService;
import org.oscar.gym.service.trainee.TraineeService;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TraineeTest {

    @Mock
    private TraineeRepositoryImp repository;

    @InjectMocks
    private TraineeService traineeService;

    private Trainee trainee;
    private TraineeDTO dto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Trainee trainee = new Trainee(1L,"calle falsa", LocalDate.parse("1990-03-30"),null,null);
        TraineeDTO dto = new TraineeDTO("Oscar","Obando",LocalDate.parse("1990-03-30"),"calle falsa");

    }

    @Test
    public void createTrainee() {

       when(repository.saveEntity(dto)).thenReturn(trainee);

        traineeService.saveTrainee(dto);

        verify(repository, times(1)).saveEntity(dto);

    }
}