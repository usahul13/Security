package com.sahul.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahul.jwt.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
 
	Optional<User> findByUsername(String username);
	boolean existsByUsername(String name);
}
