package com.tu.hellospring.controllers;

import com.tu.hellospring.dtos.requests.AuthenticationRequestDTO;
import com.tu.hellospring.dtos.requests.IntrospectRequestDTO;
import com.tu.hellospring.dtos.respones.ApiResponseDTO;
import com.tu.hellospring.dtos.respones.AuthenticationResponseDTO;
import com.tu.hellospring.dtos.respones.IntrospectResponseDTO;
import com.tu.hellospring.services.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponseDTO<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        return ApiResponseDTO.<AuthenticationResponseDTO>builder()
                .result(authenticationService.authenticate(authenticationRequestDTO))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponseDTO<IntrospectResponseDTO> introspect(@RequestBody IntrospectRequestDTO request) {
        return ApiResponseDTO.<IntrospectResponseDTO>builder()
                .result(authenticationService.introspect(request))
                .build();
    }
}
