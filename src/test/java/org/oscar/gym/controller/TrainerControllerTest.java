package org.oscar.gym.controller;

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
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerUpdateRequest;
import org.oscar.gym.dtos.response.trainer.TrainerRegistrationResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponseExtend;
import org.oscar.gym.service.trainer.ITrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("TrainerController")
class TrainerControllerTest {

    @Mock
    private ITrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    @Nested
    @DisplayName("GET /trainer - getTrainer")
    class GetTrainer {

        @Test
        @DisplayName("returns 200 and trainer when it exists")
        void getTrainer_returnsOkAndTrainer() {
            String username = "trainer.john";
            TrainerResponseExtend expected = new TrainerResponseExtend();
            expected.setUsername(username);
            expected.setFirstName("John");
            expected.setLastName("Trainer");
            expected.setSpecialization("LIFTING");
            expected.setActive(true);
            expected.setTraineeResponseList(List.of());
            
            when(trainerService.findTrainer(username)).thenReturn(expected);

            ResponseEntity<TrainerResponseExtend> result = trainerController.getTrainer(username);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            TrainerResponseExtend body = result.getBody();
            assertThat(body).isNotNull();
            assertThat(body).isEqualTo(expected);
            assertThat(body.getUsername()).isEqualTo(username);
            verify(trainerService).findTrainer(username);
        }
    }

    @Nested
    @DisplayName("POST /trainer - saveTrainer")
    class SaveTrainer {

        @Test
        @DisplayName("returns 201 and TrainerRegistrationResponse when creating")
        void saveTrainer_returnsCreatedAndResponse() {
            TrainerRegistrationRequest request = new TrainerRegistrationRequest();
            request.setFirstName("John");
            request.setLastName("Trainer");
            request.setSpecialization("LIFTING");
            
            TrainerRegistrationResponse expected = new TrainerRegistrationResponse();
            expected.setUsername("trainer.john");
            expected.setPassword("tempPass123");
            
            when(trainerService.saveTrainer(any(TrainerRegistrationRequest.class))).thenReturn(expected);

            ResponseEntity<TrainerRegistrationResponse> result = trainerController.saveTrainer(request);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            TrainerRegistrationResponse body = result.getBody();
            assertThat(body).isNotNull();
            assertThat(body).isEqualTo(expected);
            assertThat(body.getUsername()).isEqualTo("trainer.john");
            verify(trainerService).saveTrainer(request);
        }
    }

    @Nested
    @DisplayName("PUT /trainer - updateTrainer")
    class UpdateTrainer {

        @Test
        @DisplayName("returns 200 and TrainerResponseExtend when updating")
        void updateTrainer_returnsOkAndUpdatedTrainer() {
            TrainerUpdateRequest request = new TrainerUpdateRequest();
            request.setUsername("trainer.john");
            request.setFirstName("John");
            request.setLastName("Trainer");
            request.setSpecialization("CARDIO");
            request.setIsActive(true);
            
            TrainerResponseExtend expected = new TrainerResponseExtend();
            expected.setUsername("trainer.john");
            expected.setFirstName("John");
            expected.setLastName("Trainer");
            expected.setSpecialization("CARDIO");
            expected.setActive(true);
            expected.setTraineeResponseList(List.of());
            
            when(trainerService.updateTrainer(any(TrainerUpdateRequest.class))).thenReturn(expected);

            ResponseEntity<TrainerResponseExtend> result = trainerController.updateTrainer(request);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).isEqualTo(expected);
            verify(trainerService).updateTrainer(request);
        }
    }

    @Nested
    @DisplayName("PATCH /trainer/update/{id} - updateActiveTrainee")
    class UpdateActiveTrainee {

        @Test
        @DisplayName("returns 200 and activates or deactivates trainer")
        void updateActiveTrainee_returnsOkAndCallsService() {
            UserActivateDeActivate dto = new UserActivateDeActivate();
            dto.setUsername("trainer.john");
            dto.setIsActive(false);
            doNothing().when(trainerService).activeOrDeactivateTrainer(any(UserActivateDeActivate.class));

            ResponseEntity<?> result = trainerController.updateActiveTrainee(dto);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            verify(trainerService).activeOrDeactivateTrainer(dto);
        }
    }

    @Nested
    @DisplayName("POST trainer/change - changePassword")
    class ChangePassword {

        @Test
        @DisplayName("returns 200 and updates password")
        void changePassword_returnsOkAndCallsService() {
            ChangePassDTO dto = new ChangePassDTO();
            dto.setUsername("trainer.john");
            dto.setOldPass("oldPass");
            dto.setNewPass("newPass");
            doNothing().when(trainerService).updatePassword(any(ChangePassDTO.class));

            ResponseEntity<?> result = trainerController.changePassword(dto);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            verify(trainerService).updatePassword(dto);
        }
    }

    @Nested
    @DisplayName("GET trainer/not-asigned - trainerWithouTrainee")
    class TrainerWithoutTrainee {

        @Test
        @DisplayName("returns 200 and list of trainers without trainee")
        void trainerWithouTrainee_returnsOkAndList() {
            String username = "trainee.john";
            
            TrainerResponse t1 = new TrainerResponse();
            t1.setUsername("trainer1");
            t1.setFirstName("Trainer");
            t1.setLastName("One");
            t1.setSpecialization("LIFTING");
            
            TrainerResponse t2 = new TrainerResponse();
            t2.setUsername("trainer2");
            t2.setFirstName("Trainer");
            t2.setLastName("Two");
            t2.setSpecialization("CARDIO");
            
            List<TrainerResponse> expected = List.of(t1, t2);
            when(trainerService.trainerWithoutTrainee(username)).thenReturn(expected);

            ResponseEntity<List<TrainerResponse>> result = trainerController.trainerWithouTrainee(username);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).isEqualTo(expected);
            assertThat(result.getBody()).hasSize(2);
            verify(trainerService).trainerWithoutTrainee(username);
        }
    }
}