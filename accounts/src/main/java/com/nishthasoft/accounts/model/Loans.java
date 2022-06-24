package com.nishthasoft.accounts.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Setter @Getter @ToString
public class Loans {

    private int loanNumber;
    private int customerId;
    private Date startDt;
    private String loadType;
    private int totalLoan;
    private int amountPaid;
    private int outstandingAmount;
    private String createDt;

}