package org.oscar.gym.controller;

import org.oscar.gym.dtos.Login;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Login login){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
