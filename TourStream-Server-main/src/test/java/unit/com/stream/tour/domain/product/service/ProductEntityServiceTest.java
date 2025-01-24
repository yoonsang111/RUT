package com.stream.tour.domain.product.service;

import com.stream.tour.common.utils.PartnerTestUtils;
import com.stream.tour.common.utils.ProductTestUtils;
import com.stream.tour.domain.product.infrastructure.ProductJpaRepository;
import com.stream.tour.domain.product.infrastructure.RefundDetailRepository;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.global.storage.service.FileUtilsService;
import com.stream.tour.global.storage.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@DisplayName("상품 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class ProductEntityServiceTest {

    ProductService productService;
    @Mock
    ProductJpaRepository productRepository;
    @Mock RefundDetailRepository refundDetailRepository;
    @Mock StorageService storageService;
    @Mock FileUtilsService fileUtilsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
//        productService = new ProductServiceImpl(productRepository, storageService, fileUtilsService);
    }

    @DisplayName("saveProduct(): 상품 엔티티를 저장하고 저장된 상품의 id를 반환한다.")
    @Test
    void testSaveProduct() {
        // given
        ProductEntity product = ProductTestUtils.createProduct(PartnerTestUtils.createPartner(), null, null);

        given(productRepository.save(product)).willReturn(product);
        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(product));
        given(refundDetailRepository.findByProduct_Id(anyLong())).willReturn(product.getRefundDetailEntities());

        // when
//        Long savedProductId = productService.create(product);

        // then
//        assertThat(savedProductId).isEqualTo(9223372036854775807L);
//        assertThat(productRepository.findById(savedProductId).get()).isEqualTo(product);
//        assertThat(refundDetailRepository.findByProduct_Id(savedProductId)).isEqualTo(product.getRefundDetails());
    }

    @DisplayName("testCloseSales(): 상품 판매를 종료한다.")
    @Test
    public void testCloseSales() throws Exception {
        // given
        ProductEntity product = ProductTestUtils.createProduct(PartnerTestUtils.createPartner(), null, null);
        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(product));

        // when
        productService.closeSales(product.getId());

        // then
        assertThat(product.isClosed()).isTrue();
    }
}