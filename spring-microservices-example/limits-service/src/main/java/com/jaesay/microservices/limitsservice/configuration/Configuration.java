package com.jaesay.microservices.limitsservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("limits-service")
public class Configuration {
    private int minimum;
    private int maximum;
}
