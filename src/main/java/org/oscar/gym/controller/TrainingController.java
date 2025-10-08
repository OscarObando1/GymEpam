package org.oscar.gym.controller;

import org.oscar.gym.dtos.TrainingDTO;
import org.oscar.gym.service.training.ITrainingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingController {

    private final ITrainingService service;

    public TrainingController(ITrainingService service) {
        this.service = service;
    }

    @PostMapping("/training")
    public void createTraining(@RequestBody TrainingDTO dto){
        System.out.println(dto);
        service.createTraining(dto);
    }
}
