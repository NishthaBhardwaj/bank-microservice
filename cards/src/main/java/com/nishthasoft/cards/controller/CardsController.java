package com.nishthasoft.cards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nishthasoft.cards.config.CardsServiceConfig;
import com.nishthasoft.cards.model.Cards;
import com.nishthasoft.cards.model.Customer;
import com.nishthasoft.cards.model.Properties;
import com.nishthasoft.cards.repository.CardsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CardsController {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CardsServiceConfig config;

    @PostMapping("/myCards")
    public List<Cards> getCardsDetails(@RequestHeader("nishthasoft-correlation-id") String correlationId,
                                       @RequestBody Customer customer){
        List<Cards> list = cardsRepository.findByCustomerId(customer.getCustomerId());
        return list;
    }

    @GetMapping("/cards/properties")
    public String getPropertiesDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        log.info(config.getMsg());
        Properties properties = new Properties(config.getMsg(),config.getBuildVersion(),
                config.getMailDetails(),config.getActiveBranches());
        return ow.writeValueAsString(properties);

    }
}
