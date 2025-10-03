package org.oscar.gym.controller;

import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.service.TraineeService;
import org.oscar.gym.service.TrainerSercice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class testController {

    @Autowired
    TraineeService service;

    @PostMapping("/pro")
    public void test(@RequestBody TraineeDTO dto){
        System.out.println("¡Llegó al endpoint! " + dto);
        service.saveTrainee(dto);
    }
}
