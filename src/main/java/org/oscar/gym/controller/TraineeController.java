package org.oscar.gym.controller;

import org.oscar.gym.dtos.ChangePassDTO;
import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TraineeDTO;
import org.oscar.gym.dtos.request.RequestTrainee;
import org.oscar.gym.dtos.response.TraineeResponse;
import org.oscar.gym.security.IAuthenticator;
import org.oscar.gym.service.trainee.ITraineeService;
import org.springframework.web.bind.annotation.*;

@RestController
public class TraineeController {

    private final ITraineeService traineeService;

    public TraineeController(ITraineeService traineeService, IAuthenticator authenticator) {
        this.traineeService = traineeService;
    }

    @GetMapping("/trainee")
    public  TraineeResponse getTrainee(@RequestBody LoginDTO loginDTO, @RequestParam String username){
        return traineeService.findTrainee(loginDTO,username);
    }

    @PostMapping("/trainee")
    public void createTrainee(@RequestBody TraineeDTO dto){
        traineeService.saveTrainee(dto);
    }

    @PutMapping("/trainee/{id}")
    public TraineeResponse updateTrainee(@RequestBody RequestTrainee dto, @PathVariable long id){
       return traineeService.updateTrainee(dto.getLoginDTO(),dto.getTraineeDTO(),id);
    }

    @DeleteMapping("/trainee")
    public void deleteTrainee(@RequestBody LoginDTO dto, @RequestParam String username){
        traineeService.deleteTrainee(dto,username);
    }

    @PostMapping("/trainee/update/{id}")
    public TraineeResponse updateActiveTrainee(@PathVariable long id){
        return traineeService.activeOrDeactivateTraine(id);
    }

    @PostMapping("trainee/change")
    public void changePassword(@RequestBody ChangePassDTO dto){
        traineeService.updatePassword(dto);
    }
}
