package com.stream.tour.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "test@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
public class BaseIntegrationTest {

    @Autowired public MockMvc mockMvc;
    @Autowired public ObjectMapper objectMapper;
    @Autowired private WebApplicationContext context;
    public PartnerEntity partner;

    @Value("${jwt.test-token}")
    public String accessToken;
    public String authorizationValue;

    @BeforeEach
    public void setup() throws Exception {
        configureMockMvc();
    }

    private void configureMockMvc() {
        this.mockMvc = MockMvcBuilders
                        .webAppContextSetup(this.context)
//                        .addFilters(new CharacterEncodingFilter("UTF-8", true))
                        .alwaysDo(PrettyPrint.print())
                        .build();
    }


}