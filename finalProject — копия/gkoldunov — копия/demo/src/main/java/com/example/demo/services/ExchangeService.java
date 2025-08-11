package com.example.demo.services;

import com.example.demo.models.Exchange;
import com.example.demo.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    public List<Exchange> getExchangesForUser(Long userId) {
        return exchangeRepository.findByFromUserIdOrToUserId(userId, userId);
    }

    public Exchange saveExchange(Exchange exchange) {
        return exchangeRepository.save(exchange);
    }

}
