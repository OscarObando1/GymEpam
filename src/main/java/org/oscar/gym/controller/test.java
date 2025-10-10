package org.oscar.gym.controller;

import org.oscar.gym.repository.training.TrainingRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @Autowired
    TrainingRepositoryImp repositoryImp;

    @GetMapping("/test")
    public void test(@RequestParam String username){
        System.out.println(repositoryImp.getTrainingWithTrainee(username));
    }
}
