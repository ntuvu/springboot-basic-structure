package com.tu.hellospring.dtos.requests;

import com.tu.hellospring.exceptions.ErrorCode;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequestDTO {

    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, max = 20, message = "PASSWORD_INVALID")
    String password;

    String firstName;

    String lastName;

    LocalDate dob;
}
