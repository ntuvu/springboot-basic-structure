package com.tu.hellospring.dtos.respones;

import com.tu.hellospring.entities.Role;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

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

    Set<RoleResponseDTO> roles;
}
