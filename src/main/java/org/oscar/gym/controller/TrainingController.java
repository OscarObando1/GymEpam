package org.oscar.gym.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.oscar.gym.dtos.request.training.TraineeTrainingsListResquest;
import org.oscar.gym.dtos.request.training.TrainerTrainingsListRequest;
import org.oscar.gym.dtos.request.training.TrainingDTO;
import org.oscar.gym.dtos.response.TrainingResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponseExtend;
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

import java.time.LocalDate;
import java.util.List;

@Tag(
        name = "training controller",
        description = "controller where you can do CRUD operation over training entity"
)
@RestController
public class TrainingController {

    private final ITrainingService service;

    public TrainingController(ITrainingService service) {
        this.service = service;
    }

    @Operation(
            method = "POST",
            summary = "create a training",
            responses = {@ApiResponse(responseCode = "201",description = "create a training"
            ),@ApiResponse(responseCode = "404",description = "Trainee not found",
                    content = @Content(schema = @Schema(example = "trainee not found with this username 'username' "))
            ), @ApiResponse(responseCode = "404",description = "Trainer not found",
                            content = @Content(schema = @Schema(example = "trainer not found with this username 'username' "))
            )
            }
    )
    @PostMapping("/training")
    public ResponseEntity<?> createTraining(@RequestBody TrainingDTO dto){
        service.createTraining(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(
            method = "GET",
            summary = "get a list of  types training",
            responses = @ApiResponse(responseCode = "200"
            )
    )
    @GetMapping("/training")
    public ResponseEntity<List<TrainingResponseAll>> typeTrainings(){
        return new ResponseEntity<>(service.listTypes(), HttpStatus.OK);
    }


    @Operation(
            method = "GET",
            summary = "get a list trainee's training",
            responses = {@ApiResponse(responseCode = "200"
            ),@ApiResponse(responseCode = "404",description = "Trainee not found",
                    content = @Content(schema = @Schema(example = "trainee not found with this username 'username' "))
            )
            }
    )
    @GetMapping("/training/list-trainee/trainings")
    public ResponseEntity<List<TraineeTrainingsListResponse>> listTrainingTrainee(
           @RequestParam(required = true) String username, @RequestParam(required = false) LocalDate dateFrom,
           @RequestParam(required = false) LocalDate dateTo,
           @RequestParam(required = false) String trainerName
    ){
        TraineeTrainingsListResquest dto = new TraineeTrainingsListResquest(username,dateFrom,dateTo,trainerName);
        return new ResponseEntity<>(service.getTrainingListTrainee(dto),HttpStatus.OK);
    }

    @Operation(
            method = "GET",
            summary = "get a list trainer's training",
            responses = {@ApiResponse(responseCode = "200"
            ),@ApiResponse(responseCode = "404",description = "Trainer not found",
                    content = @Content(schema = @Schema(example = "trainer not found with this username 'username' "))
            )
            }
    )
    @GetMapping("/training/list-trainer/trainings")
    public ResponseEntity<List<TrainerTrainingsListResponse>> listTrainingTrainer(
            @RequestParam(required = true) String username, @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo,
            @RequestParam(required = false) String traineeName
    ){
        TrainerTrainingsListRequest dto = new TrainerTrainingsListRequest(username, dateFrom, dateTo, traineeName);
        return new ResponseEntity<>(service.getTrainingListTrainer(dto),HttpStatus.OK);
    }
}
