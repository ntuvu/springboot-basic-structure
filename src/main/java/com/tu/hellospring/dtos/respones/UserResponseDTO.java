package com.tu.hellospring.dtos.respones;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    String id;

    String username;

    String firstName;

    String lastName;

    LocalDate dob;
}
