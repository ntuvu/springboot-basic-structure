package com.tu.hellospring.controllers;

import com.tu.hellospring.dtos.requests.UserCreateRequestDTO;
import com.tu.hellospring.dtos.requests.UserUpdateRequestDTO;
import com.tu.hellospring.dtos.respones.ApiResponseDTO;
import com.tu.hellospring.dtos.respones.UserResponseDTO;
import com.tu.hellospring.entities.User;
import com.tu.hellospring.services.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;


    @PostMapping
    public ApiResponseDTO<UserResponseDTO> create(@RequestBody @Valid UserCreateRequestDTO request) {
        return ApiResponseDTO.<UserResponseDTO>builder().result(userService.create(request)).build();
    }

    @GetMapping
    public List<User> getAll() {
        return  userService.getAll();
    }

    @GetMapping("/{id}")
    public ApiResponseDTO<UserResponseDTO> getById(@PathVariable String id) {
        return ApiResponseDTO.<UserResponseDTO>builder().result(userService.getById(id)).build();
    }

    @PutMapping("/{id}")
    public ApiResponseDTO<UserResponseDTO> update(@RequestBody UserUpdateRequestDTO request, @PathVariable String id) {
        return ApiResponseDTO.<UserResponseDTO>builder().result(userService.update(request, id)).build();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        userService.delete(id);
        return "User has been deleted";
    }
}
