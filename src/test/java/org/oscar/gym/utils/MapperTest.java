package org.oscar.gym.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.request.training.TrainingDTO;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.Training;
import org.oscar.gym.entity.TrainingType;
import org.oscar.gym.enums.TypeTraining;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Mapper")
class MapperTest {

    @Mock
    private IGenerator generator;

    private Mapper mapper;

    private Trainee trainee;
    private Trainer trainer;
    private Training training;
    private TrainingType trainingType;

    @BeforeEach
    void setUp() {
        mapper = new Mapper(generator);

        trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setName(TypeTraining.LIFTING);

        trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setUsername("john.doe");
        trainee.setPassword("pass");
        trainee.setAddress("Calle 123");
        trainee.setDateOfBirth(LocalDate.of(1990, 1, 15));
        trainee.setIsActive(true);
        trainee.setTrainers(new ArrayList<>());

        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("Trainer");
        trainer.setLastName("One");
        trainer.setUsername("trainer.one");
        trainer.setPassword("pass");
        trainer.setIsActive(true);
        trainer.setSpecialization(trainingType);
        trainer.setTrainees(new ArrayList<>());

        training = new Training();
        training.setId(1L);
        training.setName("Cardio");
        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setTrainingType(trainingType);
        training.setTrainingDate(LocalDate.of(2024, 1, 15));
        training.setDurationTraining(60);
    }

    @Nested
    @DisplayName("mapTraineeResponseCreate")
    class MapTraineeResponseCreate {

        @Test
        @DisplayName("maps Trainee to TraineeRegistrationResponse")
        void mapTraineeResponseCreate_mapsCorrectly() {
            trainee.setUsername("john.doe");
            trainee.setPassword("tempPass123");

            var result = mapper.mapTraineeResponseCreate(trainee);

            assertThat(result.getUsername()).isEqualTo("john.doe");
            assertThat(result.getPassword()).isEqualTo("tempPass123");
        }
    }

    @Nested
    @DisplayName("mapTrainerResponseGet")
    class MapTrainerResponseGet {

        @Test
        @DisplayName("maps Trainer to TrainerResponse")
        void mapTrainerResponseGet_mapsCorrectly() {
            var result = mapper.mapTrainerResponseGet(trainer);

            assertThat(result.getUsername()).isEqualTo("trainer.one");
            assertThat(result.getFirstName()).isEqualTo("Trainer");
            assertThat(result.getLastName()).isEqualTo("One");
            assertThat(result.getSpecialization()).isEqualTo("LIFTING");
        }
    }

    @Nested
    @DisplayName("mapTraineeResponseGet")
    class MapTraineeResponseGet {

        @Test
        @DisplayName("maps Trainee to TraineeResponseExtend")
        void mapTraineeResponseGet_mapsCorrectly() {
            var result = mapper.mapTraineeResponseGet(trainee);

            assertThat(result.getUsername()).isEqualTo("john.doe");
            assertThat(result.getFirstName()).isEqualTo("John");
            assertThat(result.getLastName()).isEqualTo("Doe");
            assertThat(result.getDateOfBirth()).isEqualTo(LocalDate.of(1990, 1, 15));
            assertThat(result.getAddress()).isEqualTo("Calle 123");
            assertThat(result.isActive()).isTrue();
            assertThat(result.getListTrainers()).isEmpty();
        }
    }

    @Nested
    @DisplayName("mapTrainingResponseType")
    class MapTrainingResponseType {

        @Test
        @DisplayName("maps TrainingType to TrainingResponseAll")
        void mapTrainingResponseType_mapsCorrectly() {
            var result = mapper.mapTrainingResponseType(trainingType);

            assertThat(result.getTrainingType()).isEqualTo("LIFTING");
            assertThat(result.getTrainingTypeId()).isEqualTo(1L);
        }
    }

    @Nested
    @DisplayName("mapTrainingEntity")
    class MapTrainingEntity {

        @Test
        @DisplayName("maps TrainingDTO to Training entity")
        void mapTrainingEntity_mapsCorrectly() {
            TrainingDTO dto = new TrainingDTO();
            dto.setName("Cardio Session");
            dto.setDate(LocalDate.of(2024, 2, 1));
            dto.setDuration(90);

            var result = mapper.mapTrainingEntity(dto);

            assertThat(result.getName()).isEqualTo("Cardio Session");
            assertThat(result.getTrainingDate()).isEqualTo(LocalDate.of(2024, 2, 1));
            assertThat(result.getDurationTraining()).isEqualTo(90);
        }
    }

    @Nested
    @DisplayName("mapTraineeEntity")
    class MapTraineeEntity {

        @Test
        @DisplayName("maps TraineeRegistrationRequest to Trainee entity")
        void mapTraineeEntity_mapsCorrectly() {
            TraineeRegistrationRequest request = new TraineeRegistrationRequest(
                    "John", "Doe", LocalDate.of(1990, 1, 15), "Calle 123"
            );
            when(generator.createUser("John", "Doe")).thenReturn("john.doe");
            when(generator.generatePass()).thenReturn("generatedPass");

            var result = mapper.mapTraineeEntity(request);

            assertThat(result.getFirstName()).isEqualTo("John");
            assertThat(result.getLastName()).isEqualTo("Doe");
            assertThat(result.getUsername()).isEqualTo("john.doe");
            assertThat(result.getPassword()).isEqualTo("generatedPass");
            assertThat(result.getAddress()).isEqualTo("Calle 123");
            assertThat(result.getDateOfBirth()).isEqualTo(LocalDate.of(1990, 1, 15));
            assertThat(result.getIsActive()).isTrue();
        }
    }

    @Nested
    @DisplayName("mapTrainerEntity")
    class MapTrainerEntity {

        @Test
        @DisplayName("maps TrainerRegistrationRequest to Trainer entity")
        void mapTrainerEntity_mapsCorrectly() {
            TrainerRegistrationRequest request = new TrainerRegistrationRequest();
            request.setFirstName("Jane");
            request.setLastName("Trainer");
            request.setSpecialization("CARDIO");
            when(generator.createUser("Jane", "Trainer")).thenReturn("jane.trainer");
            when(generator.generatePass()).thenReturn("tempPass");

            var result = mapper.mapTrainerEntity(request);

            assertThat(result.getFirstName()).isEqualTo("Jane");
            assertThat(result.getLastName()).isEqualTo("Trainer");
            assertThat(result.getUsername()).isEqualTo("jane.trainer");
            assertThat(result.getPassword()).isEqualTo("tempPass");
            assertThat(result.getIsActive()).isTrue();
        }
    }

    @Nested
    @DisplayName("mapStatisticDto")
    class MapStatisticDto {

        @Test
        @DisplayName("maps Training to StatisticDto")
        void mapStatisticDto_mapsCorrectly() {
            var result = mapper.mapStatisticDto(training);

            assertThat(result.getTrainerUsername()).isEqualTo("trainer.one");
            assertThat(result.getTrainerFirstName()).isEqualTo("Trainer");
            assertThat(result.getTrainerLastName()).isEqualTo("One");
            assertThat(result.getIsActive()).isTrue();
            assertThat(result.getTrainingDate()).isEqualTo(LocalDate.of(2024, 1, 15));
            assertThat(result.getTrainingDuration()).isEqualTo(60);
        }
    }
}
