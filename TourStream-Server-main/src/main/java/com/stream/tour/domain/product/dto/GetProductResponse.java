package com.stream.tour.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stream.tour.domain.option.dto.OptionResponse;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.domain.ProductImage;
import com.stream.tour.domain.product.domain.RefundDetail;
import com.stream.tour.domain.product.enums.InquiryType;
import com.stream.tour.domain.product.enums.PaymentPlan;
import com.stream.tour.domain.product.enums.RefundPolicy;
import com.stream.tour.domain.product.enums.Transportation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GetProductResponse {

    @Schema(description = "상품 ID", type = "number")
    private Long productId;

    @Schema(description = "상품명", type = "string")
    private String productName;

    @Schema(description = "상품 이미지 정보", type = "array")
    private List<ProductImageDto> productImages;

    @Schema(description = "한줄 설명", type = "string")
    private String description;

    @Schema(description = "공지사항", type = "string")
    private String notice;

    @Schema(description = "상품 내용", type = "string")
    private String content;

    @Schema(description = "투어 코스", type = "string")
    private String tourCourse;

    @Schema(description = "포함 내용", type = "string")
    private String includedContent;

    @Schema(description = "불포함 내용", type = "string")
    private String excludedContent;

    @Schema(description = "기타 내용", type = "string")
    private String otherContent;

    @Schema(description = "최소 출발 인원", type = "number")
    private int minDepartureNumber;

    @Schema(description = "환불 상세 정보", type = "array")
    private List<RefundDetailDto> refundDetails;

    @Schema(description = "이동 수단", type = "string")
    private Transportation transportation;

    @Schema(description = "픽업 정보", type = "string")
    private String pickupInformation;

    @Schema(description = "픽업 장소", type = "string")
    private String pickupLocation;

    @Schema(description = "결제 방식", type = "string")
    private PaymentPlan paymentPlan;

    @Schema(description = "문의 유형", type = "string")
    private InquiryType inquiryType;

    @JsonProperty("isGuided")
    @Schema(description = "가이드 여부", type = "boolean")
    private boolean isGuided;

    @Schema(description = "고객 센터 연락처", type = "string")
    private String customerServiceContact;

    @Schema(description = "운영 시작 시간", type = "Date")
    private LocalTime operationStartTime;

    @Schema(description = "운영 종료 시간", type = "Date")
    private LocalTime operationEndTime;

    @Schema(description = "비상 연락망", type = "string")
    private String emergencyContact;

    @Schema(description = "옵션 목록", type = "array")
    private List<@Valid  OptionResponse> options;


    @Builder @Getter @Setter
    public static class ProductImageDto {
        @Schema(description = "상품 이미지 ID", type = "number")
        private Long productImageId;

        @Schema(description = "상품 이미지 url", type = "string")
        private String productImageUrl;

        @Schema(description = "상품 이미지 명", type = "string")
        private String productImageName;

        @JsonProperty("isRepresentative")
        @Schema(description = "대표 이미지 여부", type = "boolean")
        private boolean isRepresentative;

        public static List<ProductImageDto> entityToDto(List<ProductImage> productImages, Map<Long, String> productImageIdFileUrlMap) {
            return productImages.stream()
                    .map(productImage -> ProductImageDto.builder()
                            .productImageId(productImage.getId())
                            .productImageUrl(productImageIdFileUrlMap.get(productImage.getId()))
                            .productImageName(productImage.getOriginalName())
                            .isRepresentative(productImage.isRepresentative())
                            .build()
                    )
                    .toList();
        }
    }

    @Getter
    @Setter
    public static class RefundDetailDto {
        @Schema(description = "환불 상세 ID", type = "number")
        private Long refundDetailId;

        @Schema(description = "환불 정책", type = "string")
        private RefundPolicy refundPolicy;

        @Schema(description = "환불 정책 값", type = "number")
        private BigDecimal value;

        @Schema(description = "시작", type = "number")
        private Integer startNumber;

        @Schema(description = "종료", type = "number")
        private Integer endNumber;

        public static List<RefundDetailDto> entityToDto(List<RefundDetail> refundDetails) {

            return refundDetails.stream()
                    .map(refundDetail -> {
                        RefundDetailDto dto = new RefundDetailDto();
                        dto.setRefundDetailId(refundDetail.id());
                        dto.setRefundPolicy(refundDetail.refundPolicy());
                        dto.setValue(refundDetail.amount() != null ? refundDetail.amount() : refundDetail.rate());
                        dto.setStartNumber(refundDetail.startNumber());
                        dto.setEndNumber(refundDetail.endNumber());
                        return dto;
                    })
                    .toList();
        }
    }

    public GetProductResponse(Product product, Map<Long, String> productImageIdFileUrlMap, List<OptionResponse> options) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.productImages = ProductImageDto.entityToDto(product.getProductImages(), productImageIdFileUrlMap);
        this.description = product.getDescription();
        this.notice = product.getNotice();
        this.content = product.getContent();
        this.tourCourse = product.getTourCourse();
        this.includedContent = product.getIncludedContent();
        this.excludedContent = product.getExcludedContent();
        this.otherContent = product.getOtherContent();
        this.minDepartureNumber = product.getMinDepartureNumber();
        this.refundDetails = RefundDetailDto.entityToDto(product.getRefundDetails());
        this.transportation = product.getTransportation();
        this.pickupInformation = product.getPickupInformation();
        this.pickupLocation = product.getPickupLocation();
        this.paymentPlan = product.getPaymentPlan();
        this.inquiryType = product.getInquiryType();
        this.isGuided = product.isGuided();
        this.customerServiceContact = product.getPartner().getCustomerServiceContact();
        this.operationStartTime = product.getOperationStartTime();
        this.operationEndTime = product.getOperationEndTime();
        this.emergencyContact = product.getPartner().getEmergencyContact();
        this.options = options;
    }

    public static GetProductResponse from(Product product, Map<Long, String> productImageIdFileUrlMap, List<OptionResponse> options) {
        return new GetProductResponse(product, productImageIdFileUrlMap, options);
    }
}
