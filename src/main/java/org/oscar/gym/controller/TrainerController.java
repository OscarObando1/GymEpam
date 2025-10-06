package org.oscar.gym.controller;

import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TrainerDTO;
import org.oscar.gym.dtos.request.RequestTrainer;
import org.oscar.gym.dtos.response.TraineeResponse;
import org.oscar.gym.dtos.response.TrainerResponse;
import org.oscar.gym.service.trainer.ITrainerService;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrainerController {

    private final ITrainerService service;

    public TrainerController(ITrainerService service) {
        this.service = service;
    }

    @GetMapping("/trainer")
    public TrainerResponse getTrainer(@RequestBody LoginDTO dto,@RequestParam String username){
        return service.findTrainer(dto,username);
    }

    @PostMapping("/trainer")
    public void saveTrainer(@RequestBody TrainerDTO dto){
        service.saveTrainer(dto);
    }

    @PutMapping("/trainer/{id}")
    public TrainerResponse updateTrainer(@RequestBody RequestTrainer dto, @PathVariable long id){
        return service.updateTrainer(dto.getLoginDTO(),dto.getTrainerDTO(),id);
    }

    @DeleteMapping("/trainer")
    public void deleteTrainer(@RequestBody LoginDTO dto,@RequestParam String username){
        service.deleteTrainer(dto,username);
    }

    @PostMapping("/trainer/update/{id}")
    public TrainerResponse updateActiveTrainee(@PathVariable long id){
        return service.activeOrDeactivateTrainer(id);
    }
}
