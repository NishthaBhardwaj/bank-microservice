package com.nishthasof.loans.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nishthasof.loans.config.LoansServiceConfig;
import com.nishthasof.loans.model.Customer;
import com.nishthasof.loans.model.Loans;
import com.nishthasof.loans.model.Properties;
import com.nishthasof.loans.repository.LoansRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@Slf4j
public class LoansController {


    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    private LoansServiceConfig config;

    @PostMapping("/myLoans")
    public List<Loans> getLoansDetails(@RequestHeader("nishthasoft-correlation-id") String correlationId,
                                       @RequestBody Customer customer){
        List<Loans> loansList = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
        return loansList;

    }

    @GetMapping("/loans/properties")
    public String getPropertiesDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        log.info(config.getMsg());
        Properties properties = new Properties(config.getMsg(),config.getBuildVersion(),
                config.getMailDetails(),config.getActiveBranches());
        return ow.writeValueAsString(properties);

    }
}
