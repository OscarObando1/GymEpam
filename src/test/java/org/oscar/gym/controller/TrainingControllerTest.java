package org.oscar.gym.controller;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oscar.gym.dtos.request.training.TraineeTrainingsListResquest;
import org.oscar.gym.dtos.request.training.TrainerTrainingsListRequest;
import org.oscar.gym.dtos.request.training.TrainingDTO;
import org.oscar.gym.dtos.response.training.TraineeTrainingsListResponse;
import org.oscar.gym.dtos.response.training.TrainerTrainingsListResponse;
import org.oscar.gym.dtos.response.training.TrainingResponseAll;
import org.oscar.gym.service.training.ITrainingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("TrainingController")
class TrainingControllerTest {

    @Mock
    private ITrainingService trainingService;

    @InjectMocks
    private TrainingController trainingController;

    @Nested
    @DisplayName("POST /training - createTraining")
    class CreateTraining {

        @Test
        @DisplayName("returns 200 and creates training")
        void createTraining_returnsOkAndCallsService() {
            TrainingDTO dto = new TrainingDTO();
            dto.setTrainerUsername("trainer.john");
            dto.setTraineeUsername("trainee.jane");
            dto.setName("Cardio Training");
            dto.setDate(LocalDate.of(2024, 1, 15));
            dto.setDuration(60);
            
            doNothing().when(trainingService).createTraining(any(TrainingDTO.class));

            ResponseEntity<?> result = trainingController.createTraining(dto);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            verify(trainingService).createTraining(dto);
        }
    }

    @Nested
    @DisplayName("GET /training - typeTrainings")
    class TypeTrainings {

        @Test
        @DisplayName("returns 200 and list of training types")
        void typeTrainings_returnsOkAndList() {
            TrainingResponseAll type1 = new TrainingResponseAll();
            type1.setTrainingType("LIFTING");
            type1.setTrainingTypeId(1L);
            
            TrainingResponseAll type2 = new TrainingResponseAll();
            type2.setTrainingType("CARDIO");
            type2.setTrainingTypeId(2L);
            
            TrainingResponseAll type3 = new TrainingResponseAll();
            type3.setTrainingType("CROSSFIT");
            type3.setTrainingTypeId(3L);
            
            List<TrainingResponseAll> expected = List.of(type1, type2, type3);
            when(trainingService.listTypes()).thenReturn(expected);

            ResponseEntity<List<TrainingResponseAll>> result = trainingController.typeTrainings();

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).isEqualTo(expected);
            assertThat(result.getBody()).hasSize(3);
            verify(trainingService).listTypes();
        }
    }

    @Nested
    @DisplayName("GET /training/list-trainee/trainings - listTrainingTrainee")
    class ListTrainingTrainee {

        @Test
        @DisplayName("returns 200 and list of trainee trainings")
        void listTrainingTrainee_returnsOkAndList() {
            String username = "trainee.jane";
            LocalDate dateFrom = LocalDate.of(2024, 1, 1);
            LocalDate dateTo = LocalDate.of(2024, 1, 31);
            String trainerName = "trainer.john";
            
            TraineeTrainingsListResponse response1 = new TraineeTrainingsListResponse();
            response1.setTrainingName("Cardio Training");
            response1.setDate("2024-01-15");
            response1.setType("CARDIO");
            response1.setDuration("60");
            response1.setTrainerName("trainer.john");
            
            TraineeTrainingsListResponse response2 = new TraineeTrainingsListResponse();
            response2.setTrainingName("Lifting Session");
            response2.setDate("2024-01-20");
            response2.setType("LIFTING");
            response2.setDuration("90");
            response2.setTrainerName("trainer.john");
            
            List<TraineeTrainingsListResponse> expected = List.of(response1, response2);
            when(trainingService.getTrainingListTrainee(any(TraineeTrainingsListResquest.class))).thenReturn(expected);

            ResponseEntity<List<TraineeTrainingsListResponse>> result = 
                trainingController.listTrainingTrainee(username, dateFrom, dateTo, trainerName);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).isEqualTo(expected);
            assertThat(result.getBody()).hasSize(2);
            verify(trainingService).getTrainingListTrainee(any(TraineeTrainingsListResquest.class));
        }

        @Test
        @DisplayName("returns 200 with only username parameter")
        void listTrainingTrainee_returnsOkWithOnlyUsername() {
            String username = "trainee.jane";
            List<TraineeTrainingsListResponse> expected = List.of();
            when(trainingService.getTrainingListTrainee(any(TraineeTrainingsListResquest.class))).thenReturn(expected);

            ResponseEntity<List<TraineeTrainingsListResponse>> result = 
                trainingController.listTrainingTrainee(username, null, null, null);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).isEqualTo(expected);
            verify(trainingService).getTrainingListTrainee(any(TraineeTrainingsListResquest.class));
        }
    }

    @Nested
    @DisplayName("GET /training/list-trainer/trainings - listTrainingTrainer")
    class ListTrainingTrainer {

        @Test
        @DisplayName("returns 200 and list of trainer trainings")
        void listTrainingTrainer_returnsOkAndList() {
            String username = "trainer.john";
            LocalDate dateFrom = LocalDate.of(2024, 1, 1);
            LocalDate dateTo = LocalDate.of(2024, 1, 31);
            String traineeName = "trainee.jane";
            
            TrainerTrainingsListResponse response1 = new TrainerTrainingsListResponse();
            response1.setTrainingName("Cardio Training");
            response1.setDate("2024-01-15");
            response1.setType("CARDIO");
            response1.setDuration("60");
            response1.setTraineeName("trainee.jane");
            
            TrainerTrainingsListResponse response2 = new TrainerTrainingsListResponse();
            response2.setTrainingName("Lifting Session");
            response2.setDate("2024-01-20");
            response2.setType("LIFTING");
            response2.setDuration("90");
            response2.setTraineeName("trainee.jane");
            
            List<TrainerTrainingsListResponse> expected = List.of(response1, response2);
            when(trainingService.getTrainingListTrainer(any(TrainerTrainingsListRequest.class))).thenReturn(expected);

            ResponseEntity<List<TrainerTrainingsListResponse>> result = 
                trainingController.listTrainingTrainer(username, dateFrom, dateTo, traineeName);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).isEqualTo(expected);
            assertThat(result.getBody()).hasSize(2);
            verify(trainingService).getTrainingListTrainer(any(TrainerTrainingsListRequest.class));
        }

        @Test
        @DisplayName("returns 200 with only username parameter")
        void listTrainingTrainer_returnsOkWithOnlyUsername() {
            String username = "trainer.john";
            List<TrainerTrainingsListResponse> expected = List.of();
            when(trainingService.getTrainingListTrainer(any(TrainerTrainingsListRequest.class))).thenReturn(expected);

            ResponseEntity<List<TrainerTrainingsListResponse>> result = 
                trainingController.listTrainingTrainer(username, null, null, null);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).isEqualTo(expected);
            verify(trainingService).getTrainingListTrainer(any(TrainerTrainingsListRequest.class));
        }
    }
}