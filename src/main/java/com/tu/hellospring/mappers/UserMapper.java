package com.tu.hellospring.mappers;

import com.tu.hellospring.dtos.requests.UserCreateRequestDTO;
import com.tu.hellospring.dtos.requests.UserUpdateRequestDTO;
import com.tu.hellospring.dtos.respones.UserResponseDTO;
import com.tu.hellospring.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreateRequestDTO request);

    void updateUser(@MappingTarget User user, UserUpdateRequestDTO request);

    UserResponseDTO toUserResponseDTO(User user);
}
