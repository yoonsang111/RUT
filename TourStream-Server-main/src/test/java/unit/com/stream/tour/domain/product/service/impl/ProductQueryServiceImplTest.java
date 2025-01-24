package com.stream.tour.domain.product.service.impl;

import com.stream.tour.common.mock.FakePartnerRepository;
import com.stream.tour.common.mock.FakeProductImageRepository;
import com.stream.tour.common.mock.FakeProductRepository;
import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.domain.ProductImage;
import com.stream.tour.domain.product.dto.GetProductResponse;
import com.stream.tour.domain.product.dto.GetProductsResponse;
import com.stream.tour.domain.product.enums.InquiryType;
import com.stream.tour.domain.product.enums.PaymentPlan;
import com.stream.tour.domain.product.enums.PickupType;
import com.stream.tour.domain.product.enums.Transportation;
import com.stream.tour.global.storage.StorageProperties;
import com.stream.tour.global.storage.service.FileUtilsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductQueryServiceImplTest {

    private ProductQueryServiceImpl productQueryService;
    private Base64.Encoder encoder = Base64.getEncoder();

    @BeforeEach
    void init() {
        StorageProperties storageProperties = new StorageProperties();
        StorageProperties.Location location = new StorageProperties.Location();
        location.setProduct("/productResponse");
        storageProperties.setLocation(location);

        FileUtilsService fileUtilsService = FileUtilsService.builder()
                .storageProperties(storageProperties)
                .host("http://localhost:8080")
                .build();

        FakeProductRepository fakeProductRepository = new FakeProductRepository();
        FakePartnerRepository fakePartnerRepository = new FakePartnerRepository();
        FakeProductImageRepository fakeProductImageRepository = new FakeProductImageRepository();
        productQueryService = ProductQueryServiceImpl.builder()
                .fileUtilsService(fileUtilsService)
                .productRepository(fakeProductRepository)
                .partnerRepository(fakePartnerRepository)
                .productImageRepository(fakeProductImageRepository)
                .build();

        Partner partner = Partner.builder()
                .id(1L)
                .corporateRegistrationNumber("1234567890")
                .name("홍길동")
                .phoneNumber("010-1234-5678")
                .email("honggd@naver.com")
                .password("12341234")
                .passwordChanged(true)
                .customerServiceContact("010-1234-5678")
                .operationStartTime(LocalTime.of(9, 0))
                .operationEndTime(LocalTime.of(18, 0))
                .emergencyContact("010-1234-5678")
                .build();
        fakePartnerRepository.save(partner);

        Product product = Product.builder()
                .id(1L)
                .partner(partner)
                .name("제주도 여행")
                .description("제주도 여행 상품입니다.")
                .notice("공지사항 입니다")
                .content("상품 내용 입니다.")
                .tourCourse("투어 코스 입니다.")
                .includedContent("포함 사항입니다")
                .excludedContent("불포함 사항입니다")
                .otherContent("기타 사항 입니다.")
                .minDepartureNumber(1)
                .transportation(Transportation.CAR)
                .pickupType(PickupType.ACCOMMODATION)
                .pickupInformation("픽업 정보 입니다.")
                .pickupLocation("픽업 장소 입니다.")
                .paymentPlan(PaymentPlan.DEPOSIT_PAYMENT)
                .inquiryType(InquiryType.DIRECT)
                .isGuided(true)
                .isClosed(false)
                .isRepresentative(true)
                .operationStartTime(LocalTime.of(9, 0))
                .operationEndTime(LocalTime.of(18, 0))
                .build();

        fakeProductRepository.create(product);

        ProductImage productImage = ProductImage.builder()
                .id(1L)
                .product(product)
                .originalName("제주도 호텔 이미지.jpg")
                .filePath("productResponse/2024/3/18")
                .storedName("2007fbf9-a6fb-48fd-8b7c-c4df2b0c9be3.jpg")
                .extension("jpg")
                .size(1000L)
                .isRepresentative(true)
                .build();

        fakeProductImageRepository.save(productImage);
    }

    @Test
    void getProducts로_상품_목록을_조회할_수_있다() {
        // given
        // when
        List<GetProductsResponse> productsResponse = productQueryService.getProducts(1L);

        // then
        GetProductsResponse getProductsResponse = productsResponse.get(0);
        assertAll(
                () -> assertThat(productsResponse).hasSize(1),
                () -> assertThat(productsResponse).isNotNull(),
                () -> assertThat(getProductsResponse.getProductId()).isEqualTo(1L),
                () -> assertThat(getProductsResponse.getProductName()).isEqualTo("제주도 여행"),
                () -> assertThat(getProductsResponse.isClosed()).isEqualTo(false),
                () -> assertThat(getProductsResponse.getProductImageUrl()).isEqualTo("http://localhost:8080/files/" + encoder.encodeToString("productResponse/2024/3/18".getBytes()) + "/" + encoder.encodeToString("2007fbf9-a6fb-48fd-8b7c-c4df2b0c9be3.jpg".getBytes())),
                () -> assertThat(getProductsResponse.getProductImageName()).isEqualTo("제주도 호텔 이미지.jpg")
        );
    }

    @Test
    void getProduct로_상품_하나를_조회할_수_있다() {
        // given
        // when
        GetProductResponse productResponse = productQueryService.getProduct(1L, 1L);

        // then
        assertAll(
                () -> assertThat(productResponse).isNotNull(),
                () -> assertThat(productResponse.getProductId()).isEqualTo(1L),
                () -> assertThat(productResponse.getProductName()).isEqualTo("제주도 여행")
        );
    }

    @Test
    void findByParnterId로_파트너의_상품을_조회할_수_있다() {
        // given
        // when
        List<Product> products = productQueryService.findByPartnerId(1L);

        // then
        Product product = products.get(0);
        ProductImage productImage = product.getProductImages().get(0);
        Partner partner = product.getPartner();
        assertAll(
                // productResponse
                () -> assertThat(products).hasSize(1),
                () -> assertThat(products).isNotNull(),
                () -> assertThat(product.getId()).isEqualTo(1L),
                () -> assertThat(product.getName()).isEqualTo("제주도 여행"),
                () -> assertThat(product.isClosed()).isEqualTo(false),
                () -> assertThat(product.getDescription()).isEqualTo("제주도 여행 상품입니다."),
                () -> assertThat(product.getNotice()).isEqualTo("공지사항 입니다"),
                () -> assertThat(product.getContent()).isEqualTo("상품 내용 입니다."),
                () -> assertThat(product.getTourCourse()).isEqualTo("투어 코스 입니다."),
                () -> assertThat(product.getIncludedContent()).isEqualTo("포함 사항입니다"),
                () -> assertThat(product.getExcludedContent()).isEqualTo("불포함 사항입니다"),
                () -> assertThat(product.getOtherContent()).isEqualTo("기타 사항 입니다."),
                () -> assertThat(product.getMinDepartureNumber()).isEqualTo(1),
                () -> assertThat(product.getTransportation()).isEqualTo(Transportation.CAR),
                () -> assertThat(product.getPickupType()).isEqualTo(PickupType.ACCOMMODATION),
                () -> assertThat(product.getPickupInformation()).isEqualTo("픽업 정보 입니다."),
                () -> assertThat(product.getPickupLocation()).isEqualTo("픽업 장소 입니다."),
                () -> assertThat(product.getPaymentPlan()).isEqualTo(PaymentPlan.DEPOSIT_PAYMENT),
                () -> assertThat(product.getInquiryType()).isEqualTo(InquiryType.DIRECT),
                () -> assertThat(product.isGuided()).isTrue(),
                () -> assertThat(product.isClosed()).isFalse(),
                () -> assertThat(product.isRepresentative()).isTrue(),
                () -> assertThat(product.getOperationStartTime()).isEqualTo(LocalTime.of(9, 0)),
                () -> assertThat(product.getOperationEndTime()).isEqualTo(LocalTime.of(18, 0)),

                // productImages
                () -> assertThat(productImage.getId()).isEqualTo(1L),
                () -> assertThat(productImage.getOriginalName()).isEqualTo("제주도 호텔 이미지.jpg"),
                () -> assertThat(productImage.getFilePath()).isEqualTo("productResponse/2024/3/18"),
                () -> assertThat(productImage.getStoredName()).isEqualTo("2007fbf9-a6fb-48fd-8b7c-c4df2b0c9be3.jpg"),
                () -> assertThat(productImage.getExtension()).isEqualTo("jpg"),
                () -> assertThat(productImage.getSize()).isEqualTo(1000L),
                () -> assertThat(productImage.isRepresentative()).isTrue(),

                // partner
                () -> assertThat(partner.getId()).isEqualTo(1L),
                () -> assertThat(partner.getCorporateRegistrationNumber()).isEqualTo("1234567890"),
                () -> assertThat(partner.getName()).isEqualTo("홍길동"),
                () -> assertThat(partner.getPhoneNumber()).isEqualTo("010-1234-5678"),
                () -> assertThat(partner.getEmail()).isEqualTo("honggd@naver.com"),
                () -> assertThat(partner.getPassword()).isEqualTo("12341234"),
                () -> assertThat(partner.isPasswordChanged()).isTrue(),
                () -> assertThat(partner.getCustomerServiceContact()).isEqualTo("010-1234-5678"),
                () -> assertThat(partner.getOperationStartTime()).isEqualTo(LocalTime.of(9, 0)),
                () -> assertThat(partner.getOperationEndTime()).isEqualTo(LocalTime.of(18, 0)),
                () -> assertThat(partner.getEmergencyContact()).isEqualTo("010-1234-5678")
        );
    }
}