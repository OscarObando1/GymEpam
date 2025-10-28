package org.oscar.gym.controller;

import org.oscar.gym.dtos.request.temp.ChangePassDTO;
import org.oscar.gym.dtos.request.temp.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.dtos.response.trainee.TraineeResponse;
import org.oscar.gym.dtos.response.trainee.TraineeRegistrationResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponseExtend;
import org.oscar.gym.security.IAuthenticator;
import org.oscar.gym.service.trainee.ITraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TraineeController {

    private final ITraineeService traineeService;

    public TraineeController(ITraineeService traineeService, IAuthenticator authenticator) {
        this.traineeService = traineeService;
    }

    @GetMapping("/trainee")
    public ResponseEntity<TraineeResponseExtend> getTrainee(@RequestParam String username){
        return new ResponseEntity<>(traineeService.findTrainee(username),HttpStatus.OK);
    }

    @PostMapping("/trainee")
    public ResponseEntity<TraineeRegistrationResponse> createTrainee(@RequestBody TraineeRegistrationRequest dto){
        return new ResponseEntity<>(traineeService.saveTrainee(dto), HttpStatus.CREATED);
    }

    @PutMapping("/trainee/{id}")
    public ResponseEntity<TraineeResponseExtend> updateTrainee(@RequestBody TraineeUpdateRequest dto, @PathVariable long id){
       return new ResponseEntity<>(traineeService.updateTrainee(dto,id),HttpStatus.OK);
    }

    @DeleteMapping("/trainee")
    public ResponseEntity<?> deleteTrainee(@RequestParam String username){
        traineeService.deleteTrainee(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/trainee/update/{id}")
    public ResponseEntity<?> updateActiveTrainee(@RequestBody UserActivateDeActivate dto){
        traineeService.activeOrDeactivateTraine(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("trainee/change")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassDTO dto){
        traineeService.updatePassword(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
