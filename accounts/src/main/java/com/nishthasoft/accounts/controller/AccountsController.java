package com.nishthasoft.accounts.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nishthasoft.accounts.config.AccountsServiceConfig;
import com.nishthasoft.accounts.model.*;
import com.nishthasoft.accounts.repository.AccountsRepository;
import com.nishthasoft.accounts.service.client.CardsFeignClient;
import com.nishthasoft.accounts.service.client.LoansFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsServiceConfig config;

    @Autowired
    private LoansFeignClient loansFeignClient;

    @Autowired
    private CardsFeignClient cardsFeignClient;
    
    @PostMapping("/myAccount")
    public Accounts getAccountDetails(@RequestBody Customer customer){

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        if(accounts !=null){
            return accounts;
        }else {
            return null;
        }
    }

    @GetMapping("/accounts/properties")
    public String getPropertiesDetails() throws JsonProcessingException{
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        log.info(config.getMsg());
        Properties properties = new Properties(config.getMsg(),config.getBuildVersion(),
                config.getMailDetails(),config.getActiveBranches());
        return ow.writeValueAsString(properties);

    }

    @PostMapping("/myCustomerDetails")/*
    @CircuitBreaker(name ="detailsForCustomerSupportApp",
    fallbackMethod = "myCustomerDetailsFallback")*/
    @Retry(name="retryForCustomerDetails", fallbackMethod = "myCustomerDetailsFallback")
    public CustomerDetails getMyCustomerDetails(@RequestHeader("nishthasoft-correlation-id") String correlationId, @RequestBody Customer customer){
        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Cards> cards = cardsFeignClient.getCardDetails(correlationId,customer);
        List<Loans> loans = loansFeignClient.getLoanDetails(correlationId,customer);
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(account);
        customerDetails.setCards(cards);
        customerDetails.setLoans(loans);
        log.info("Customer Details {} ", customerDetails);
        return customerDetails;

    }

    public CustomerDetails myCustomerDetailsFallback(@RequestHeader("nishthasoft-correlation-id") String correlationId,Customer customer, Throwable t){
        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans = loansFeignClient.getLoanDetails(correlationId,customer);
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(account);
        customerDetails.setLoans(loans);
        return customerDetails;

    }

    @GetMapping("sayHello")
    @RateLimiter(name ="sayHello", fallbackMethod = "sayHelloFallback")
    public String sayHello(){
        return "Hello, Welcome to NishthaSoft Bank";
    }

    private String sayHelloFallback(Throwable t){
        return "Hi, Welcome to NishthaSoft Bank\"";
    }


}
