package com.stream.tour.domain.product.service;

import com.stream.tour.common.utils.*;
import com.stream.tour.domain.exchangeRate.service.ExchangeRateService;
import com.stream.tour.domain.option.service.OptionService;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.domain.partner.service.PartnerService;
import com.stream.tour.domain.product.controller.request.ProductRequest;
import com.stream.tour.domain.product.dto.GetProductNameResponse;
import com.stream.tour.domain.product.facade.ProductFacade;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.global.storage.StorageProperties;
import com.stream.tour.global.storage.service.FileUtilsService;
import com.stream.tour.global.storage.service.StorageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProductEntityFacadeTest {

    @InjectMocks
    ProductFacade productFacade;
    @Mock
    ProductService productService;
    @Mock
    PartnerService partnerService;
    @Mock
    StorageService storageService;
    @Mock
    ProductImageService productImageService;
    @Mock
    RefundDetailService refundDetailService;
    @Mock
    FileUtilsService fileUtilsService;
    @Mock
    StorageProperties storageProperties;
    @Mock
    ExchangeRateService exchangeRateService;
    @Mock
    OptionService optionService;

//    @BeforeEach
//    void setup() {
//        productFacade = new ProductFacade(productService, partnerService, storageService, productImageService, refundDetailService, fileUtilsService, storageProperties, exchangeRateService, optionService);
//    }

    @DisplayName("updateProduct(): 상품을 수정한다.")
    @Test
    void testUpdateProduct() throws Exception {
        PartnerEntity partner = PartnerTestUtils.createPartner();
        ProductEntity product = ProductTestUtils.createProduct(partner, null, List.of(ProductImageTestUtils.createProductImage()));
        ReflectionTestUtils.setField(partner, "products", List.of(product));

        // request 객체 생성
        ProductRequest updateRequest = createProductRequestUpdate();

        // given
//        given(partnerService.findById(anyLong())).willReturn(partner);
//        given(productService.findById(anyLong())).willReturn(product);
//        given(productService.updateProduct(any(ProductRequest.class), any(ProductEntity.class), any(List.class))).willReturn(product.getId());

        // when
//        ProductResponse.Update update = productFacade.update(1L, 1L, updateRequest);

        // then
//        assertEquals(1L, update.getProductId());
//        then(productService).should(times(2)).findById(anyLong());
//        then(productService).should().updateProduct(any(ProductRequest.class), any(ProductEntity.class), any(List.class));
        then(refundDetailService).should(times(1)).deleteByProduct_Id(anyLong());
    }

    private ProductRequest createProductRequestUpdate() {
        ProductRequest updateRequest = new ProductRequest();

        ProductRequest.ProductImage productImage = new ProductRequest.ProductImage();
        productImage.setProductImageUrl("https://test.com");
        productImage.setIsRepresentative(true);

        updateRequest.setProductImages(List.of(productImage));

        return updateRequest;
    }

    @DisplayName("updateProduct(): 내가 등록한 상품이 아닐 경우 AccessDeniedException을 던진다.")
    @Test
    void testUpdateProductWithIllegalStateException() throws Exception {
        // given
        PartnerEntity partner = PartnerTestUtils.createPartner();
        ProductEntity product = ProductTestUtils.createProduct(partner, null, null);

        ProductRequest updateRequest = new ProductRequest();

//        given(partnerService.findById(anyLong())).willReturn(partner);
//        given(productService.findById(anyLong())).willReturn(product);

        // when
//        AccessDeniedException accessDeniedException =
//                assertThrows(AccessDeniedException.class, () -> productFacade.update(1L, 1L, updateRequest));

        // then
//        assertEquals(ErrorMessage.ACCESS_DENIED.getMessage(), accessDeniedException.getMessage());
//        then(productImageService).should(never()).deleteProductImages(any(ProductEntity.class));
    }

    @DisplayName("getProductNames(): 예약 현황에 필요한 상품명 목록을 조회한다.")
    @Test
    public void testGetProductNames() throws Exception {
        // given

        List<ProductEntity> testProducts = List.of(
                ProductTestUtils.createProduct(
                        PartnerTestUtils.createPartner(),
                        List.of(OptionTestUtils.createOption(null, List.of(ReservationTestUtils.createReservation(null, null, null, null)))),
                        null
                )
        );
//        given(productService.findByPartnerIdOrderByName(anyLong())).willReturn(testProducts);

        // when
        List<GetProductNameResponse> getProductNameResponses = productService.getProductNames();

        // then
        assertEquals(1, getProductNameResponses.size());
        getProductNameResponses.forEach(getProductNameResponse -> {
            assertEquals(testProducts.get(0).getId(), getProductNameResponse.getProductId());
            assertEquals(testProducts.get(0).getName(), getProductNameResponse.getProductName());
//            assertEquals(testProducts.get(0).getOptions().get(0).getReservations().get(0), getProductNameResponse.hasNewReservation());
        });
//        then(productService).should(times(1)).getProductNames(anyLong());
    }

}