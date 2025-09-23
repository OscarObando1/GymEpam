package org.oscar.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.oscar.App;
import org.oscar.beansdb.TraineeDB;
import org.oscar.dtos.TraineeDTO;
import org.oscar.model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = App.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TraineeServiceTest {

    @Autowired
    private TraineeDB traineeDB;
    @Autowired
    private TraineeService traineeService;

    private  Map<Long, Trainee> map;

    @BeforeEach
    public void setUp(){
        map = traineeDB.traineeMap;
    }

    @Test
    @Order(1)
    public void verifyCSVDataIsUpload(){
        int quantityDataInsertedd =3;
        assertFalse(map.isEmpty());
        assertEquals(quantityDataInsertedd,map.values().stream().count());
    }

    @Test
    @Order(2)
    public void createTrainee(){
        TraineeDTO traineeDTO = new TraineeDTO("Oscar", "Obando", LocalDate.parse("1991-01-01"), "la dir de mi casa");
        traineeService.createTrainee(traineeDTO);
        int expected = 4;
        assertEquals(4,map.values().stream().count());

    }

    @Test
    @Order(3)
    public void selectTrainee(){
        String name = "Ana";
        int expected =4;
        assertEquals(name,traineeService.selectTrainee(name).getFirstName());
        assertEquals(expected,map.values().stream().count());
    }

    @Test
    @Order(4)
    public void updateTrainee(){
        TraineeDTO traineeDTO = new TraineeDTO("Oscar", "Obando", LocalDate.parse("1991-01-01"), "la dir de mi casa");
        String nameExpected = "Oscar";
        int numExpected =4;
        traineeService.updateTrainee(traineeDTO,1L);
        assertEquals(nameExpected,traineeService.selectTrainee("Oscar").getFirstName());
        assertEquals(numExpected,map.values().stream().count());
    }

    @Test
    @Order(5)
    public void deleteTrainee(){
        int expected =3;
        traineeService.deleteTrainee(1L);
        assertEquals(expected,map.values().stream().count());
    }



}
