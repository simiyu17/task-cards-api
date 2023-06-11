package com.card.config;

import com.card.security.permission.TaskCardServiceOwnerShipInterceptor;
import com.card.task.service.CardService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    private final CardService cardService;

    public ApplicationConfig(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( new TaskCardServiceOwnerShipInterceptor(cardService) );
    }
}
