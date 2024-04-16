package com.tu.hellospring.dtos.requests;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class PermissionRequestDTO {

    String name;

    String description;
}
