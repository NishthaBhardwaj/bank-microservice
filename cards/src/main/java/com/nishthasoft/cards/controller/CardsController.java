package com.nishthasoft.cards.controller;

import com.nishthasoft.cards.model.Cards;
import com.nishthasoft.cards.model.Customer;
import com.nishthasoft.cards.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardsRepository cardsRepository;

    @PostMapping("/myCards")
    public List<Cards> getCardsDetails(@RequestBody Customer customer){
        List<Cards> list = cardsRepository.findByCustomerId(customer.getCustomerId());
        return list;
    }
}
