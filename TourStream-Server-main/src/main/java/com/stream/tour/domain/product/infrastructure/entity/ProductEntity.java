package com.stream.tour.domain.product.infrastructure.entity;

import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.domain.ProductImage;
import com.stream.tour.domain.product.controller.request.ProductRequest;
import com.stream.tour.domain.product.enums.InquiryType;
import com.stream.tour.domain.product.enums.PaymentPlan;
import com.stream.tour.domain.product.enums.PickupType;
import com.stream.tour.domain.product.enums.Transportation;
import com.stream.tour.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "product")
@Entity
public class ProductEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private PartnerEntity partner;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OptionEntity> options = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private List<ProductImageEntity> productImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<RefundDetailEntity> refundDetailEntities = new ArrayList<>();

    @Comment("상품명")
    @Column(length = 50, nullable = false)
    private String name;

    @Comment("한줄 설명")
    @Column(length = 100, nullable = false)
    private String description;

    @Comment("공지 사항")
    @Column(length = 100, nullable = false)
    private String notice;

    @Comment("상품 내용")
    @Column(length = 100, nullable = false)
    private String content;

    @Comment("투어 코스")
    @Column(length = 100, nullable = false)
    private String tourCourse;

    @Comment("포함 사항")
    @Column(length = 100, nullable = false)
    private String includedContent;

    @Comment("불포함 사항")
    @Column(length = 100, nullable = false)
    private String excludedContent;

    @Comment("기타 사항")
    @Column(length = 100, nullable = false)
    private String otherContent;

    @Comment("최소 출발 인원")
    private int minDepartureNumber;

    @Comment("이동 수단")
    @Enumerated(STRING)
    private Transportation transportation;

    @Comment("픽업 유형")
    @Enumerated(STRING)
    private PickupType pickupType;

    @Comment("픽업 정보")
    @Column(length = 100, nullable = false)
    private String pickupInformation;

    @Comment("픽업 장소")
    @Column(length = 100, nullable = false)
    private String pickupLocation;

    @Comment("결제 방식")
    @Enumerated(STRING)
    private PaymentPlan paymentPlan;

    @Comment("문의 형식")
    @Enumerated(STRING)
    private InquiryType inquiryType;

    @Comment("가이드 여부")
    @Column(columnDefinition = "TINYINT", length = 1, nullable = false)
    private boolean isGuided;

    @Accessors(fluent = true)
    @Comment("마감 여부")
    @Column(columnDefinition = "TINYINT", length = 1, nullable = false)
    private boolean isClosed;

    @Accessors(fluent = true)
    @Comment("대표 상품 여부")
    @Column(columnDefinition = "TINYINT", length = 1, nullable = false)
    private boolean isRepresentative;

    @Comment("운영 시작 시간")
    private LocalTime operationStartTime;

    @Comment("운영 종료 시간")
    private LocalTime operationEndTime;

    public Product toModel() {
        return Product.builder()
                .id(id)
                .partner(partner.toModel())
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
                .isClosed(isClosed)
                .isRepresentative(isRepresentative)
                .operationStartTime(operationStartTime)
                .operationEndTime(operationEndTime)
                .build();
    }

    public static ProductEntity from(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .partner(PartnerEntity.from(product.getPartner()))
                .productImages(ProductImage.from(product.getProductImages()))
                .name(product.getName())
                .description(product.getDescription())
                .notice(product.getNotice())
                .content(product.getContent())
                .tourCourse(product.getTourCourse())
                .includedContent(product.getIncludedContent())
                .excludedContent(product.getExcludedContent())
                .otherContent(product.getOtherContent())
                .minDepartureNumber(product.getMinDepartureNumber())
                .transportation(product.getTransportation())
                .pickupType(product.getPickupType())
                .pickupInformation(product.getPickupInformation())
                .pickupLocation(product.getPickupLocation())
                .paymentPlan(product.getPaymentPlan())
                .inquiryType(product.getInquiryType())
                .isGuided(product.isGuided())
                .isClosed(product.isClosed())
                .isRepresentative(product.isRepresentative())
                .operationStartTime(product.getOperationStartTime())
                .operationEndTime(product.getOperationEndTime())
                .build();
    }


    //==생성 메서드==//
    public static ProductEntity createProduct(ProductRequest.Create req, PartnerEntity partner, List<ProductImageEntity> productImages) {
        ProductEntity product = ProductEntity.builder()
                .partner(partner)
                .refundDetailEntities(RefundDetailEntity.createRefundDetail(req.getRefundDetails()))
                .productImages(productImages)
                .name(req.getProductName())
                .description(req.getDescription())
                .notice(req.getNotice())
                .content(req.getContent())
                .tourCourse(req.getTourCourse())
                .includedContent(req.getIncludedContent())
                .excludedContent(req.getExcludedContent())
                .otherContent(req.getOtherContent())
                .minDepartureNumber(req.getMinDepartureNumber())
//                .transportation(Transportation.of(req.getTransportation()))
//                .pickupType(PickupType.of(req.getPickupType()))
                .pickupInformation(req.getPickupInformation())
                .pickupLocation(req.getPickupLocation())
//                .paymentPlan(PaymentPlan.of(req.getPaymentPlan()))
//                .inquiryType(InquiryType.of(req.getInquiryType()))
                .isGuided(req.getIsGuided())
                .operationStartTime(req.getOperationStartTime())
                .operationEndTime(req.getOperationEndTime())
                .build();

        product.partner.getProducts().add(product);
        productImages.forEach(productImage -> productImage.addProduct(product));
        product.refundDetailEntities.forEach(refundDetail -> refundDetail.addProduct(product));

        return product;
    }

    //==비즈니스 로직==//
    public void closeSales() {
        this.isClosed = true;
    }

    public void updateProduct(ProductRequest request, List<ProductImageEntity> addedProductImages) {
        this.productImages.addAll(addedProductImages);
        this.name = request.getProductName();
        this.description = request.getDescription();
        this.notice = request.getNotice();
        this.content = request.getContent();
        this.tourCourse = request.getTourCourse();
        this.includedContent = request.getIncludedContent();
        this.excludedContent = request.getExcludedContent();
        this.otherContent = request.getOtherContent();
        this.minDepartureNumber = request.getMinDepartureNumber();
        this.refundDetailEntities = RefundDetailEntity.createRefundDetail(request.getRefundDetails());
//        this.transportation = Transportation.of(request.getTransportation());
        this.pickupInformation = request.getPickupInformation();
        this.pickupLocation = request.getPickupLocation();
//        this.paymentPlan = PaymentPlan.of(request.getPaymentPlan());
//        this.inquiryType = InquiryType.of(request.getInquiryType());
        this.isGuided = request.getIsGuided();
        this.operationStartTime = request.getOperationStartTime();
        this.operationEndTime = request.getOperationEndTime();

        this.productImages.forEach(productImage -> productImage.addProduct(this));
        this.refundDetailEntities.forEach(refundDetail -> refundDetail.addProduct(this));
    }

    /**
     * 상품을 복사한다.(영속성 컨텍스트에서 detach된 상태여야 한다.)
     */
    public ProductEntity deepCopyProduct() {
        this.id = null;
        this.options.forEach(option -> option.deepCopyOption(this));
//        this.productImages.forEach(productImage -> productImage.deepCopyProductImage(this));
        this.refundDetailEntities.forEach(refundDetail -> refundDetail.deepCopyRefundDetail(this));
        return this;
    }

    public void addProductImages(List<ProductImageEntity> productImages) {
        this.productImages = productImages;
    }

    public static List<Long> getProductIdsFrom(List<ProductEntity> products) {
        return products.stream()
                .map(ProductEntity::getId)
                .toList();
    }
}
