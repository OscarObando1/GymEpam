package org.oscar.gym.service.training;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oscar.gym.dtos.request.training.TraineeTrainingsListResquest;
import org.oscar.gym.dtos.request.training.TrainerTrainingsListRequest;
import org.oscar.gym.dtos.request.training.TrainingDTO;
import org.oscar.gym.dtos.response.training.TraineeTrainingsListResponse;
import org.oscar.gym.dtos.response.training.TrainerTrainingsListResponse;
import org.oscar.gym.dtos.response.training.TrainingResponseAll;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.Training;
import org.oscar.gym.entity.TrainingType;
import org.oscar.gym.enums.TypeTraining;
import org.oscar.gym.exception.TraineeNotFoundException;
import org.oscar.gym.exception.TrainerNotFoundException;
import org.oscar.gym.producer.StatisticsProducer;
import org.oscar.gym.repository.trainee.TraineeRepository;
import org.oscar.gym.repository.trainer.TrainerRepository;
import org.oscar.gym.repository.training.TrainingRepository;
import org.oscar.gym.utils.Mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TrainigService")
class TrainigServiceTest {

    @Mock
    private TrainingRepository repository;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private Mapper mapper;
    @Mock
    private StatisticsProducer statisticsProducer;

    @InjectMocks
    private TrainigService trainingService;

    private Trainee trainee;
    private Trainer trainer;
    private Training training;
    private TrainingType trainingType;

    @BeforeEach
    void setUp() {
        trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setName(TypeTraining.CARDIO);

        trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUsername("trainee.jane");
        trainee.setFirstName("Jane");

        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUsername("trainer.john");
        trainer.setFirstName("John");
        trainer.setSpecialization(trainingType);

        training = new Training();
        training.setId(1L);
        training.setName("Cardio Training");
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        training.setTrainingDate(LocalDate.of(2024, 1, 15));
        training.setDurationTraining(60);
    }

    @Nested
    @DisplayName("createTraining")
    class CreateTraining {

        @Test
        @DisplayName("creates training when trainer and trainee exist")
        void createTraining_succeeds() {
            TrainingDTO dto = new TrainingDTO();
            dto.setTrainerUsername("trainer.john");
            dto.setTraineeUsername("trainee.jane");
            dto.setName("Cardio Training");
            dto.setDate(LocalDate.of(2024, 1, 15));
            dto.setDuration(60);

            when(trainerRepository.findEntity("trainer.john")).thenReturn(trainer);
            when(traineeRepository.findEntity("trainee.jane")).thenReturn(trainee);
            when(mapper.mapTrainingEntity(dto)).thenReturn(training);
            doNothing().when(repository).createTraining(any(Training.class));
            doNothing().when(statisticsProducer).sendTrainingRecord(any());

            trainingService.createTraining(dto);

            verify(repository).createTraining(any(Training.class));
            verify(statisticsProducer).sendTrainingRecord(any());
        }

        @Test
        @DisplayName("throws TrainerNotFoundException when trainer not found")
        void createTraining_throwsWhenTrainerNotFound() {
            TrainingDTO dto = new TrainingDTO();
            dto.setTrainerUsername("unknown");
            dto.setTraineeUsername("trainee.jane");
            dto.setName("Training");
            dto.setDate(LocalDate.now());
            dto.setDuration(60);
            when(trainerRepository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> trainingService.createTraining(dto))
                    .isInstanceOf(TrainerNotFoundException.class);
        }

        @Test
        @DisplayName("throws TraineeNotFoundException when trainee not found")
        void createTraining_throwsWhenTraineeNotFound() {
            TrainingDTO dto = new TrainingDTO();
            dto.setTrainerUsername("trainer.john");
            dto.setTraineeUsername("unknown");
            dto.setName("Training");
            dto.setDate(LocalDate.now());
            dto.setDuration(60);
            when(trainerRepository.findEntity("trainer.john")).thenReturn(trainer);
            when(traineeRepository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> trainingService.createTraining(dto))
                    .isInstanceOf(TraineeNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("listTypes")
    class ListTypes {

        @Test
        @DisplayName("returns list of training types")
        void listTypes_returnsList() {
            List<TrainingType> types = List.of(trainingType);
            TrainingResponseAll response = new TrainingResponseAll();
            response.setTrainingType("CARDIO");
            response.setTrainingTypeId(1L);

            when(repository.getTypes()).thenReturn(types);
            when(mapper.mapTrainingResponseType(any(TrainingType.class))).thenReturn(response);

            List<TrainingResponseAll> result = trainingService.listTypes();

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getTrainingType()).isEqualTo("CARDIO");
        }
    }

    @Nested
    @DisplayName("getTrainingListTrainee")
    class GetTrainingListTrainee {

        @Test
        @DisplayName("returns list of trainee trainings")
        void getTrainingListTrainee_returnsList() {
            TraineeTrainingsListResquest request = new TraineeTrainingsListResquest();
            request.setUsername("trainee.jane");
            TraineeTrainingsListResponse response = new TraineeTrainingsListResponse();
            response.setTrainingName("Cardio");
            response.setDate("2024-01-15");

            when(traineeRepository.findEntity("trainee.jane")).thenReturn(trainee);
            when(repository.getTraineeTrainings(request)).thenReturn(List.of(training));
            when(mapper.mapTrainigListTrainee(any(Training.class))).thenReturn(response);

            List<TraineeTrainingsListResponse> result = trainingService.getTrainingListTrainee(request);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getTrainingName()).isEqualTo("Cardio");
        }

        @Test
        @DisplayName("throws TraineeNotFoundException when trainee not found")
        void getTrainingListTrainee_throwsWhenTraineeNotFound() {
            TraineeTrainingsListResquest request = new TraineeTrainingsListResquest();
            request.setUsername("unknown");
            when(traineeRepository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> trainingService.getTrainingListTrainee(request))
                    .isInstanceOf(TraineeNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("getTrainingListTrainer")
    class GetTrainingListTrainer {

        @Test
        @DisplayName("returns list of trainer trainings")
        void getTrainingListTrainer_returnsList() {
            TrainerTrainingsListRequest request = new TrainerTrainingsListRequest();
            request.setUsername("trainer.john");
            TrainerTrainingsListResponse response = new TrainerTrainingsListResponse();
            response.setTrainingName("Cardio");
            response.setDate("2024-01-15");

            when(trainerRepository.findEntity("trainer.john")).thenReturn(trainer);
            when(repository.getTrainerTrainings(request)).thenReturn(List.of(training));
            when(mapper.mapTrainigListTrainer(any(Training.class))).thenReturn(response);

            List<TrainerTrainingsListResponse> result = trainingService.getTrainingListTrainer(request);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getTrainingName()).isEqualTo("Cardio");
        }

        @Test
        @DisplayName("throws TrainerNotFoundException when trainer not found")
        void getTrainingListTrainer_throwsWhenTrainerNotFound() {
            TrainerTrainingsListRequest request = new TrainerTrainingsListRequest();
            request.setUsername("unknown");
            when(trainerRepository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> trainingService.getTrainingListTrainer(request))
                    .isInstanceOf(TrainerNotFoundException.class);
        }
    }
}
