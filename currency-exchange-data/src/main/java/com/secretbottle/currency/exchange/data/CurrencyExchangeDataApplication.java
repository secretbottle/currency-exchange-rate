package com.secretbottle.currency.exchange.data;

import com.secretbottle.currency.exchange.data.service.Converter;
import com.secretbottle.currency.exchange.info.model.CurrencyExchangeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.function.Function;

@Slf4j
@SpringBootApplication()
public class CurrencyExchangeDataApplication {

    private final Converter converter;

    @Autowired
    CurrencyExchangeDataApplication (Converter converter){
        this. converter = converter;
    }

    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeDataApplication.class, args);
    }

    @Bean
    public Function<CurrencyExchangeInfo, CurrencyExchangeInfo> receiveAndReply() {
        return x -> {
            x.setCommission(Converter.COMMISSION);

            BigDecimal ratio = converter.exchangeRateOrNull(x.getFrom().toUpperCase(), x.getTo().toUpperCase());
            x.setRatio(ratio);

            log.info("Exchange-Data: Send answer POJO to Exchange-Info" + x.toString());
            return x;
        };
    }

}
