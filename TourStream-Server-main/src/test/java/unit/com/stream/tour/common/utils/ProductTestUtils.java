package com.stream.tour.common.utils;

import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.product.infrastructure.entity.ProductImageEntity;
import com.stream.tour.domain.product.enums.InquiryType;
import com.stream.tour.domain.product.enums.PaymentPlan;
import com.stream.tour.domain.product.enums.PickupType;
import com.stream.tour.domain.product.enums.Transportation;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductTestUtils {

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static List<ProductEntity> createProducts(PartnerEntity partner, List<OptionEntity> options, List<ProductImageEntity> productImages) {
        List<ProductEntity> products = new ArrayList<>();
        Arrays.stream(ProductEntity.class.getDeclaredConstructors()).forEach(constructor -> {
            if (constructor.getParameterCount() == 0) {
                constructor.setAccessible(true);
                try {
                    ProductEntity product = (ProductEntity) constructor.newInstance();
                    setFields(partner, options, productImages, product);
                    products.add(product);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return products;
    }

    public static ProductEntity createProduct(PartnerEntity partner, List<OptionEntity> options, List<ProductImageEntity> productImages) {
        return Arrays.stream(ProductEntity.class.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .map(constructor -> {
                    constructor.setAccessible(true);
                    ProductEntity product = null;
                    try {
                        product = (ProductEntity) constructor.newInstance();
                        setFields(partner, options, productImages, product);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return product;
                })
                .findAny().orElseThrow();
    }

    private static void setFields(PartnerEntity partner, List<OptionEntity> options, List<ProductImageEntity> productImages, ProductEntity product) {
        if (productImages == null || productImages.isEmpty()) {
            productImages = List.of(ProductImageTestUtils.createProductImage());
        }

        ReflectionTestUtils.setField(product, "partner", partner);
        ReflectionTestUtils.setField(product, "options", options);
        ReflectionTestUtils.setField(product, "productImages", productImages);
        ReflectionTestUtils.setField(product, "name", "상품명");
        ReflectionTestUtils.setField(product, "description", "상품 설명");
        ReflectionTestUtils.setField(product, "notice", "공지");
        ReflectionTestUtils.setField(product, "content", "내용");
        ReflectionTestUtils.setField(product, "tourCourse", "상품 코스");
        ReflectionTestUtils.setField(product, "includedContent", "포함 사항");
        ReflectionTestUtils.setField(product, "excludedContent", "불포함 사항");
        ReflectionTestUtils.setField(product, "otherContent", "기타 사항");
        ReflectionTestUtils.setField(product, "minDepartureNumber", 1);
        ReflectionTestUtils.setField(product, "transportation", Transportation.CAR);
        ReflectionTestUtils.setField(product, "pickupType", PickupType.ACCOMMODATION);
        ReflectionTestUtils.setField(product, "pickupInformation", "픽업 정보");
        ReflectionTestUtils.setField(product, "pickupLocation", "픽업 장소");
        ReflectionTestUtils.setField(product, "paymentPlan", PaymentPlan.DEPOSIT_PAYMENT);
        ReflectionTestUtils.setField(product, "inquiryType", InquiryType.DIRECT);
        ReflectionTestUtils.setField(product, "isGuided", true);
        ReflectionTestUtils.setField(product, "isClosed", false);
        ReflectionTestUtils.setField(product, "isRepresentative", true);
    }

    public static Map<String, Object> createSaveRequest() {
        Map<String, Object> params = new HashMap<>();

        params.put("productName", "상품명");
        params.put("productImages", createProductImages());
        params.put("description", "상품 설명");
        params.put("notice", "공지");
        params.put("content", "내용");
        params.put("tourCourse", "상품 코스");
        params.put("includedContent", "포함 사항");
        params.put("excludedContent", "불포함 사항");
        params.put("otherContent", "기타 사항");
        params.put("minDepartureNumber", 1);
        params.put("refundDetails", createRefundDetails());
        params.put("transportation", "CAR");
        params.put("pickupType", "ACCOMMODATION");
        params.put("pickupInformation", "픽업 정보");
        params.put("pickupLocation", "픽업 장소");
        params.put("paymentPlan", "DEPOSIT_PAYMENT");
        params.put("inquiryType", "DIRECT");
        params.put("isGuided", true);
        params.put("customerServiceContact", "010-1234-1234");
        params.put("operationStartTime", "10:30");
        params.put("operationEndTime", "20:30");
        params.put("emergencyContact", "010-1234-5678");
        params.put("options", List.of(OptionTestUtils.createOptionRequestsMap()));

        return params;
    }


    public static List<Map<String, Object>> createProductImages() {
        return List.of(
                Map.of(
                        "productImageUrl", "http://localhost:8080/files/YV9yYW5kb21fcGF0aA==/ZGU5NjI5OWQtY2VhMi00MmFiLWJhNmUtYTJmODQyNjVkYTIxLmpwZw==",
                        "isRepresentative", true
                )
        );
    }

    public static List<Map<String, Object>> createRefundDetails() {
        return List.of(
                Map.of(
                        "refundPolicy", "RATE",
                        "value", 0.1,
                        "startNumber", "123",
                        "endNumber", "2"
                ),
                Map.of(
                        "refundPolicy", "RATE",
                        "value", 0.1,
                        "startNumber", "1",
                        "endNumber", "2"
                )
        );
    }
}
