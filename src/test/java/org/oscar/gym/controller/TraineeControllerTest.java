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
import org.oscar.gym.dtos.ChangePassDTO;
import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateListTrainerRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.dtos.response.trainee.TraineeRegistrationResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponseExtend;
import org.oscar.gym.dtos.response.trainer.TrainerResponse;
import org.oscar.gym.service.trainee.ITraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("TraineeController")
class TraineeControllerTest {

    @Mock
    private ITraineeService traineeService;

    @InjectMocks
    private TraineeController traineeController;

    @Nested
    @DisplayName("GET /trainee - getTrainee")
    class GetTrainee {

        @Test
        @DisplayName("returns 200 and trainee when it exists")
        void getTrainee_returnsOkAndTrainee() {
            String username = "john.doe";
            TraineeResponseExtend expected = new TraineeResponseExtend(
                    username, "John", "Doe", LocalDate.of(1990, 1, 15),
                    "Calle 123", true, List.of()
            );
            when(traineeService.findTrainee(username)).thenReturn(expected);

            ResponseEntity<TraineeResponseExtend> result = traineeController.getTrainee(username);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            TraineeResponseExtend body = result.getBody();
            assertThat(body).isNotNull();
            assertThat(body).isEqualTo(expected);
            assertThat(body.getUsername()).isEqualTo(username);
            verify(traineeService).findTrainee(username);
        }
    }

    @Nested
    @DisplayName("POST /trainee - createTrainee")
    class CreateTrainee {

        @Test
        @DisplayName("returns 201 and TraineeRegistrationResponse when creating")
        void createTrainee_returnsCreatedAndResponse() {
            TraineeRegistrationRequest request = new TraineeRegistrationRequest(
                    "John", "Doe", LocalDate.of(1990, 1, 15), "Calle 123"
            );
            TraineeRegistrationResponse expected = new TraineeRegistrationResponse("john.doe", "tempPass123");
            when(traineeService.saveTrainee(any(TraineeRegistrationRequest.class))).thenReturn(expected);

            ResponseEntity<TraineeRegistrationResponse> result = traineeController.createTrainee(request);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            TraineeRegistrationResponse body = result.getBody();
            assertThat(body).isNotNull();
            assertThat(body).isEqualTo(expected);
            assertThat(body.getUsername()).isEqualTo("john.doe");
            verify(traineeService).saveTrainee(request);
        }
    }

    @Nested
    @DisplayName("PUT /trainee - updateTrainee")
    class UpdateTrainee {

        @Test
        @DisplayName("returns 200 and TraineeResponseExtend when updating")
        void updateTrainee_returnsOkAndUpdatedTrainee() {
            TraineeUpdateRequest request = new TraineeUpdateRequest(
                    "john.doe", "John", "Doe", LocalDate.of(1990, 1, 15), "Nueva dirección", true
            );
            TraineeResponseExtend expected = new TraineeResponseExtend(
                    "john.doe", "John", "Doe", LocalDate.of(1990, 1, 15),
                    "Nueva dirección", true, List.of()
            );
            when(traineeService.updateTrainee(any(TraineeUpdateRequest.class))).thenReturn(expected);

            ResponseEntity<TraineeResponseExtend> result = traineeController.updateTrainee(request);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).isEqualTo(expected);
            verify(traineeService).updateTrainee(request);
        }
    }

    @Nested
    @DisplayName("PUT /trainee/list-trainer - updateListTrainer")
    class UpdateListTrainer {

        @Test
        @DisplayName("returns 200 and updated trainer list")
        void updateListTrainer_returnsOkAndList() {
            TraineeUpdateListTrainerRequest request = new TraineeUpdateListTrainerRequest();
            request.setUsername("john.doe");
            request.setListUsernameTrainer(List.of("trainer1", "trainer2"));

            TrainerResponse t1 = new TrainerResponse();
            t1.setUsername("trainer1");
            t1.setFirstName("Trainer");
            t1.setLastName("One");
            t1.setSpecialization("Fitness");
            List<TrainerResponse> expected = List.of(t1);
            when(traineeService.updateListOfTrainer(any(TraineeUpdateListTrainerRequest.class))).thenReturn(expected);

            ResponseEntity<List<TrainerResponse>> result = traineeController.updateListTrainer(request);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).isEqualTo(expected);
            assertThat(result.getBody()).hasSize(1);
            verify(traineeService).updateListOfTrainer(request);
        }
    }

    @Nested
    @DisplayName("DELETE /trainee - deleteTrainee")
    class DeleteTrainee {

        @Test
        @DisplayName("returns 200 and calls service to delete")
        void deleteTrainee_returnsOkAndCallsService() {
            String username = "john.doe";
            doNothing().when(traineeService).deleteTrainee(username);

            ResponseEntity<?> result = traineeController.deleteTrainee(username);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            verify(traineeService).deleteTrainee(username);
        }
    }

    @Nested
    @DisplayName("PATCH /trainee/update - updateActiveTrainee")
    class UpdateActiveTrainee {

        @Test
        @DisplayName("returns 200 and activates or deactivates trainee")
        void updateActiveTrainee_returnsOkAndCallsService() {
            UserActivateDeActivate dto = new UserActivateDeActivate();
            dto.setUsername("john.doe");
            dto.setIsActive(false);
            doNothing().when(traineeService).activeOrDeactivateTraine(any(UserActivateDeActivate.class));

            ResponseEntity<?> result = traineeController.updateActiveTrainee(dto);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            verify(traineeService).activeOrDeactivateTraine(dto);
        }
    }

    @Nested
    @DisplayName("POST trainee/change - changePassword")
    class ChangePassword {

        @Test
        @DisplayName("returns 200 and updates password")
        void changePassword_returnsOkAndCallsService() {
            ChangePassDTO dto = new ChangePassDTO();
            dto.setUsername("john.doe");
            dto.setOldPass("oldPass");
            dto.setNewPass("newPass");
            doNothing().when(traineeService).updatePassword(any(ChangePassDTO.class));

            ResponseEntity<?> result = traineeController.changePassword(dto);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            verify(traineeService).updatePassword(dto);
        }
    }
}
