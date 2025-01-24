package com.stream.tour.domain.product.service.impl;

import com.stream.tour.common.utils.PartnerTestUtils;
import com.stream.tour.common.utils.ProductTestUtils;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.domain.partner.infrastructure.PartnerJpaRepository;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class ProductEntityServiceImplTest {

    @Autowired ProductService productService;

    @Autowired
    PartnerJpaRepository partnerJpaRepository;

    @Transactional
    @Test
    public void testDetach() throws Exception {
        // given
        PartnerEntity partner = PartnerTestUtils.createPartner();
        partnerJpaRepository.save(partner);

        ProductEntity product = ProductTestUtils.createProduct(partner, null, null);
//        productService.createProduct(product);

        // when
        Long deepCopyProductId = productService.deepCopyProduct(product.getId());
        ProductEntity deepCopyProduct = null; // productService.findById(deepCopyProductId);

        // TODO: 테스트 케이스 추가
        // then
        assertAll(() -> {
            assertThat(deepCopyProductId).isEqualTo(deepCopyProduct.getId());
            assertThat(product.getName()).isEqualTo(deepCopyProduct.getName());
        });
    }
}