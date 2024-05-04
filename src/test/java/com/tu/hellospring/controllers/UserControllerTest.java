package com.tu.hellospring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tu.hellospring.dtos.requests.UserCreateRequestDTO;
import com.tu.hellospring.dtos.respones.UserResponseDTO;
import com.tu.hellospring.services.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserCreateRequestDTO userCreateRequest;
    private UserResponseDTO userCreateResponse;

    @BeforeEach
    public void initData() {
        LocalDate dob = LocalDate.of(1990, 1, 1);
        userCreateRequest = UserCreateRequestDTO.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .password("123456789")
                .dob(dob)
                .build();

        userCreateResponse = UserResponseDTO.builder()
                .id("26dba272-2406-4101-84ee-5e1fb5aac2b7")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();
    }

    @Test
    @SneakyThrows
    void create_validRequest_success() {
        // GIVEN
        var content = objectMapper.writeValueAsString(userCreateRequest);

        // Disable service
        Mockito.when(userService.create(ArgumentMatchers.any())).thenReturn(userCreateResponse);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("26dba272-2406-4101-84ee-5e1fb5aac2b7"));
    }

    @Test
    @SneakyThrows
    void create_usernameInvalid_fail() {
        // GIVEN
        userCreateRequest.setUsername("jo");
        var content = objectMapper.writeValueAsString(userCreateRequest);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                // Then
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1003"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 3 characters"));
    }
}
