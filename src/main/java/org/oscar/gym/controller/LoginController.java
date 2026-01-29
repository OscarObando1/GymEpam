package org.oscar.gym.controller;

import org.oscar.gym.dtos.Login;
import org.oscar.gym.security.AuthCredentials;
import org.oscar.gym.security.JwtToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(
        name = "login controller",
        description = "login controller tha able session to use other controllers"
)
public class LoginController {

    private final JwtToken token;
    private final AuthCredentials credentials;

    public LoginController(JwtToken token, AuthCredentials credentials) {
        this.token = token;
        this.credentials = credentials;
    }

    @PostMapping("/login")
    @Operation(
            method = "POST",
            summary = "user username and pass to login",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
                    schema = @Schema(implementation = Login.class))),
            responses = {@ApiResponse(responseCode = "200",description = "user logged"),@ApiResponse(responseCode = "403",description = "user not valid")}
    )
    public ResponseEntity<?> login( @RequestBody Login dto) {
        credentials.isAuth(dto);
        return ResponseEntity.ok().body(token.create(dto.getUsername()));
    }
}
