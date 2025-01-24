package com.stream.tour.domain.partner.infrastructure;

import com.stream.tour.common.JpaTestAudit;
import com.stream.tour.common.TestConfig;
import com.stream.tour.common.utils.PartnerTestUtils;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
@DataJpaTest
class PartnerJpaRepositoryTest {

    @Autowired
    private PartnerJpaRepository partnerJpaRepository;

    @DisplayName("save(): 파트너 엔티티를 저장하고 저장된 파트너를 반환한다.")
    @Test
    public void testSavePartner() throws Exception {
        PartnerEntity partner = PartnerTestUtils.createPartner();
        JpaTestAudit.setBaseEntity(partner);

        // when
        PartnerEntity savedPartner = partnerJpaRepository.save(partner);

        // then
        assertThat(savedPartner.getId()).isEqualTo(partner.getId());
    }
}