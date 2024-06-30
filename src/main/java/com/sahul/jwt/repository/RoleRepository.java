package com.sahul.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahul.jwt.model.ERole;
import com.sahul.jwt.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
 Optional<Role>findByName(ERole name);
}
