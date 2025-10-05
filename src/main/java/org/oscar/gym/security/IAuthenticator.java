package org.oscar.gym.security;

import org.springframework.stereotype.Component;

@Component
public interface IAuthenticator {

    boolean isAuthorized(String username, String password);

}
