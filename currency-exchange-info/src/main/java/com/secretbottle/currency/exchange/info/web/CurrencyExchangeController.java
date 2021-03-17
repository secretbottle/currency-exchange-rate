package com.secretbottle.currency.exchange.info.web;

import com.secretbottle.currency.exchange.info.CurrencyExchangeInfoApplication;
import com.secretbottle.currency.exchange.info.model.CurrencyExchangeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class CurrencyExchangeController {
    private final StreamBridge streamBridge;

    @Autowired
    public CurrencyExchangeController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchangeInfo currencyConverter(@PathVariable String from, @PathVariable String to) {
        CurrencyExchangeInfo currencyExchangeInfo = new CurrencyExchangeInfo();

        currencyExchangeInfo.setFrom(from);
        currencyExchangeInfo.setTo(to);

        log.info("Sending POJO " + currencyExchangeInfo.toString() + " from controller");
        streamBridge.send("request-out-0", MessageBuilder.withPayload(currencyExchangeInfo).build());
        try {
            currencyExchangeInfo = CurrencyExchangeInfoApplication.ORDERS.poll(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("", e);
        }

        return currencyExchangeInfo;
    }

}
