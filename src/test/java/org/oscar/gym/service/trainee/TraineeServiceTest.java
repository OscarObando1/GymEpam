package org.oscar.gym.service.trainee;

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
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateListTrainerRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.dtos.response.trainee.TraineeRegistrationResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponseExtend;
import org.oscar.gym.dtos.response.trainer.TrainerResponse;
import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.entity.TrainingType;
import org.oscar.gym.enums.TypeTraining;
import org.oscar.gym.exception.TraineeNotFoundException;
import org.oscar.gym.repository.trainee.TraineeRepository;
import org.oscar.gym.repository.trainer.TrainerRepository;
import org.oscar.gym.utils.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TraineeService")
class TraineeServiceTest {

    @Mock
    private TraineeRepository repository;
    @Mock
    private Mapper mapper;
    @Mock
    private TrainerRepository repoTrainer;
    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private TraineeService traineeService;

    private Trainee trainee;
    private TraineeResponseExtend traineeResponseExtend;

    @BeforeEach
    void setUp() {
        trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setUsername("john.doe");
        trainee.setPassword("encodedPass");
        trainee.setAddress("Calle 123");
        trainee.setDateOfBirth(LocalDate.of(1990, 1, 15));
        trainee.setIsActive(true);
        trainee.setTrainers(new ArrayList<>());

        traineeResponseExtend = new TraineeResponseExtend(
                "john.doe", "John", "Doe", LocalDate.of(1990, 1, 15),
                "Calle 123", true, List.of()
        );
    }

    @Nested
    @DisplayName("saveTrainee")
    class SaveTrainee {

        @Test
        @DisplayName("returns TraineeRegistrationResponse when creating trainee")
        void saveTrainee_returnsResponse() {
            TraineeRegistrationRequest request = new TraineeRegistrationRequest(
                    "John", "Doe", LocalDate.of(1990, 1, 15), "Calle 123"
            );
            when(mapper.mapTraineeEntity(request)).thenReturn(trainee);
            when(mapper.mapTraineeResponseCreate(trainee)).thenReturn(
                    new TraineeRegistrationResponse("john.doe", "tempPass")
            );
            when(encoder.encode(anyString())).thenReturn("encodedPass");
            when(repository.saveEntity(any(Trainee.class))).thenReturn(trainee);

            TraineeRegistrationResponse result = traineeService.saveTrainee(request);

            assertThat(result.getUsername()).isEqualTo("john.doe");
            assertThat(result.getPassword()).isEqualTo("tempPass");
            verify(repository).saveEntity(any(Trainee.class));
            verify(encoder).encode(anyString());
        }
    }

    @Nested
    @DisplayName("findTrainee")
    class FindTrainee {

        @Test
        @DisplayName("returns TraineeResponseExtend when trainee exists")
        void findTrainee_returnsResponseWhenExists() {
            when(repository.findEntity("john.doe")).thenReturn(trainee);
            when(mapper.mapTraineeResponseGet(trainee)).thenReturn(traineeResponseExtend);

            TraineeResponseExtend result = traineeService.findTrainee("john.doe");

            assertThat(result.getUsername()).isEqualTo("john.doe");
            assertThat(result.getFirstName()).isEqualTo("John");
            verify(repository).findEntity("john.doe");
        }

        @Test
        @DisplayName("throws TraineeNotFoundException when trainee not found")
        void findTrainee_throwsWhenNotFound() {
            when(repository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> traineeService.findTrainee("unknown"))
                    .isInstanceOf(TraineeNotFoundException.class)
                    .hasMessageContaining("trainee not found");
        }
    }

    @Nested
    @DisplayName("updateTrainee")
    class UpdateTrainee {

        @Test
        @DisplayName("returns updated TraineeResponseExtend when trainee exists")
        void updateTrainee_returnsUpdated() {
            TraineeUpdateRequest request = new TraineeUpdateRequest(
                    "john.doe", "John", "Doe", LocalDate.of(1990, 1, 15), "Nueva dirección", true
            );
            when(repository.findEntity("john.doe")).thenReturn(trainee);
            when(repository.updateEntity(any(Trainee.class))).thenReturn(trainee);
            when(mapper.mapTraineeResponseGet(trainee)).thenReturn(traineeResponseExtend);

            TraineeResponseExtend result = traineeService.updateTrainee(request);

            assertThat(result.getUsername()).isEqualTo("john.doe");
            verify(repository).updateEntity(trainee);
            assertThat(trainee.getAddress()).isEqualTo("Nueva dirección");
        }

