package com.tu.hellospring.dtos.requests;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequestDTO {

    @Size(min = 8, max = 20, message = "PASSWORD_INVALID")
    String password;

    String firstName;

    String lastName;

    LocalDate dob;
}
