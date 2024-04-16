package com.tu.hellospring.services;

import com.tu.hellospring.dtos.requests.PermissionRequestDTO;
import com.tu.hellospring.dtos.respones.PermissionResponseDTO;
import com.tu.hellospring.mappers.PermissionMapper;
import com.tu.hellospring.repositories.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponseDTO create(PermissionRequestDTO request) {
        var permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponseDTO(permission);
    }

    public List<PermissionResponseDTO> getAll() {
        var permissions = permissionRepository.findAll();

        return permissions.stream().map(permissionMapper::toPermissionResponseDTO).toList();
    }

    public void delete(String name) {
        permissionRepository.deleteById(name);
    }
}
