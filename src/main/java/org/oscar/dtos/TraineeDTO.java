package org.oscar.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TraineeDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;

    public TraineeDTO() {
    }

    public TraineeDTO(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

}
