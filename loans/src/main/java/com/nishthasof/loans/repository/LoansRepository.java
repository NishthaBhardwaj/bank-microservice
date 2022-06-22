package com.nishthasof.loans.repository;

import com.nishthasof.loans.model.Loans;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoansRepository extends CrudRepository<Loans, Long> {
    List<Loans> findByCustomerIdOrderByStartDtDesc(int customerID);
}
