package org.oscar.gym.controller;

import org.oscar.gym.dtos.request.training.TrainingDTO;
import org.oscar.gym.dtos.response.TrainingResponse;
import org.oscar.gym.dtos.response.training.TrainingResponseAll;
import org.oscar.gym.service.training.ITrainingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrainingController {

    private final ITrainingService service;

    public TrainingController(ITrainingService service) {
        this.service = service;
    }

    @PostMapping("/training")
    public ResponseEntity<?> createTraining(@RequestBody TrainingDTO dto){
        service.createTraining(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/training/trainee")
    public List<TrainingResponse> listTrainingTrainee(@RequestParam String username){
        return service.listTrainingOfTrainee(username);
    }

    @GetMapping("/training/trainer")
    public List<TrainingResponse> listTrainingTrainer(@RequestParam String username){
        return service.listTrainingOfTrainer(username);
    }

    @GetMapping("/training")
    public ResponseEntity<List<TrainingResponseAll>> typeTrainings(){
        return new ResponseEntity<>(service.listTypes(), HttpStatus.OK);
    }
}
