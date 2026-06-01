package com.kop.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/money")
public class MoneyController {

    @GetMapping("")
    public BigDecimal getAvailableBalance() {
        return BigDecimal.ZERO;
    }
}
