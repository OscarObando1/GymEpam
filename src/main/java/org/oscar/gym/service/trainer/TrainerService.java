package org.oscar.gym.service.trainer;


import lombok.extern.slf4j.Slf4j;
import org.oscar.gym.dtos.request.temp.ChangePassDTO;
import org.oscar.gym.dtos.LoginDTO;
import org.oscar.gym.dtos.TrainerDTO;
import org.oscar.gym.dtos.request.trainer.TrainerRegistrationRequest;
import org.oscar.gym.dtos.request.trainer.TrainerUpdateRequest;
import org.oscar.gym.dtos.response.TrainerResponsetemp;
import org.oscar.gym.dtos.response.trainer.TrainerRegistrationResponse;
import org.oscar.gym.dtos.response.trainer.TrainerResponseExtend;
import org.oscar.gym.entity.Trainer;
import org.oscar.gym.repository.trainer.TrainerRepository;
import org.oscar.gym.security.IAuthenticator;
import org.oscar.gym.utils.Mapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TrainerService implements ITrainerService{
    private final TrainerRepository repository;
    private final Mapper mapper;
    private final IAuthenticator authenticator;


    public TrainerService(TrainerRepository repository, Mapper mapper, IAuthenticator authenticator) {
        this.repository = repository;
        this.mapper = mapper;
        this.authenticator = authenticator;
    }

    @Override
    public TrainerRegistrationResponse saveTrainer(TrainerRegistrationRequest dto){
        Trainer trainer= repository.saveEntity(dto);
        return mapper.mapTrainerResponse(trainer);
    }

    public TrainerResponseExtend findTrainer(String username){
         try {
                    Trainer trainer = repository.findEntity(username);
                    return mapper.mapTrainerResponseGetMethod(trainer);
                }catch (Exception e) {
                    log.info("Does not found trainer with this username "+username);
                    }
                return null;

    }
    @Override
    public TrainerResponseExtend updateTrainer(TrainerUpdateRequest dto, long id) {
        try {
            Trainer trainer = repository.updateEntity(dto, id);
            return mapper.mapTrainerResponseGetMethod(trainer);
        } catch (Exception e) {
            log.info("Trainer not found with id " + id);
        }
        return null;
    }

    @Override
    public void deleteTrainer(LoginDTO dto,String username){
        if(!authenticator.isAuthorized(dto.getUsername(), dto.getPassword())){
            throw new UnsupportedOperationException("Sorry user not authorized");
        }
        repository.deleteEntity(username);
    }

    @Override
    public void assignTrainee(LoginDTO dto,String userTrainer, String userTrainee){
        repository.assignTraineeEntity(userTrainer,userTrainee);
    }

    @Override
    public TrainerResponsetemp activeOrDeactivateTrainer(long id) {
        try {
            Trainer trainer = repository.changeActive(id);
            return mapper.mapTrainerResponsetemp(trainer);
        } catch (Exception e) {
            log.info("Trainer not found with id " + id);
        }
        return null;
    }

    @Override
    public void updatePassword(ChangePassDTO dto) {
        if(!authenticator.isAuthorized(dto.getUsername(), dto.getOldPass())){
            throw new UnsupportedOperationException("Sorry user not authorized");
        }
        repository.updatePass(dto);
    }
}
