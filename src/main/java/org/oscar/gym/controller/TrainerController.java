package org.oscar.gym.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.oscar.gym.dtos.ChangePassDTO;

import org.oscar.gym.dtos.UserActivateDeActivate;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerUpdateRequest;
import org.oscar.gym.dtos.response.trainer.TrainerRegistrationResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponseExtend;
import org.oscar.gym.repository.trainer.TrainerRepositoryImp;
import org.oscar.gym.service.trainer.ITrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "trainer controller",
        description = "controller where you can do CRUD operation over trainer entity"
)
@RestController
public class TrainerController {

    private final ITrainerService service;

    public TrainerController(ITrainerService service) {
        this.service = service;
    }


    @Operation(
            method = "GET",
            summary = "get a trainer",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = TrainerResponseExtend.class)
                    )
            ),@ApiResponse(responseCode = "404",description = "Trainer not found",
                    content = @Content(schema = @Schema(example = "trainer not found with this username 'username' "))
            )
            }
    )
    @GetMapping("/trainer")
    public ResponseEntity<TrainerResponseExtend> getTrainer(@RequestParam String username){
        return new ResponseEntity<>(service.findTrainer(username),HttpStatus.OK);
    }

    @Operation(
            method = "POST",
            summary = "save a trainer",
            responses = @ApiResponse(responseCode = "201",
                    content = @Content(
                            schema = @Schema(implementation = TrainerRegistrationResponse.class)
                    )
            )
    )
    @PostMapping("/trainer")
    public ResponseEntity<TrainerRegistrationResponse> saveTrainer(@Valid @RequestBody TrainerRegistrationRequest dto){
        return new ResponseEntity<>(service.saveTrainer(dto), HttpStatus.CREATED);
    }


    @Operation(
            method = "PUT",
            summary = "update a trainer",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = TrainerResponse.class)
                    )
            ),@ApiResponse(responseCode = "404",description = "Trainer not found",
                    content = @Content(schema = @Schema(example = "trainer not found with this username 'username' "))
            )
            }
    )
    @PutMapping("/trainer")
    public ResponseEntity<TrainerResponseExtend>  updateTrainer(@RequestBody TrainerUpdateRequest dto){
        return new ResponseEntity<>(service.updateTrainer(dto),HttpStatus.OK);
    }

//    @DeleteMapping("/trainer")
//    public void deleteTrainer(@RequestBody LoginDTO dto,@RequestParam String username){
//        service.deleteTrainer(dto,username);
//    }
//
//    @PostMapping("/trainer/assign")
//    public void assignTrainee(@RequestBody LoginDTO dto,@RequestParam String userTrainer, @RequestParam String userTrainee){
//        service.assignTrainee(dto,userTrainer,userTrainee);
//    }

    @Operation(
            method = "PATCH",
            summary = "active or deactivate a user",
            responses = @ApiResponse(responseCode = "200"
            )
    )
    @PatchMapping ("/trainer/update/{id}")
    public ResponseEntity<?> updateActiveTrainee(@RequestBody UserActivateDeActivate dto){
        service.activeOrDeactivateTrainer(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            method = "POST",
            summary = "update pass of an user",
            responses = {@ApiResponse(responseCode = "200"
            ),@ApiResponse(responseCode = "404",description = "Trainer not found",
                    content = @Content(schema = @Schema(example = "trainer not found with this username 'username' "))
            )
            }
    )
    @PostMapping("trainer/change")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassDTO dto){
        service.updatePassword(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(
            method = "GET",
            summary = "obtein a list of trainer without trainee with username of trainee",
            responses = {@ApiResponse(responseCode = "200"
            ),@ApiResponse(responseCode = "404",description = "Trainee not found",
                    content = @Content(schema = @Schema(example = "trainee not found with this username 'username' "))
            )
            }
    )
    @GetMapping("trainer/not-asigned")
    public ResponseEntity<List<TrainerResponse>> trainerWithouTrainee(@RequestParam String username){
        return  new ResponseEntity<>(service.trainerWithoutTrainee(username),HttpStatus.OK);
    }
}
