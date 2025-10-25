package org.oscar.gym.trainee;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TraineeTest {

//    @Mock
//    private TraineeRepositoryImp repository;
//
//    @Mock
//    private Authenticator authenticator;
//
//    @InjectMocks
//    private TraineeService traineeService;
//
//    @Mock
//    private Mapper mapper;
//
//    private Trainee trainee;
//    private TraineeDTO dto;
//    private LoginDTO loginDTO;
//    private TraineeResponseExtend response;
//
//    @BeforeEach
//    public void setUp() {
//        trainee = new Trainee(1L, "calle falsa", LocalDate.parse("1990-03-30"), null, null);
//        dto = new TraineeDTO("Oscar", "Obando", LocalDate.parse("1990-03-30"), "calle falsa");
//        loginDTO = new LoginDTO("Oscar.Obando", "PASS123456");
//        response = new TraineeResponseExtend("Oscar", "Obando", "Oscar.Obando");
//    }
//
//    @Test
//    public void createTrainee() {
//        when(repository.saveEntity(dto)).thenReturn(trainee);
//
//        traineeService.saveTrainee(dto);
//
//        verify(repository, times(1)).saveEntity(dto);
//    }
//
//    @Test
//    public void updateTrainee() {
//        long id = 1L;
//        when(authenticator.isAuthorized(loginDTO.getUsername(), loginDTO.getPassword())).thenReturn(true);
//
//        when(repository.updateEntity(dto, id)).thenReturn(trainee);
//        when(mapper.mapTraineeResponseExtend(trainee)).thenReturn(response);
//
//        TraineeResponseExtend result = traineeService.updateTrainee(loginDTO, dto, id);
//
//        assertNotNull(result);
//
//        verify(authenticator, times(1)).isAuthorized(loginDTO.getUsername(), loginDTO.getPassword());
//        verify(repository, times(1)).updateEntity(dto, id);
//        verify(mapper, times(1)).mapTraineeResponseExtend(trainee);
//    }
//
//    @Test
//    public void findTrainee(){
//
//        when(authenticator.isAuthorized(loginDTO.getUsername(), loginDTO.getPassword())).thenReturn(true);
//        when(repository.findEntity("Oscar.Obando")).thenReturn(trainee);
//
//        TraineeResponseExtend result = traineeService.findTrainee(loginDTO,"Oscar.Obando");
//
//        verify(repository, times(1)).findEntity("Oscar.Obando");
//
//
//    }
//
//    @Test
//    public void deleteTrainee(){
//        when(authenticator.isAuthorized(loginDTO.getUsername(), loginDTO.getPassword())).thenReturn(true);
//        traineeService.deleteTrainee(loginDTO,"Oscar.Obando");
//
//        verify(repository, times(1)).deleteEntity("Oscar.Obando");
//
//    }
}
