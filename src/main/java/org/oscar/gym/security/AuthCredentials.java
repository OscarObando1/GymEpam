package org.oscar.gym.security;

import org.oscar.gym.dtos.Login;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthCredentials {

    private final AuthenticationManager authenticationManager;

    public AuthCredentials(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public boolean isAuth(Login dto){
        UsernamePasswordAuthenticationToken token1 = new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(token1);
        return authentication.isAuthenticated();
    }




}
