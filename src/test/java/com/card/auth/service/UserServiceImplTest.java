package com.card.auth.service;

import com.card.IntegrationTestBase;
import com.card.auth.dto.JwtRequest;
import com.card.auth.exception.UserNotFoundException;
import com.card.util.AppConstant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

class UserServiceImplTest extends IntegrationTestBase {

    @Test
    void given_username_a_valid_user_to_find_user_by_username_then_return_user() {
        final var user = userService.findUserByUserName(AppConstant.DEFAULT_ADMIN_USER_EMAIL_ADDRESS);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getUsername()).isEqualTo(AppConstant.DEFAULT_ADMIN_USER_EMAIL_ADDRESS);
    }

    @Test
    void given_username_of_unknown_user_to_find_user_by_username_then_throw_user_not_found_exception() {
        Assertions.assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.findUserByUserName(AppConstant.DEFAULT_TEST_USER_EMAIL_ADDRESS))
                .withMessageContaining("User Not Found");
    }

    @Test
    void given_credentials_of_valid_user_to_authenticate_then_authenticate() {
        final var loginResponse = userService.authenticateUser(new JwtRequest(AppConstant.DEFAULT_ADMIN_USER_EMAIL_ADDRESS, AppConstant.DEFAULT_ADMIN_USER_PASSWORD));
        Assertions.assertThat(loginResponse).isNotNull();
        Assertions.assertThat(loginResponse.authToken()).isNotNull();
        Assertions.assertThat(loginResponse.msg()).isEqualTo(AppConstant.LOGIN_SUCCESS_MESSAGE);
    }

    @Test
    void given_credentials_of_unknown_user_to_authenticate_then_throw_user_not_found_exception() {
        Assertions.assertThatExceptionOfType(BadCredentialsException.class)
                .isThrownBy(() -> userService.authenticateUser(new JwtRequest(AppConstant.DEFAULT_ADMIN_USER_EMAIL_ADDRESS, AppConstant.DEFAULT_TEST_USER_PASSWORD)))
                .withMessageContaining("Bad credentials");
    }
}