package com.card.auth.api;

import com.card.auth.dto.JwtRequest;
import com.card.auth.dto.LoginResponse;
import com.card.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Login here.",
            description = """
                    Provide a username in form of an email
                    and a password to obtain a JWT token
                    to be used for authentication
                    """)

    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Bad Credentials")
    @ApiResponse(responseCode = "403", description = "Unauthorized User")
    @PostMapping(value = "/auth/authenticate")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody JwtRequest jwtRequest){
        return new ResponseEntity<>(userService.authenticateUser(jwtRequest), HttpStatus.OK);
    }
}
