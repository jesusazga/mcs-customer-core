package com.pe.customer.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pe.customer.domain.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	boolean existsByName(String name);
}
