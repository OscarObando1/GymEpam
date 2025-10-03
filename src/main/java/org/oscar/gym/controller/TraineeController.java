package org.oscar.gym.controller;

import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.dtos.response.TraineeResponse;
import org.oscar.gym.service.trainee.ITraineeService;
import org.springframework.web.bind.annotation.*;

@RestController
public class TraineeController {

    private final ITraineeService traineeService;

    public TraineeController(ITraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping("/trainee")
    public TraineeResponse getTrainee(@RequestParam String username){
        return traineeService.findTrainee(username);
    }

    @PostMapping("/trainee")
    public void createTrainee(@RequestBody TraineeDTO dto){
        traineeService.saveTrainee(dto);
    }

    @PutMapping("/trainee/{id}")
    public TraineeResponse updateTrainee(@RequestBody TraineeDTO dto, @PathVariable long id){
       return traineeService.updateTrainee(dto,id);
    }

    @DeleteMapping("/trainee")
    public void deleteTrainee(@RequestParam String username){
        traineeService.deleteTrainee(username);
    }
}
