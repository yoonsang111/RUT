package com.stream.tour.global.jwt;

import com.stream.tour.common.utils.PartnerTestUtils;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Transactional
@SpringBootTest
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Test
    void generateToken_WithoutExtraClaims_ShouldSuccess() {
        // given
//        PartnerEntity partner = PartnerTestUtils.createPartner();

        // when
//        String accessToken = jwtProvider.generateToken(partner);

        // then
//        assertThat(accessToken).isNotNull();
//        assertThat(jwtProvider.extractPartnerId(accessToken)).isNull();
    }

    @Test
    void generateToken_WithExtraClaims_AndUserDetails_ShouldSuccess() throws Exception {
        // given
        Map<String, Object> extraClaims = Map.of("partnerId", 1L);
//        PartnerEntity partner = PartnerTestUtils.createPartner();

        // when
//        String accessToken = jwtProvider.generateToken(extraClaims, partner);

        // then
//        assertThat(accessToken).isNotNull();
//        assertThat(jwtProvider.extractPartnerId(accessToken)).isEqualTo(1L);
//        assertThat(jwtProvider.extractClaim(accessToken, Claims::getSubject)).isEqualTo(partner.getEmail());
//        assertThat(jwtProvider.extractClaim(accessToken, Claims::getExpiration)).isBefore(Instant.now().plus(jwtExpiration, ChronoUnit.MILLIS));
    }

    @Test
    void generateRefreshToken_ShouldSuccess() {
        // given
        PartnerEntity partner = PartnerTestUtils.createPartner();

        // when
//        String refreshToken = jwtProvider.generateRefreshToken(partner);

        // test
    }

}
















