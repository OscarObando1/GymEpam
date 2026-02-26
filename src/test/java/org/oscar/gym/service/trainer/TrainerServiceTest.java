package org.oscar.gym.service.trainer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oscar.gym.dtos.ChangePassDTO;
import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerUpdateRequest;
import org.oscar.gym.dtos.response.trainer.TrainerRegistrationResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponseExtend;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.TrainingType;
import org.oscar.gym.enums.TypeTraining;
import org.oscar.gym.exception.TraineeNotFoundException;
import org.oscar.gym.exception.TrainerNotFoundException;
import org.oscar.gym.repository.trainee.TraineeRepository;
import org.oscar.gym.repository.trainer.TrainerRepository;
import org.oscar.gym.repository.trainig_types.TrainingTypes;
import org.oscar.gym.utils.Mapper;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TrainerService")
class TrainerServiceTest {

    @Mock
    private TrainingTypes typeRepository;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainerRepository repository;
    @Mock
    private Mapper mapper;

    @InjectMocks
    private TrainerService trainerService;

    private Trainer trainer;
    private TrainerResponseExtend trainerResponseExtend;
    private TrainingType trainingType;

    @BeforeEach
    void setUp() {
        trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setName(TypeTraining.LIFTING);

        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("John");
        trainer.setLastName("Trainer");
        trainer.setUsername("trainer.john");
        trainer.setPassword("pass");
        trainer.setIsActive(true);
        trainer.setSpecialization(trainingType);
        trainer.setTrainees(new ArrayList<>());

        trainerResponseExtend = new TrainerResponseExtend();
        trainerResponseExtend.setUsername("trainer.john");
        trainerResponseExtend.setFirstName("John");
        trainerResponseExtend.setLastName("Trainer");
        trainerResponseExtend.setSpecialization("LIFTING");
        trainerResponseExtend.setActive(true);
        trainerResponseExtend.setTraineeResponseList(List.of());
    }

    @Nested
    @DisplayName("saveTrainer")
    class SaveTrainer {

        @Test
        @DisplayName("returns TrainerRegistrationResponse when creating trainer")
        void saveTrainer_returnsResponse() {
            TrainerRegistrationRequest request = new TrainerRegistrationRequest();
            request.setFirstName("John");
            request.setLastName("Trainer");
            request.setSpecialization("LIFTING");

            when(mapper.mapTrainerEntity(request)).thenReturn(trainer);
            when(typeRepository.findType("LIFTING")).thenReturn(trainingType);
            when(repository.saveEntity(any(Trainer.class))).thenReturn(trainer);
            TrainerRegistrationResponse response = new TrainerRegistrationResponse();
            response.setUsername("trainer.john");
            response.setPassword("tempPass");
            when(mapper.mapTrainerResponse(trainer)).thenReturn(response);

            TrainerRegistrationResponse result = trainerService.saveTrainer(request);

            assertThat(result.getUsername()).isEqualTo("trainer.john");
            assertThat(result.getPassword()).isEqualTo("tempPass");
            verify(repository).saveEntity(any(Trainer.class));
        }
    }

    @Nested
    @DisplayName("findTrainer")
    class FindTrainer {

        @Test
        @DisplayName("returns TrainerResponseExtend when trainer exists")
        void findTrainer_returnsResponseWhenExists() {
            when(repository.findEntity("trainer.john")).thenReturn(trainer);
            when(mapper.mapTrainerResponseGetMethod(trainer)).thenReturn(trainerResponseExtend);

            TrainerResponseExtend result = trainerService.findTrainer("trainer.john");

            assertThat(result.getUsername()).isEqualTo("trainer.john");
            verify(repository).findEntity("trainer.john");
        }

        @Test
        @DisplayName("throws TrainerNotFoundException when trainer not found")
        void findTrainer_throwsWhenNotFound() {
            when(repository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> trainerService.findTrainer("unknown"))
                    .isInstanceOf(TrainerNotFoundException.class)
                    .hasMessageContaining("trainer not found");
        }
    }

    @Nested
    @DisplayName("updateTrainer")
    class UpdateTrainer {

        @Test
        @DisplayName("returns updated TrainerResponseExtend when trainer exists")
        void updateTrainer_returnsUpdated() {
            TrainerUpdateRequest request = new TrainerUpdateRequest();
            request.setUsername("trainer.john");
            request.setFirstName("John");
            request.setLastName("Trainer");
            request.setSpecialization("CARDIO");
            request.setIsActive(true);

            when(repository.findEntity("trainer.john")).thenReturn(trainer);
            when(repository.updateEntity(any(Trainer.class))).thenReturn(trainer);
            when(mapper.mapTrainerResponseGetMethod(trainer)).thenReturn(trainerResponseExtend);

            TrainerResponseExtend result = trainerService.updateTrainer(request);

            assertThat(result.getUsername()).isEqualTo("trainer.john");
            verify(repository).updateEntity(trainer);
        }

