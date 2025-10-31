package org.oscar.gym.controller;

import jakarta.servlet.http.HttpSession;
import org.oscar.gym.dtos.Login;
import org.oscar.gym.security.IAuthenticator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final IAuthenticator authenticator;

    public LoginController(IAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpSession session, @RequestBody Login dto) {

        boolean isAuth = authenticator.isAuthorized(dto.getUsername(), dto.getPassword());

        if (!isAuth) {
            return new ResponseEntity<>("user not valid", HttpStatus.FORBIDDEN);

        }

        session.setAttribute("usuario", dto.getUsername());

        return ResponseEntity.ok("User logged: ");
    }
}
