package com.example.demo.controllers;

import com.example.demo.models.Exchange;
import com.example.demo.repository.ExchangeRepository;
import com.example.demo.services.ExchangeService;
import com.example.demo.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exchanges")
public class ExchangeController {
    @Autowired
    private ExchangeService exchangeService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<Exchange>>> getUserExchanges(@RequestParam Long userId) {
        List<Exchange> exchanges = exchangeService.getExchangesForUser(userId);
        ApiResponse<List<Exchange>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "User's exchanges retrieved successfully",
                exchanges
        );
        return ResponseEntity.ok(response);
    }
}
