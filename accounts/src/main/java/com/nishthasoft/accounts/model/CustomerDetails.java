package com.nishthasoft.accounts.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter @Getter @ToString
public class CustomerDetails {

    private Accounts accounts;
    public List<Cards> cards;
    public List<Loans> loans;
}
