package com.tu.hellospring.mappers;

import com.tu.hellospring.dtos.requests.PermissionRequestDTO;
import com.tu.hellospring.dtos.respones.PermissionResponseDTO;
import com.tu.hellospring.entities.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequestDTO request);

    PermissionResponseDTO toPermissionResponseDTO(Permission permission);
}
