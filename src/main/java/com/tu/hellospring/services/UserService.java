package com.tu.hellospring.services;

import com.tu.hellospring.dtos.requests.UserCreateRequestDTO;
import com.tu.hellospring.dtos.requests.UserUpdateRequestDTO;
import com.tu.hellospring.dtos.respones.UserResponseDTO;
import com.tu.hellospring.entities.User;
import com.tu.hellospring.exceptions.AppException;
import com.tu.hellospring.exceptions.ErrorCode;
import com.tu.hellospring.mappers.UserMapper;
import com.tu.hellospring.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponseDTO create(UserCreateRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.toUserResponseDTO(userRepository.save(user));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public UserResponseDTO getById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toUserResponseDTO(user);
    }

    public UserResponseDTO update(UserUpdateRequestDTO request, String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);
        userRepository.save(user);

        return userMapper.toUserResponseDTO(user);
    }

    public void delete(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
