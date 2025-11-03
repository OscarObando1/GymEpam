package org.oscar.gym.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.oscar.gym.dtos.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {



    @Operation(
            method = "POST",
            summary = "logout user",
            responses = {@ApiResponse(responseCode = "200",description = "User Logout"),@ApiResponse(responseCode = "403",description = "user not valid")}
    )
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("User Logout");
    }
}