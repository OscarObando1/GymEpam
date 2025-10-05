package org.oscar.gym.controller;

import org.oscar.gym.dtos.TrainerDTO;
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
    public TrainerResponse getTrainer(@RequestParam String username){
        return service.findTrainer(username);
    }

    @PostMapping("/trainer")
    public void saveTrainer(@RequestBody TrainerDTO dto){
        service.saveTrainer(dto);
    }

    @PutMapping("/trainer/{id}")
    public TrainerResponse updateTrainer(@RequestBody TrainerDTO dto, @PathVariable long id){
        return service.updateTrainer(dto,id);
    }

    @DeleteMapping("/trainer")
    public void deleteTrainer(@RequestParam String username){
        service.deleteTrainer(username);
    }
}
