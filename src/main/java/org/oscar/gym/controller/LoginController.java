package org.oscar.gym.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.oscar.gym.dtos.Login;
import org.oscar.gym.security.IAuthenticator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
        name = "login controller",
        description = "login controller tha able session to use other controllers"
)
public class LoginController {

    private final IAuthenticator authenticator;

    public LoginController(IAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    @PostMapping("/login")
    @Operation(
            method = "POST",
            summary = "user username and pass to login",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
                    schema = @Schema(implementation = Login.class))),
            responses = {@ApiResponse(responseCode = "200",description = "user logged"),@ApiResponse(responseCode = "403",description = "user not valid")}
    )
    public ResponseEntity<String> login(HttpSession session, @RequestBody Login dto) {

        boolean isAuth = authenticator.isAuthorized(dto.getUsername(), dto.getPassword());

        if (!isAuth) {
            return new ResponseEntity<>("user not valid", HttpStatus.FORBIDDEN);

        }

        session.setAttribute("usuario", dto.getUsername());

        return ResponseEntity.ok("User logged ");
    }
}
