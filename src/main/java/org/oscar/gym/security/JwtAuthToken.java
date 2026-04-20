package org.oscar.gym.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthToken extends UsernamePasswordAuthenticationToken {
    private final String jwt;

    public JwtAuthToken(UserDetails principal, String jwt, Collection<? extends GrantedAuthority> authorities) {
        super(principal, null, authorities);
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}