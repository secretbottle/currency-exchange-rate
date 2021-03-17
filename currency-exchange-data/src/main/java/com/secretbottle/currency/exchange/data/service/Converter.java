package com.secretbottle.currency.exchange.data.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class Converter {
    public static final BigDecimal COMMISSION = new BigDecimal("0.3");

    private final Map<String, BigDecimal> exchangeRates = new HashMap<>();

    {
        exchangeRates.put("USD-RUB", new BigDecimal("72.79"));
    }

    public BigDecimal exchangeRateOrNull(String from, String to) {
        String currencies = from + "-" + to;
        BigDecimal ratio = exchangeRates.get(currencies);

        if (ratio == null) {
            ratio = exchangeRates.get(to + "-" + from);
            if (ratio != null) {
                return new BigDecimal("1.0").divide(ratio,5, RoundingMode.HALF_EVEN);
            }
        }

        return exchangeRates.get(currencies);
    }
}
