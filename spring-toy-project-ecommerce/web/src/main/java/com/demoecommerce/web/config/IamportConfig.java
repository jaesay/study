package com.demoecommerce.web.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IamportConfig {

    @Bean
    public IamportClient iamportClient() {
        String test_api_key = "9295104363484280";
        String test_api_secret = "sRlAomzmEyVLAp5cMz0cQQdj9fkfoWAQNVrbi0xXytI9SNmEnmQdc4xmjSBHObyB7ZNq0lnzO5IHS4mg";
        return new IamportClient(test_api_key, test_api_secret);
    }

}
