package com.stream.tour.domain.product.domain;

import com.stream.tour.domain.option.domain.Option;
import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.product.controller.request.ProductRequest;
import com.stream.tour.domain.product.enums.InquiryType;
import com.stream.tour.domain.product.enums.PaymentPlan;
import com.stream.tour.domain.product.enums.PickupType;
import com.stream.tour.domain.product.enums.Transportation;
import com.stream.tour.global.storage.service.FileUtilsService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.net.URI;
import java.time.LocalTime;
import java.util.*;

@Builder
@AllArgsConstructor
@Getter
public class Product {
    private final Long id;
    private final Partner partner;

    @Builder.Default
    private final List<Option> options = new ArrayList<>();
    @Builder.Default
    private final List<ProductImage> productImages = new ArrayList<>();
    @Builder.Default
    private final List<RefundDetail> refundDetails = new ArrayList<>();
    private final String name; // 상품명
    private final String description; // 한줄 설명
    private final String notice; // 공지 사항
    private final String content; // 상품 내용
    private final String tourCourse; // 투어 코스
    private final String includedContent; //포함 사항
    private final String excludedContent; // 불포함 사항
    private final String otherContent; // 기타 사항
    private final int minDepartureNumber; // 최소 출발 인원
    private final Transportation transportation; // 이동 수단
    private final PickupType pickupType; // 픽업 유형
    private final String pickupInformation; // 픽업 정보
    private final String pickupLocation; // 픽업 장소
    private final PaymentPlan paymentPlan; // 결제 방식
    private final InquiryType inquiryType; // 문의 형식
    private final boolean isGuided; // 가이드 여부
    private final boolean isClosed; // 마감 여부
    private final boolean isRepresentative; // 대표 상품 여부
    private final LocalTime operationStartTime; //운영 시작 시간
    private final LocalTime operationEndTime; //운영 종료 시간

    public static Map<Long, String> makeProductIdfileUrlMap(List<Product> products, FileUtilsService fileUtilsService) {
        Map<Long, String> productIdFileUrlMap = new HashMap<>(); // key: productId, value: url
        products.forEach(product ->
                product.getProductImages().forEach(productImage -> {
                            if (productImage.isRepresentative()) {
                                URI fileURI = fileUtilsService.loadAsMvcURI(
                                        productImage.getFilePath(),
                                        productImage.getStoredName());
                                productIdFileUrlMap.put(product.getId(), fileURI.toString());
                            }
                        }
                )
        );
        return productIdFileUrlMap;
    }

    public static Product from(ProductRequest.Create request, Partner partner, List<ProductImage> productImages) {
        return Product.builder()
                .partner(partner)
                .productImages(productImages)
                .name(request.getProductName())
                .description(request.getDescription())
                .notice(request.getNotice())
                .content(request.getContent())
                .tourCourse(request.getTourCourse())
                .includedContent(request.getIncludedContent())
                .excludedContent(request.getExcludedContent())
                .otherContent(request.getOtherContent())
                .minDepartureNumber(request.getMinDepartureNumber())
                .transportation(request.getTransportation())
                .pickupType(request.getPickupType())
                .pickupInformation(request.getPickupInformation())
                .pickupLocation(request.getPickupLocation())
                .paymentPlan(request.getPaymentPlan())
                .inquiryType(request.getInquiryType())
                .isGuided(request.getIsGuided())
                .isClosed(false)
                .isRepresentative(false)
                .operationStartTime(request.getOperationStartTime())
                .operationEndTime(request.getOperationEndTime())
                .build();
    }

    public boolean isMyProduct(Long partnerId) {
        return Objects.equals(this.getPartner().getId(), partnerId);
    }

    public Product closeSales() {
        return Product.builder()
                .id(id)
                .partner(partner)
                .productImages(productImages)
                .name(name)
                .description(description)
                .notice(notice)
                .content(content)
                .tourCourse(tourCourse)
                .includedContent(includedContent)
                .excludedContent(excludedContent)
                .otherContent(otherContent)
                .minDepartureNumber(minDepartureNumber)
                .transportation(transportation)
                .pickupType(pickupType)
                .pickupInformation(pickupInformation)
                .pickupLocation(pickupLocation)
                .paymentPlan(paymentPlan)
                .inquiryType(inquiryType)
                .isGuided(isGuided)
                .isClosed(true)
                .isRepresentative(isRepresentative)
                .operationStartTime(operationStartTime)
                .operationEndTime(operationEndTime)
                .build();
    }

    /**
     * 상품을 복사하여 새로운 상품을 생성한다.
     * productImages는 실제 이미지까지 복사 되어야 한다.
     */
    public Product deepCopyProduct() {
        return Product.builder()
                .partner(partner)
                .name(name)
                .description(description)
                .notice(notice)
                .content(content)
                .tourCourse(tourCourse)
                .includedContent(includedContent)
                .excludedContent(excludedContent)
                .otherContent(otherContent)
                .minDepartureNumber(minDepartureNumber)
                .transportation(transportation)
                .pickupType(pickupType)
                .pickupInformation(pickupInformation)
                .pickupLocation(pickupLocation)
                .paymentPlan(paymentPlan)
                .inquiryType(inquiryType)
                .isGuided(isGuided)
                .isClosed(false)
                .isRepresentative(isRepresentative)
                .operationStartTime(operationStartTime)
                .operationEndTime(operationEndTime)
                .options(options.stream().map(Option::deepCopyOption).toList())
                .refundDetails(refundDetails.stream().map(RefundDetail::deepCopyRefundDetail).toList())
                .build();
    }


    public Product updateProduct(ProductRequest request, List<ProductImage> addedProductImages) {
        Product updatedProduct = Product.builder()
                .id(id)
                .partner(partner)
                .options(options)
                .productImages(productImages)
                .name(request.getProductName())
                .description(request.getDescription())
                .notice(request.getNotice())
                .content(request.getContent())
                .tourCourse(request.getTourCourse())
                .includedContent(request.getIncludedContent())
                .excludedContent(request.getExcludedContent())
                .otherContent(request.getOtherContent())
                .minDepartureNumber(request.getMinDepartureNumber())
                .refundDetails(RefundDetail.from(request.getRefundDetails()))
                .transportation(request.getTransportation())
                .pickupInformation(request.getPickupInformation())
                .pickupLocation(request.getPickupLocation())
                .paymentPlan(request.getPaymentPlan())
                .inquiryType(request.getInquiryType())
                .isGuided(request.getIsGuided())
                .operationStartTime(request.getOperationStartTime())
                .operationEndTime(request.getOperationEndTime())
                .build();

        updatedProduct.getProductImages().addAll(addedProductImages);

        return updatedProduct;
    }
}
