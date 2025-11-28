package org.oscar.gym.configuration;

import org.oscar.gym.entity.Trainee;
import org.oscar.gym.entity.User;
import org.oscar.gym.repository.UserRepository;
import org.oscar.gym.service.trainee.TraineeService;
import org.oscar.gym.service.trainer.TrainerService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUser implements UserDetailsService {

    private final UserRepository repository;

    public CustomUser(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return buildUserDetails(user);

    }

    private UserDetails buildUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.getIsActive())
                .authorities(List.of())
                .build();
    }
}
