package com.stream.tour.domain.product.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stream.tour.domain.option.dto.OptionRequest;
import com.stream.tour.domain.product.dto.SaveRefundDetailRequest;
import com.stream.tour.domain.product.enums.InquiryType;
import com.stream.tour.domain.product.enums.PaymentPlan;
import com.stream.tour.domain.product.enums.PickupType;
import com.stream.tour.domain.product.enums.Transportation;
import com.stream.tour.global.exception.custom.children.NoRepresentativeImageException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class ProductRequest {
    @Schema(name = "productName", description = "상품명", maxLength = 50, minLength = 1, example = "제주도 투어")
    @NotBlank @Size(max = 50)
    private String productName;

    @Schema(name = "productImages", description = "상품 이미지", type = "array")
    @NotEmpty @Size(max = 5, min = 1)
    private List<@Valid ProductImage> productImages;

    @Schema(name = "description", description = "한줄 설명", maxLength = 100, minLength = 1, example = "제주도 투어 입니다.")
    @NotBlank @Size(max = 500, min = 1)
    private String description;

    @Schema(name = "notice", description = "공지 사항", maxLength = 100, minLength = 1, example = "공지 사항")
    @NotBlank @Size(max = 500, min = 1)
    private String notice;

    @Schema(name = "content", description = "상품 내용", maxLength = 100, minLength = 1, example = "상품 내용")
    @NotBlank @Size(max = 500, min = 1)
    private String content;

    @Schema(name = "tourCourse", description = "투어 코스", maxLength = 100, minLength = 1, example = "투어 코스")
    @NotBlank @Size(max = 500, min = 1)
    private String tourCourse;

    @Schema(name = "includedContent", description = "포함 사항", maxLength = 100, minLength = 1, example = "포함 사항")
    @NotBlank @Size(max = 500, min = 1)
    private String includedContent;

    @Schema(name = "excludedContent", description = "불포함 사항", maxLength = 100, minLength = 1, example = "불포함 사항")
    @NotBlank @Size(max = 500, min = 1)
    private String excludedContent;

    @Schema(name = "otherContent", description = "기타 사항", maxLength = 100, minLength = 1, example = "기타 사항")
    @NotBlank @Size(max = 100, min = 1)
    private String otherContent;

    @Schema(name = "minDepartureNumber", description = "최소 출발 인원", maximum = "100", minimum = "1", example = "1",  requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(max = 100, min = 1)
    private int minDepartureNumber;

    @Schema(name = "refundDetails", description = "환불 상세", type = "array")
    @NotEmpty @Size(max = 20, min = 1)
    private List<@Valid SaveRefundDetailRequest> refundDetails;

    @Schema(name = "transportation", description = "이동 수단")
    private Transportation transportation;

    @Schema(name = "pickupType", description = "픽업 유형", requiredMode = Schema.RequiredMode.REQUIRED)
    private PickupType pickupType;

    @Schema(name = "pickupInformation", description = "픽업 정보", maxLength = 100, minLength = 1, example = "픽업 정보")
    @NotBlank @Length(max = 100, min = 1)
    private String pickupInformation;

    @Schema(name = "pickupLocation", description = "픽업 장소", maxLength = 100, minLength = 1, example = "픽업 장소")
    @NotBlank @Length(max = 100, min = 1)
    private String pickupLocation;

    @Schema(name = "paymentPlan", description = "결제 방식", requiredMode = Schema.RequiredMode.REQUIRED)
    private PaymentPlan paymentPlan;

    @Schema(name = "inquiryType", description = "문의 유형", requiredMode = Schema.RequiredMode.REQUIRED)
    private InquiryType inquiryType;

    @JsonProperty("isGuided")
    @Schema(name = "isGuided", description = "가이드 여부")
    @NotNull
    private Boolean isGuided;

    @Schema(name = "customerServiceContact", description = "고객 센터 연락처", maxLength = 50, minLength = 1, example = "010-1234-5678")
    @NotBlank @Length(max = 50, min = 1)
    private String customerServiceContact;

    @Schema(name = "operationStartTime", description = "운영 시작 시간", example = "10:30")
    @NotNull
    private LocalTime operationStartTime;

    @Schema(name = "operationEndTime", description = "운영 종료 시간", example = "20:30")
    @NotNull
    private LocalTime operationEndTime;

    @Schema(name = "emergencyContact", description = "비상 연락처", maxLength = 50, minLength = 1, example = "010-1234-5678")
    @NotBlank @Length(max = 50, min = 1)
    private String emergencyContact;

    @JsonIgnore
    public List<String> getProductImageUrls() {
        return productImages.stream()
                .map(ProductImage::getProductImageUrl)
                .toList();
    }

    public boolean hasMultiRepresentativeImages() {
        return this.productImages.stream()
                .filter(productImage -> productImage.isRepresentative)
                .count() > 1;
    }

    public boolean containsExternalFileSources(String tourStreamApiURL) {
        return !this.productImages.stream()
                .allMatch(productImage -> productImage.getProductImageUrl().startsWith(tourStreamApiURL));

    }

    public static ProductRequest.ProductImage findRepresentativeImage(List<ProductRequest.ProductImage> productImages) {
        return productImages.stream()
                .filter(ProductImage::getIsRepresentative)
                .findAny()
                .orElseThrow(NoRepresentativeImageException::new);
    }

    /**
     * SAVE DTO
     */
    @Getter @Setter
    @Schema(name = "request", description = "상품 저장 요청 데이터")
    public static class Create extends ProductRequest {
        @Schema(name = "options", description = "옵션 등록 객체")
        @NotEmpty @Size(min = 1)
        private List<@Valid OptionRequest> options;
    }


    @Getter @Setter
    public static class ProductImage {
        @NotNull
        @Schema(description = "상품 이미지 url", type = "string")
        private String productImageUrl;

        @NotNull
        @JsonProperty("isRepresentative")
        @Schema(description = "대표 이미지 여부", type = "boolean")
        private Boolean isRepresentative;
    }
}
