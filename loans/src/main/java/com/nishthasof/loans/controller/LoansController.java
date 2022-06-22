package com.nishthasof.loans.controller;

import com.nishthasof.loans.model.Customer;
import com.nishthasof.loans.model.Loans;
import com.nishthasof.loans.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class LoansController {


    @Autowired
    private LoansRepository loansRepository;

    @PostMapping("/myLoans")
    public List<Loans> getLoansDetails(@RequestBody Customer customer){
        List<Loans> loansList = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
        return loansList;

    }
}
