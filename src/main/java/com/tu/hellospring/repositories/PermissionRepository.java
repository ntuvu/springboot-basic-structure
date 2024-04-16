package com.tu.hellospring.repositories;

import com.tu.hellospring.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}
