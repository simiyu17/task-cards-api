package com.card.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {

    @Value("${online.banking.client.account.length:14}")
    private Long clientAccountLength;

    @Value("${online.banking.default.page.size:20}")
    private Long defaultPageSize;

    public Long getClientAccountLength() {
        return clientAccountLength;
    }

    public Long getDefaultPageSize() {
        return defaultPageSize;
    }
}
