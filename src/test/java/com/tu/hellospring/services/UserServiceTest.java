package com.tu.hellospring.services;

import com.tu.hellospring.dtos.requests.UserCreateRequestDTO;
import com.tu.hellospring.dtos.respones.UserResponseDTO;
import com.tu.hellospring.entities.User;
import com.tu.hellospring.exceptions.AppException;
import com.tu.hellospring.exceptions.ErrorCode;
import com.tu.hellospring.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreateRequestDTO userCreateRequest;
    private UserResponseDTO userCreateResponse;
    private User user;

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

        user = User.builder()
                .id("26dba272-2406-4101-84ee-5e1fb5aac2b7")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();
    }

    @Test
    public void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.create(userCreateRequest);

        // THEN
        assertThat(response.getId()).isEqualTo("26dba272-2406-4101-84ee-5e1fb5aac2b7");
        assertThat(response.getUsername()).isEqualTo("john");
        assertThat(response.getFirstName()).isEqualTo("John");
        assertThat(response.getLastName()).isEqualTo("Doe");
    }

    @Test
    public void createUser_userExisted_fail() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.create(userCreateRequest));
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);

    }

    @Test
    @WithMockUser(username = "john")
    public void getMyInfo_valid_success() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        var response = userService.getMyInfo();

        assertThat(response.getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getId()).isEqualTo(user.getId());
    }

    @Test
    @WithMockUser(username = "john")
    public void getMyInfo_userNotFound_error() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));
        var exception = assertThrows(AppException.class, () -> userService.getMyInfo());

        assertThat(exception.getErrorCode().getCode()).isEqualTo(ErrorCode.USER_NOT_EXISTED.getCode());
    }
}
