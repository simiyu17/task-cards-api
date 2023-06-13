package com.card;


import com.card.auth.service.UserService;
import com.card.security.JwtTokenUtil;
import com.card.task.service.CardService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@AutoConfigureMockMvc
public abstract class IntegrationTestBase {

    @LocalServerPort
    protected Integer port;
    @Autowired
    protected UserService userService;

    @Autowired
    protected CardService cardService;

    protected HttpHeaders headers;
    @Autowired
    protected TestRestTemplate testRestTemplate;
    protected static final ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    @Autowired
    protected MockMvc mvc;

    @ServiceConnection
    protected  static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.24"));
    static {
        mySQLContainer.withUrlParam("serverTimezone", "UTC")
                .withReuse(true)
                .start();
    }

    /**
     * Authentication for test classes with user passed in param
     */
    protected void setUpUser(String userName){
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+ JwtTokenUtil.createToken(userName));
    }

}
