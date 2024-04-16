package com.tu.hellospring.mappers;

import com.tu.hellospring.dtos.requests.RoleRequestDTO;
import com.tu.hellospring.dtos.respones.RoleResponseDTO;
import com.tu.hellospring.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequestDTO request);

    RoleResponseDTO toRoleResponseDTO(Role role);
}
