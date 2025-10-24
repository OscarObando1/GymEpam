package org.oscar.gym.controller;

import org.oscar.gym.dtos.request.temp.ChangePassDTO;
import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.request.RequestTrainer;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.response.TrainerResponseExtend;
import org.oscar.gym.dtos.response.trainer.TrainerRegistrationResponse;
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
    public TrainerResponseExtend getTrainer(@RequestBody LoginDTO dto, @RequestParam String username){
        return service.findTrainer(dto,username);
    }

    @PostMapping("/trainer")
    public ResponseEntity<TrainerRegistrationResponse> saveTrainer(@RequestBody TrainerRegistrationRequest dto){
        return new ResponseEntity<>(service.saveTrainer(dto), HttpStatus.CREATED);
    }

    @PutMapping("/trainer/{id}")
    public TrainerResponseExtend updateTrainer(@RequestBody RequestTrainer dto, @PathVariable long id){
        return service.updateTrainer(dto.getLoginDTO(),dto.getTrainerDTO(),id);
    }

    @DeleteMapping("/trainer")
    public void deleteTrainer(@RequestBody LoginDTO dto,@RequestParam String username){
        service.deleteTrainer(dto,username);
    }

    @PostMapping("/trainer/assign")
    public void assignTrainee(@RequestBody LoginDTO dto,@RequestParam String userTrainer, @RequestParam String userTrainee){
        service.assignTrainee(dto,userTrainer,userTrainee);
    }

    @PostMapping("/trainer/update/{id}")
    public TrainerResponseExtend updateActiveTrainee(@PathVariable long id){
        return service.activeOrDeactivateTrainer(id);
    }

    @PostMapping("trainer/change")
    public void changePassword(@RequestBody ChangePassDTO dto){
        service.updatePassword(dto);
    }
}
