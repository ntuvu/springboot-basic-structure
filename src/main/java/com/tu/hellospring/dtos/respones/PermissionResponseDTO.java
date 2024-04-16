package com.tu.hellospring.dtos.respones;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
public class PermissionResponseDTO {

    String name;

    String description;
}
