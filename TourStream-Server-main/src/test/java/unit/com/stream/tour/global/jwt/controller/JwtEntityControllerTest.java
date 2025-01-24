package com.stream.tour.global.jwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.tour.common.utils.PartnerTestUtils;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.domain.partner.infrastructure.PartnerJpaRepository;
import com.stream.tour.global.jwt.JwtFactory;
import com.stream.tour.global.jwt.JwtProperties;
import com.stream.tour.global.jwt.JwtRepository;
import com.stream.tour.global.jwt.domain.GenerateAccessTokenRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static com.stream.tour.global.jwt.entity.JwtEntity.createRefreshToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class JwtEntityControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired private WebApplicationContext context;

    @Autowired private JwtProperties jwtProperties;

    @Autowired private PartnerJpaRepository partnerJpaRepository;

    @Autowired
    JwtRepository refreshTokenRepository;

    @BeforeEach
    void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @DisplayName("generateAccessToken: 새로운 액세스 토큰을 발급한다.")
    @Test
    public void generateAccessToken() throws Exception {
        // given
        PartnerEntity partner = PartnerTestUtils.createPartner();
        partnerJpaRepository.save(partner);

        String refreshToken = JwtFactory.builder()
                .claims(Map.of("partnerId", partner.getId()))
                .build()
                .generateToken(jwtProperties);

        refreshTokenRepository.save(createRefreshToken(partner.getId(), refreshToken));

        GenerateAccessTokenRequest request = new GenerateAccessTokenRequest();
        request.setRefreshToken(refreshToken);
        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(post("/access-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        resultActions
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty());
    }
}