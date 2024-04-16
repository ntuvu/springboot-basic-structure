package com.tu.hellospring.services;

import com.tu.hellospring.dtos.requests.RoleRequestDTO;
import com.tu.hellospring.dtos.respones.RoleResponseDTO;
import com.tu.hellospring.mappers.RoleMapper;
import com.tu.hellospring.repositories.PermissionRepository;
import com.tu.hellospring.repositories.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;


    public RoleResponseDTO create(RoleRequestDTO request) {
        var role = roleMapper.toRole(request);

        var permission = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permission));
        role = roleRepository.save(role);

        return roleMapper.toRoleResponseDTO(role);
    }

    public List<RoleResponseDTO> getAll() {
        var roles = roleRepository.findAll();

        return roles.stream().map(roleMapper::toRoleResponseDTO).toList();
    }

    public void delete(String name) {
        roleRepository.deleteById(name);
    }
}
