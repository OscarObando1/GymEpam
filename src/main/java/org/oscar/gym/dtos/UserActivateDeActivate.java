package org.oscar.gym.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserActivateDeActivate {
    private String username;
    private Boolean isActive;
}
