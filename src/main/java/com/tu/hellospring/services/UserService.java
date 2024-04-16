package com.tu.hellospring.services;

import com.tu.hellospring.dtos.requests.UserCreateRequestDTO;
import com.tu.hellospring.dtos.requests.UserUpdateRequestDTO;
import com.tu.hellospring.dtos.respones.UserResponseDTO;
import com.tu.hellospring.entities.User;
import com.tu.hellospring.enums.Role;
import com.tu.hellospring.exceptions.AppException;
import com.tu.hellospring.exceptions.ErrorCode;
import com.tu.hellospring.mappers.UserMapper;
import com.tu.hellospring.repositories.RoleRepository;
import com.tu.hellospring.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;
    UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserResponseDTO create(UserCreateRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRoles(Set.of(Role.USER.name()));
        return userMapper.toUserResponseDTO(userRepository.save(user));
    }

    @PreAuthorize("hasAuthority('SCOPE_APPROVE_POST')")
    public List<UserResponseDTO> getAll() {
        log.info("In method getAll");

        return userRepository.findAll().stream().map(userMapper::toUserResponseDTO).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponseDTO getById(String id) {
        log.info("In method getById");

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toUserResponseDTO(user);
    }

    public UserResponseDTO update(UserUpdateRequestDTO request, String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!user.getUsername().equals(request.getUsername()) && userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        user = userRepository.save(user);
        return userMapper.toUserResponseDTO(user);
    }

    public void delete(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    public UserResponseDTO getMyInfo() {
        log.info("In method getMyInfo");

        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        var currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponseDTO(currentUser);
    }
}
