package com.nishthasoft.accounts.repository;

import com.nishthasoft.accounts.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
