package com.card.config;

import com.card.auth.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * This class will run immediately the server is up
 * to create default users if they don't exist
 */
@Component
public class ApplicationDefaultUsersCreatorRunner implements ApplicationRunner {

    private final UserService userService;

    public ApplicationDefaultUsersCreatorRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) {
        this.userService.createDefaultUsers();
    }
}
