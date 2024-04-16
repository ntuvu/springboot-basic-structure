package com.tu.hellospring.dtos.requests;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class RoleRequestDTO {

    String name;

    String description;

    Set<String> permissions;
}
