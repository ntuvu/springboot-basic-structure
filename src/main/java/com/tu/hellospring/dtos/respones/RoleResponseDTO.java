package com.tu.hellospring.dtos.respones;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
public class RoleResponseDTO {

    String name;

    String description;

    Set<PermissionResponseDTO> permissions;
}
