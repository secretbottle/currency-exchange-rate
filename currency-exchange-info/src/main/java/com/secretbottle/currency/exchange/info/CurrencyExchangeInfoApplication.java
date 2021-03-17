package com.secretbottle.currency.exchange.info;

import com.secretbottle.currency.exchange.info.model.CurrencyExchangeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@SpringBootApplication
public class CurrencyExchangeInfoApplication {
    public static final BlockingQueue<CurrencyExchangeInfo> ORDERS = new ArrayBlockingQueue<>(1, true);

    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeInfoApplication.class, args);
    }

    @Bean
    public Function<CurrencyExchangeInfo, CurrencyExchangeInfo> request() {
        return x -> {
            log.info("Send message from controller" + x.toString());
            return x;
        };
    }

    @Bean
    public Consumer<CurrencyExchangeInfo> receive() {
        return x -> {
            log.info("Receive message from responseOrder" + x.toString());
            try {
                ORDERS.put(x);
            } catch (InterruptedException e) {
                log.error("Error while putting to queue: ", e);
            }
        };
    }

}
