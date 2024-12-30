package com.pe.customer.infrastructure.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pe.customer.infrastructure.security.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String name);
}
