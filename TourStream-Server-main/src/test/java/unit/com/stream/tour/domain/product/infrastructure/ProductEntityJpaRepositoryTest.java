package com.stream.tour.domain.product.infrastructure;

import com.stream.tour.common.JpaTestAudit;
import com.stream.tour.common.TestConfig;
import com.stream.tour.common.utils.PartnerTestUtils;
import com.stream.tour.common.utils.ProductImageTestUtils;
import com.stream.tour.common.utils.ProductTestUtils;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.domain.partner.infrastructure.PartnerJpaRepository;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.product.infrastructure.entity.ProductImageEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
@DataJpaTest
class ProductEntityJpaRepositoryTest {

    @Autowired
    private ProductJpaRepository productRepository;
    @Autowired
    private PartnerJpaRepository partnerJpaRepository;

    @Test
    void testSave() throws Exception {
        // given
        // partner
        PartnerEntity partner = PartnerTestUtils.createPartner();
        JpaTestAudit.setBaseEntity(partner);
        partnerJpaRepository.save(partner);

        // productImage
        ProductImageEntity productImage = ProductImageTestUtils.createProductImage();
        JpaTestAudit.setBaseEntity(productImage);

        // product
        ProductEntity product = ProductTestUtils.createProduct(partner, null, List.of(productImage));
        JpaTestAudit.setBaseEntity(product);

        // when
        ProductEntity savedProduct = productRepository.save(product);

        // then
        assertThat(savedProduct.getId()).isEqualTo(product.getId());

    }

}
