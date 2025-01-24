package com.stream.tour.domain.naver.naver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class SignatureGeneratorTest {

    @Value("${api.naver.client.id}")
    private String clientId;

    @Value("${api.naver.client.secret}")
    private String clientSecret;

    @Test
    public void generateSignature() throws Exception {
        // given
        long currentTimeMillis = System.currentTimeMillis();

        // when
        String signature = SignatureGenerator.generateSignature(clientId, clientSecret, currentTimeMillis);

        // then
        System.out.println("signature = " + signature);
        assertAll(
                () -> assertThat(signature).isNotNull(),
                () -> assertThat(signature).isBase64()
        );
    }
}