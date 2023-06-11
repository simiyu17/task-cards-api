package com.card;

import com.card.security.CurrentUserDetails;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@WithMockUser(roles = {"ADMIN", "MEMBER"})
public abstract class UnitTestBase {

    @MockBean
    protected CurrentUserDetails userDetails;
    protected static final ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
}
