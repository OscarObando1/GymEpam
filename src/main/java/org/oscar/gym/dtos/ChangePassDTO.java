package org.oscar.gym.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePassDTO {
    private String username;
    private String oldPass;
    private String newPass;

}
