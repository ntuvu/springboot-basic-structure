package com.tu.hellospring.dtos.requests;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenRequestDTO {

    String token;
}
