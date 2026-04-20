package org.oscar.gym.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oscar.gym.security.AuthCredentials;
import org.oscar.gym.security.JwtToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoginController")
class LoginControllerTest {

    @Mock
    private JwtToken token;
    @Mock
    private AuthCredentials credentials;

    @InjectMocks
    private LoginController loginController;

    @Nested
    @DisplayName("POST /login")
    class Login {

        @Test
        @DisplayName("returns 200 and JWT when credentials valid")
        void login_returnsOkAndToken() {
            org.oscar.gym.dtos.Login dto = new org.oscar.gym.dtos.Login("john.doe", "password123");
            when(credentials.isAuth(dto)).thenReturn(true);
            when(token.create("john.doe")).thenReturn("jwt-token-123");

            ResponseEntity<?> result = loginController.login(dto);

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).isEqualTo("jwt-token-123");
            verify(credentials).isAuth(dto);
            verify(token).create("john.doe");
        }

        @Test
        @DisplayName("calls credentials and token services")
        void login_invokesServices() {
            org.oscar.gym.dtos.Login dto = new org.oscar.gym.dtos.Login("user", "pass");
            when(credentials.isAuth(dto)).thenReturn(true);
            when(token.create(anyString())).thenReturn("token");

            loginController.login(dto);

            verify(credentials).isAuth(dto);
            verify(token).create("user");
        }
    }
}