        @Test
        @DisplayName("throws TrainerNotFoundException when trainer not found")
        void updateTrainer_throwsWhenNotFound() {
            TrainerUpdateRequest request = new TrainerUpdateRequest();
            request.setUsername("unknown");
            when(repository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> trainerService.updateTrainer(request))
                    .isInstanceOf(TrainerNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("activeOrDeactivateTrainer")
    class ActiveOrDeactivateTrainer {

        @Test
        @DisplayName("deactivates trainer when active")
        void activeOrDeactivateTrainer_deactivates() {
            UserActivateDeActivate dto = new UserActivateDeActivate();
            dto.setUsername("trainer.john");
            dto.setIsActive(false);
            when(repository.findEntity("trainer.john")).thenReturn(trainer);
            when(repository.updateEntity(any(Trainer.class))).thenReturn(trainer);

            trainerService.activeOrDeactivateTrainer(dto);

            assertThat(trainer.getIsActive()).isFalse();
            verify(repository).updateEntity(trainer);
        }

        @Test
        @DisplayName("activates trainer when inactive")
        void activeOrDeactivateTrainer_activates() {
            trainer.setIsActive(false);
            UserActivateDeActivate dto = new UserActivateDeActivate();
            dto.setUsername("trainer.john");
            dto.setIsActive(true);
            when(repository.findEntity("trainer.john")).thenReturn(trainer);
            when(repository.updateEntity(any(Trainer.class))).thenReturn(trainer);

            trainerService.activeOrDeactivateTrainer(dto);

            assertThat(trainer.getIsActive()).isTrue();
            verify(repository).updateEntity(trainer);
        }

        @Test
        @DisplayName("throws TrainerNotFoundException when trainer not found")
        void activeOrDeactivateTrainer_throwsWhenNotFound() {
            UserActivateDeActivate dto = new UserActivateDeActivate();
            dto.setUsername("unknown");
            dto.setIsActive(false);
            when(repository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> trainerService.activeOrDeactivateTrainer(dto))
                    .isInstanceOf(TrainerNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("updatePassword")
    class UpdatePassword {

        @Test
        @DisplayName("updates password when trainer exists")
        void updatePassword_succeeds() {
            ChangePassDTO dto = new ChangePassDTO();
            dto.setUsername("trainer.john");
            dto.setNewPass("newPass");
            when(repository.findEntity("trainer.john")).thenReturn(trainer);
            when(repository.updateEntity(any(Trainer.class))).thenReturn(trainer);

            trainerService.updatePassword(dto);

            assertThat(trainer.getPassword()).isEqualTo("newPass");
            verify(repository).updateEntity(trainer);
        }

        @Test
        @DisplayName("throws when trainer not found")
        void updatePassword_throwsWhenNotFound() {
            ChangePassDTO dto = new ChangePassDTO();
            dto.setUsername("unknown");
            dto.setNewPass("new");
            when(repository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> trainerService.updatePassword(dto))
                    .isInstanceOf(TraineeNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("trainerWithoutTrainee")
    class TrainerWithoutTrainee {

        @Test
        @DisplayName("returns list of trainers without trainee")
        void trainerWithoutTrainee_returnsList() {
            Trainee trainee = new Trainee();
            trainee.setUsername("trainee.john");
            TrainerResponse tr = new TrainerResponse();
            tr.setUsername("trainer.john");
            tr.setSpecialization("LIFTING");

            when(traineeRepository.findEntity("trainee.john")).thenReturn(trainee);
            when(repository.getTrainerWithoutTrainee("trainee.john")).thenReturn(List.of(trainer));
            when(mapper.mapTrainerResponseGet(any(Trainer.class))).thenReturn(tr);

            List<TrainerResponse> result = trainerService.trainerWithoutTrainee("trainee.john");

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getUsername()).isEqualTo("trainer.john");
        }

        @Test
        @DisplayName("throws TraineeNotFoundException when trainee not found")
        void trainerWithoutTrainee_throwsWhenTraineeNotFound() {
            when(traineeRepository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> trainerService.trainerWithoutTrainee("unknown"))
                    .isInstanceOf(TraineeNotFoundException.class);
        }

        @Test
        @DisplayName("throws TrainerNotFoundException when no trainers found")
        void trainerWithoutTrainee_throwsWhenNoTrainers() {
            Trainee trainee = new Trainee();
            trainee.setUsername("trainee.john");
            when(traineeRepository.findEntity("trainee.john")).thenReturn(trainee);
            when(repository.getTrainerWithoutTrainee("trainee.john")).thenReturn(null);

            assertThatThrownBy(() -> trainerService.trainerWithoutTrainee("trainee.john"))
                    .isInstanceOf(TrainerNotFoundException.class);
        }
    }
}
