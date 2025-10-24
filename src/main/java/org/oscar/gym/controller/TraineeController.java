package org.oscar.gym.controller;

import org.oscar.gym.dtos.request.temp.ChangePassDTO;
import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.request.RequestTrainee;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.response.TraineeResponseExtend;
import org.oscar.gym.dtos.response.trainee.TraineeRegistrationResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponse;
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
    public ResponseEntity<TraineeResponse> getTrainee(@RequestParam String username){
        return new ResponseEntity<>(traineeService.findTrainee(username),HttpStatus.OK);
    }

    @PostMapping("/trainee")
    public ResponseEntity<TraineeRegistrationResponse> createTrainee(@RequestBody TraineeRegistrationRequest dto){
        return new ResponseEntity<>(traineeService.saveTrainee(dto), HttpStatus.CREATED);
    }

    @PutMapping("/trainee/{id}")
    public TraineeResponseExtend updateTrainee(@RequestBody RequestTrainee dto, @PathVariable long id){
       return traineeService.updateTrainee(dto.getLoginDTO(),dto.getTraineeDTO(),id);
    }

    @DeleteMapping("/trainee")
    public void deleteTrainee(@RequestBody LoginDTO dto, @RequestParam String username){
        traineeService.deleteTrainee(dto,username);
    }

    @PostMapping("/trainee/update/{id}")
    public TraineeResponseExtend updateActiveTrainee(@PathVariable long id){
        return traineeService.activeOrDeactivateTraine(id);
    }

    @PostMapping("trainee/change")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassDTO dto){
        traineeService.updatePassword(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
