package org.oscar.gym.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.oscar.gym.dtos.ChangePassDTO;
import org.oscar.gym.dtos.Login;
import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainee.TraineeRegistrationRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateListTrainerRequest;
import org.oscar.gym.dtos.request.trainee.TraineeUpdateRequest;
import org.oscar.gym.dtos.response.trainee.TraineeRegistrationResponse;
import org.oscar.gym.dtos.response.trainee.TraineeResponseExtend;
import org.oscar.gym.dtos.response.trainer.TrainerResponse;
import org.oscar.gym.exception.TraineeNotFoundException;
import org.oscar.gym.security.IAuthenticator;
import org.oscar.gym.service.trainee.ITraineeService;
import org.oscar.gym.service.trainee.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
        name = "trainee controller",
        description = "controller where you can do CRUD operation over trainee entity"
)
@RestController
public class TraineeController {

    private final ITraineeService traineeService;

    public TraineeController(ITraineeService traineeService, IAuthenticator authenticator) {
        this.traineeService = traineeService;
    }


    @Operation(
            method = "GET",
            summary = "get a trainee by the username",
            responses = {@ApiResponse(responseCode = "200",description = "get a trainee",
                    content = @Content(
                            schema = @Schema(implementation = TraineeResponseExtend.class)
                    )
            ),@ApiResponse(responseCode = "404",description = "Trainee not found",
                    content = @Content(schema = @Schema(example = "trainee not found with this username 'username' "))
            )
            }
    )
    @GetMapping("/trainee")
    public ResponseEntity<TraineeResponseExtend> getTrainee(@RequestParam String username){
        return new ResponseEntity<>(traineeService.findTrainee(username),HttpStatus.OK);
    }


    @Operation(
            method = "POST",
            summary = "create a trainee",
            responses = @ApiResponse(responseCode = "201",
                    content = @Content(
                            schema = @Schema(implementation = TraineeRegistrationResponse.class)
                    )
            )
    )
    @PostMapping("/trainee")
    public ResponseEntity<TraineeRegistrationResponse> createTrainee(@Valid @RequestBody TraineeRegistrationRequest dto){
        return new ResponseEntity<>(traineeService.saveTrainee(dto), HttpStatus.CREATED);
    }


    @Operation(
            method = "PUT",
            summary = "update a trainee",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = TraineeResponseExtend.class)
                    )
            ),@ApiResponse(responseCode = "404",description = "Trainee not found",
                    content = @Content(schema = @Schema(example = "trainee not found with this username 'username' "))
            )
            }
    )
    @PutMapping("/trainee")
    public ResponseEntity<TraineeResponseExtend> updateTrainee(@Valid @RequestBody TraineeUpdateRequest dto){
       return new ResponseEntity<>(traineeService.updateTrainee(dto),HttpStatus.OK);
    }

    @Operation(
            method = "PUT",
            summary = "update a trainee's trainer list",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = TrainerResponse.class)
                    )
            ),@ApiResponse(responseCode = "404",description = "Trainer not found",
                    content = @Content(schema = @Schema(example = "trainer not found with this username 'username' "))
            ),@ApiResponse(responseCode = "404",description = "Trainee not found",
                    content = @Content(schema = @Schema(example = "trainee not found with this username 'username' "))
            )
            }
    )
    @PutMapping("/trainee/list-trainer")
    public ResponseEntity<List<TrainerResponse>> updateListTrainer(@RequestBody TraineeUpdateListTrainerRequest dto){
        return new ResponseEntity<>(traineeService.updateListOfTrainer(dto),HttpStatus.OK);
    }


    @Operation(
            method = "DELETE",
            summary = "delete a trainee",
            responses = {@ApiResponse(responseCode = "200"
            ),@ApiResponse(responseCode = "404",description = "Trainee not found",
                    content = @Content(schema = @Schema(example = "trainee not found with this username 'username' "))
            )
            }
    )
    @DeleteMapping("/trainee")
    public ResponseEntity<?> deleteTrainee(@RequestParam String username){
        traineeService.deleteTrainee(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(
            method = "PATCH",
            summary = "active or deactivate a user",
            responses = @ApiResponse(responseCode = "200"
            )
    )
    @PatchMapping("/trainee/update")
    public ResponseEntity<?> updateActiveTrainee(@RequestBody UserActivateDeActivate dto){
        traineeService.activeOrDeactivateTraine(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(
            method = "POST",
            summary = "update pass of an user",
            responses = {@ApiResponse(responseCode = "200"
            ),@ApiResponse(responseCode = "404",description = "Trainee not found",
                    content = @Content(schema = @Schema(example = "trainee not found with this username 'username' "))
            )
            }
    )
    @PostMapping("trainee/change")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassDTO dto){
        traineeService.updatePassword(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
