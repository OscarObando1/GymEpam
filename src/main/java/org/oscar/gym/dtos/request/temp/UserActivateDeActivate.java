package org.oscar.gym.dtos.request.temp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserActivateDeActivate {
    private String username;
    private boolean isActive;
}
