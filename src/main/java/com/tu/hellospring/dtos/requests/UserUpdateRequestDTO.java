package com.tu.hellospring.dtos.requests;

import com.tu.hellospring.validators.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserUpdateRequestDTO {

    String username;

    @Size(min = 8, max = 20, message = "PASSWORD_INVALID")
    String password;

    String firstName;

    String lastName;

    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;

    Set<String> roles;
}