        @Test
        @DisplayName("throws TraineeNotFoundException when trainee not found")
        void updateTrainee_throwsWhenNotFound() {
            TraineeUpdateRequest request = new TraineeUpdateRequest(
                    "unknown", "John", "Doe", LocalDate.of(1990, 1, 15), "Address", true
            );
            when(repository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> traineeService.updateTrainee(request))
                    .isInstanceOf(TraineeNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("deleteTrainee")
    class DeleteTrainee {

        @Test
        @DisplayName("deletes trainee when exists")
        void deleteTrainee_succeedsWhenExists() {
            when(repository.deleteEntity("john.doe")).thenReturn(trainee);

            traineeService.deleteTrainee("john.doe");

            verify(repository).deleteEntity("john.doe");
        }

        @Test
        @DisplayName("throws TraineeNotFoundException when trainee not found")
        void deleteTrainee_throwsWhenNotFound() {
            when(repository.deleteEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> traineeService.deleteTrainee("unknown"))
                    .isInstanceOf(TraineeNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("activeOrDeactivateTraine")
    class ActiveOrDeactivateTraine {

        @Test
        @DisplayName("deactivates trainee when active")
        void activeOrDeactivateTraine_deactivates() {
            UserActivateDeActivate dto = new UserActivateDeActivate();
            dto.setUsername("john.doe");
            dto.setIsActive(false);
            when(repository.findEntity("john.doe")).thenReturn(trainee);
            when(repository.updateEntity(any(Trainee.class))).thenReturn(trainee);

            traineeService.activeOrDeactivateTraine(dto);

            assertThat(trainee.getIsActive()).isFalse();
            verify(repository).updateEntity(trainee);
        }

        @Test
        @DisplayName("activates trainee when inactive")
        void activeOrDeactivateTraine_activates() {
            trainee.setIsActive(false);
            UserActivateDeActivate dto = new UserActivateDeActivate();
            dto.setUsername("john.doe");
            dto.setIsActive(true);
            when(repository.findEntity("john.doe")).thenReturn(trainee);
            when(repository.updateEntity(any(Trainee.class))).thenReturn(trainee);

            traineeService.activeOrDeactivateTraine(dto);

            assertThat(trainee.getIsActive()).isTrue();
            verify(repository).updateEntity(trainee);
        }

        @Test
        @DisplayName("throws TraineeNotFoundException when trainee not found")
        void activeOrDeactivateTraine_throwsWhenNotFound() {
            UserActivateDeActivate dto = new UserActivateDeActivate();
            dto.setUsername("unknown");
            dto.setIsActive(false);
            when(repository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> traineeService.activeOrDeactivateTraine(dto))
                    .isInstanceOf(TraineeNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("updatePassword")
    class UpdatePassword {

        @Test
        @DisplayName("updates password when trainee exists")
        void updatePassword_succeeds() {
            ChangePassDTO dto = new ChangePassDTO();
            dto.setUsername("john.doe");
            dto.setOldPass("old");
            dto.setNewPass("newPass");
            when(repository.findEntity("john.doe")).thenReturn(trainee);
            when(encoder.encode("newPass")).thenReturn("encodedNew");
            when(repository.updateEntity(any(Trainee.class))).thenReturn(trainee);

            traineeService.updatePassword(dto);

            assertThat(trainee.getPassword()).isEqualTo("encodedNew");
            verify(repository).updateEntity(trainee);
        }

        @Test
        @DisplayName("throws TraineeNotFoundException when trainee not found")
        void updatePassword_throwsWhenNotFound() {
            ChangePassDTO dto = new ChangePassDTO();
            dto.setUsername("unknown");
            dto.setNewPass("new");
            when(repository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> traineeService.updatePassword(dto))
                    .isInstanceOf(TraineeNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("updateListOfTrainer")
    class UpdateListOfTrainer {

        @Test
        @DisplayName("returns updated trainer list when trainee exists")
        void updateListOfTrainer_returnsList() {
            Trainer trainer = createTrainer("trainer1");
            TraineeUpdateListTrainerRequest request = new TraineeUpdateListTrainerRequest();
            request.setUsername("john.doe");
            request.setListUsernameTrainer(List.of("trainer1"));

            TrainerResponse tr = new TrainerResponse();
            tr.setUsername("trainer1");
            tr.setFirstName("Trainer");
            tr.setLastName("One");
            tr.setSpecialization("LIFTING");

            when(repository.findEntity("john.doe")).thenReturn(trainee);
            when(repoTrainer.findEntity("trainer1")).thenReturn(trainer);
            when(repository.updateEntity(any(Trainee.class))).thenReturn(trainee);
            when(mapper.mapTrainerResponseGet(any(Trainer.class))).thenReturn(tr);

            List<TrainerResponse> result = traineeService.updateListOfTrainer(request);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getUsername()).isEqualTo("trainer1");
            verify(repository).updateEntity(trainee);
        }

        @Test
        @DisplayName("throws TraineeNotFoundException when trainee not found")
        void updateListOfTrainer_throwsWhenNotFound() {
            TraineeUpdateListTrainerRequest request = new TraineeUpdateListTrainerRequest();
            request.setUsername("unknown");
            request.setListUsernameTrainer(List.of("trainer1"));
            when(repository.findEntity("unknown")).thenReturn(null);

            assertThatThrownBy(() -> traineeService.updateListOfTrainer(request))
                    .isInstanceOf(TraineeNotFoundException.class);
        }
    }

    private Trainer createTrainer(String username) {
        Trainer t = new Trainer();
        t.setId(1L);
        t.setUsername(username);
        t.setFirstName("Trainer");
        t.setLastName("One");
        t.setIsActive(true);
        TrainingType tt = new TrainingType();
        tt.setId(1L);
        tt.setName(TypeTraining.LIFTING);
        t.setSpecialization(tt);
        t.setTrainees(new ArrayList<>());
        return t;
    }
}
