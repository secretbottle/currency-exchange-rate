package com.secretbottle.currency.exchange.info.model;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class CurrencyExchangeInfo {

    private String from;
    private String to;
    private BigDecimal ratio;
    private BigDecimal commission;

}