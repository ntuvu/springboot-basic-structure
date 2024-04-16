package com.tu.hellospring.repositories;

import com.tu.hellospring.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
