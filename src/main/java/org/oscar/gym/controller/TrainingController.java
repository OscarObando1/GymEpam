package org.oscar.gym.controller;

import jakarta.validation.Valid;
import org.oscar.gym.dtos.request.training.TraineeTrainingsListResquest;
import org.oscar.gym.dtos.request.training.TrainerTrainingsListRequest;
import org.oscar.gym.dtos.request.training.TrainingDTO;
import org.oscar.gym.dtos.response.TrainingResponse;
import org.oscar.gym.dtos.response.training.TraineeTrainingsListResponse;
import org.oscar.gym.dtos.response.training.TrainerTrainingsListResponse;
import org.oscar.gym.dtos.response.training.TrainingResponseAll;
import org.oscar.gym.entity.Training;
import org.oscar.gym.repository.training.TrainingRepositoryImp;
import org.oscar.gym.service.training.ITrainingService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/training/list-trainee/trainings")
    public ResponseEntity<List<TraineeTrainingsListResponse>> listTrainingTrainee(@Valid @RequestBody TraineeTrainingsListResquest dto){
        return new ResponseEntity<>(service.getTrainingListTrainee(dto),HttpStatus.OK);
    }

    @GetMapping("/training/list-trainer/trainings")
    public ResponseEntity<List<TrainerTrainingsListResponse>> listTrainingTrainer(@Valid @RequestBody TrainerTrainingsListRequest dto){
        return new ResponseEntity<>(service.getTrainingListTrainer(dto),HttpStatus.OK);
    }
}
