package com.nishthasoft.accounts.service.client;

import com.nishthasoft.accounts.model.Customer;
import com.nishthasoft.accounts.model.Loans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient("loans")
public interface LoansFeignClient {

    @PostMapping(value = "myLoans", consumes = "application/json")
    List<Loans> getLoanDetails(@RequestHeader("nishthasoft-correlation-id") String correlationId
            ,@RequestBody Customer customer);
}
