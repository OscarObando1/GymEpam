package org.oscar.gym.controller;

import jakarta.validation.Valid;
import org.oscar.gym.dtos.ChangePassDTO;
import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateListTrainerRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.dtos.response.trainee.TraineeRegistrationResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponseExtend;
import org.oscar.gym.dtos.response.trainer.TrainerResponse;
import org.oscar.gym.security.IAuthenticator;
import org.oscar.gym.service.trainee.ITraineeService;
import org.oscar.gym.service.trainee.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<TraineeRegistrationResponse> createTrainee(@Valid @RequestBody TraineeRegistrationRequest dto){
        return new ResponseEntity<>(traineeService.saveTrainee(dto), HttpStatus.CREATED);
    }

    @PutMapping("/trainee")
    public ResponseEntity<TraineeResponseExtend> updateTrainee(@Valid @RequestBody TraineeUpdateRequest dto){
       return new ResponseEntity<>(traineeService.updateTrainee(dto),HttpStatus.OK);
    }

    @PutMapping("/trainee/list-trainer")
    public ResponseEntity<List<TrainerResponse>> updateListTrainer(@RequestBody TraineeUpdateListTrainerRequest dto){
        return new ResponseEntity<>(traineeService.updateListOfTrainer(dto),HttpStatus.OK);
    }

    @DeleteMapping("/trainee")
    public ResponseEntity<?> deleteTrainee(@RequestParam String username){
        traineeService.deleteTrainee(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/trainee/update")
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
