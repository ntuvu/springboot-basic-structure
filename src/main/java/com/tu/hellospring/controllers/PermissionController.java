package com.tu.hellospring.controllers;

import com.tu.hellospring.dtos.requests.PermissionRequestDTO;
import com.tu.hellospring.dtos.respones.ApiResponseDTO;
import com.tu.hellospring.dtos.respones.PermissionResponseDTO;
import com.tu.hellospring.services.PermissionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {

    PermissionService permissionService;

    @PostMapping
    public ApiResponseDTO<PermissionResponseDTO> create(@RequestBody @Valid PermissionRequestDTO request) {
        return ApiResponseDTO.<PermissionResponseDTO>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponseDTO<List<PermissionResponseDTO>> getAll() {
        return ApiResponseDTO.<List<PermissionResponseDTO>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{name}")
    public ApiResponseDTO<Void> getAll(@PathVariable String name) {
        permissionService.delete(name);
        return ApiResponseDTO.<Void>builder().build();
    }
}
