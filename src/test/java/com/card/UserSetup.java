package com.card;

import com.card.auth.dto.UserDto;
import com.card.auth.service.UserService;
import com.card.security.JwtTokenUtil;
import com.card.task.service.CardService;
import com.card.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserSetup {

    @Autowired
    protected UserService userService;

    public void setUpUser(String role, HttpHeaders headers){
        final var adminUsername = AppConstant.DEFAULT_TEST_USER_EMAIL_ADDRESS;
        final var testUserDto = new UserDto("test", "test", adminUsername, AppConstant.DEFAULT_TEST_USER_PASSWORD, Set.of(role));
        userService.createUser(testUserDto);
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+ JwtTokenUtil.createToken(userService.findUserByUserName(adminUsername)));
    }
}
