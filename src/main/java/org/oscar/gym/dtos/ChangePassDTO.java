package org.oscar.gym.dtos;

import lombok.Getter;

@Getter
public class ChangePassDTO {
    private String username;
    private String oldPass;
    private String newPass;

}
