package org.oscar.gym.controller;

import org.oscar.gym.dtos.request.temp.ChangePassDTO;
import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.request.RequestTrainer;
import org.oscar.gym.dtos.request.temp.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerUpdateRequest;
import org.oscar.gym.dtos.response.TrainerResponsetemp;
import org.oscar.gym.dtos.response.trainer.TrainerRegistrationResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponseExtend;
import org.oscar.gym.service.trainer.ITrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrainerController {

    private final ITrainerService service;

    public TrainerController(ITrainerService service) {
        this.service = service;
    }

    @GetMapping("/trainer")
    public ResponseEntity<TrainerResponseExtend> getTrainer(@RequestParam String username){
        return new ResponseEntity<>(service.findTrainer(username),HttpStatus.OK);
    }

    @PostMapping("/trainer")
    public ResponseEntity<TrainerRegistrationResponse> saveTrainer(@RequestBody TrainerRegistrationRequest dto){
        return new ResponseEntity<>(service.saveTrainer(dto), HttpStatus.CREATED);
    }

    @PutMapping("/trainer/{id}")
    public ResponseEntity<TrainerResponseExtend>  updateTrainer(@RequestBody TrainerUpdateRequest dto, @PathVariable long id){
        return new ResponseEntity<>(service.updateTrainer(dto,id),HttpStatus.OK);
    }

    @DeleteMapping("/trainer")
    public void deleteTrainer(@RequestBody LoginDTO dto,@RequestParam String username){
        service.deleteTrainer(dto,username);
    }

    @PostMapping("/trainer/assign")
    public void assignTrainee(@RequestBody LoginDTO dto,@RequestParam String userTrainer, @RequestParam String userTrainee){
        service.assignTrainee(dto,userTrainer,userTrainee);
    }

    @PatchMapping ("/trainer/update/{id}")
    public ResponseEntity<?> updateActiveTrainee(@RequestBody UserActivateDeActivate dto){
        service.activeOrDeactivateTrainer(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("trainer/change")
    public void changePassword(@RequestBody ChangePassDTO dto){
        service.updatePassword(dto);
    }
}
