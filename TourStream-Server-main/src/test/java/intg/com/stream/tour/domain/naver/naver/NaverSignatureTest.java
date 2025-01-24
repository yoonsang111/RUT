package com.stream.tour.domain.naver.naver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NaverSignatureTest {

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
        Assertions.assertThat(signature).isNotNull();

    }
}
