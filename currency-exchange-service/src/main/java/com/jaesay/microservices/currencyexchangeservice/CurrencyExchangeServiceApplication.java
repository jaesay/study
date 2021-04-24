package com.jaesay.microservices.currencyexchangeservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class CurrencyExchangeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(CurrencyExchangeRepository repository) {
        return args -> {
            List<CurrencyExchange> currencyExchanges = List.of(
                    CurrencyExchange.builder()
                            .from("USD")
                            .to("KRW")
                            .conversionMultiple(BigDecimal.valueOf(10))
                            .build(),
                    CurrencyExchange.builder()
                            .from("EUR")
                            .to("KRW")
                            .conversionMultiple(BigDecimal.valueOf(20))
                            .build(),
                    CurrencyExchange.builder()
                            .from("AUD")
                            .to("KRW")
                            .conversionMultiple(BigDecimal.valueOf(30))
                            .build()
            );

            repository.saveAll(currencyExchanges);
        };
    }
}
