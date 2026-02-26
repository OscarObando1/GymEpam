package org.oscar.gym.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oscar.gym.exception.TraineeNotFoundException;
import org.oscar.gym.exception.TrainerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("HandlerException")
class HandlerExceptionTest {

    @InjectMocks
    private HandlerException handlerException;

    @Nested
    @DisplayName("handleValidationErrors")
    class HandleValidationErrors {

        @Test
        @DisplayName("returns 400 with field errors for MethodArgumentNotValidException")
        void handleValidationErrors_returnsBadRequest() {
            MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
            BindingResult bindingResult = mock(BindingResult.class);
            FieldError fieldError = new FieldError("obj", "firstName", "firstname is mandatory");
            when(ex.getBindingResult()).thenReturn(bindingResult);
            when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

            ResponseEntity<?> result = handlerException.handleValidationErrors(ex);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            @SuppressWarnings("unchecked")
            var body = (java.util.Map<String, String>) result.getBody();
            assertThat(body).isNotNull();
            assertThat(body).containsEntry("firstName", "firstname is mandatory");
        }
    }

    @Nested
    @DisplayName("traineeNotFound")
    class TraineeNotFound {

        @Test
        @DisplayName("returns 404 with message for TraineeNotFoundException")
        void traineeNotFound_returnsNotFound() {
            TraineeNotFoundException ex = new TraineeNotFoundException("trainee not found with this username john.doe");

            ResponseEntity<String> result = handlerException.traineeNotFound(ex);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(result.getBody()).isEqualTo("trainee not found with this username john.doe");
        }
    }

    @Nested
    @DisplayName("trainerNotFound")
    class TrainerNotFound {

        @Test
        @DisplayName("returns 404 with message for TrainerNotFoundException")
        void trainerNotFound_returnsNotFound() {
            TrainerNotFoundException ex = new TrainerNotFoundException("trainer not found with this username trainer.john");

            ResponseEntity<String> result = handlerException.trainerNotFound(ex);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(result.getBody()).isEqualTo("trainer not found with this username trainer.john");
        }
    }
}
