package com.tu.hellospring.controllers;

import com.tu.hellospring.dtos.requests.PermissionRequestDTO;
import com.tu.hellospring.dtos.requests.RoleRequestDTO;
import com.tu.hellospring.dtos.respones.ApiResponseDTO;
import com.tu.hellospring.dtos.respones.PermissionResponseDTO;
import com.tu.hellospring.dtos.respones.RoleResponseDTO;
import com.tu.hellospring.services.RoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {

    RoleService roleService;

    @PostMapping
    public ApiResponseDTO<RoleResponseDTO> create(@RequestBody @Valid RoleRequestDTO request) {
        return ApiResponseDTO.<RoleResponseDTO>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponseDTO<List<RoleResponseDTO>> getAll() {
        return ApiResponseDTO.<List<RoleResponseDTO>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{name}")
    public ApiResponseDTO<Void> getAll(@PathVariable String name) {
        roleService.delete(name);
        return ApiResponseDTO.<Void>builder().build();
    }
}
