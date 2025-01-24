package com.stream.tour.domain.naver.naver.dto;

import com.stream.tour.domain.naver.naver.enums.ChannelProductDisplayStatusType;
import com.stream.tour.domain.naver.naver.enums.ProductInfoProvidedNoticeType;
import com.stream.tour.domain.naver.naver.enums.SaleType;
import com.stream.tour.domain.naver.naver.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@AllArgsConstructor
@Getter
@Setter
public class SaveProductRequest {

    private OriginProduct originProduct;
    private SmartstoreChannelProduct smartstoreChannelProduct;
    private WindowChannelProduct windowChannelProduct;
    private DetailAttribute detailAttribute;
    private ProductInfoProvidedNotice productInfoProvidedNotice;

    public static SaveProductRequest createSaveProductRequest() {
        return SaveProductRequest.builder()
                .originProduct(createOriginProduct())
                .smartstoreChannelProduct(createSmartStoreChannelProduct())
//                .windowChannelProduct(createWindowChannelProduct())
                .build();
    }

    private static ProductInfoProvidedNotice createProductInfoProvidedNotice() {
        return ProductInfoProvidedNotice.builder()
                .productInfoProvidedNoticeType(ProductInfoProvidedNoticeType.ETC)
                .etc(ProductInfoProvidedNotice.Etc.builder()
                        .returnCostReason("0")
                        .noRefundReason("0")
                        .qualityAssuranceStandard("0")
                        .compensationProcedure("0")
                        .troubleShootingContents("0")
                        .itemName("테스트 상품")
                        .modelName("테스트 상품 모델명")
                        .manufacturer("테스트 제조사")
                        .afterServiceDirector("테스트 A/S 책임자")
                        .build())
                .build();
    }

    private static OriginProduct createOriginProduct() {
        return OriginProduct.builder()
                .statusType(StatusType.WAIT)
                .saleType(SaleType.NEW)
                .leafCategoryId("50007253") // 국내패키지/기타
                .name("테스트 상품")
                .detailContent("테스트 상품 상세 설명")
                .stockQuantity(1)
                .images(OriginProduct.Image.builder()
                        .representativeImage(OriginProduct.Image.RepresentativeImage.builder()
                                .url("http://shop1.phinf.naver.net/20240220_175/1708430695926qCf0G_PNG/1593922806245872_927794774.png")
                                .build()).build())
                .salePrice(10000)
                .detailAttribute(createDetailAttribute())
                .build();
    }

    public static DetailAttribute createDetailAttribute() {
        return DetailAttribute.builder()
                .afterServiceInfo(createAfterServiceInfo())
                .originAreaInfo(createOriginAreaInfo())
                .minorPurchasable(true)
                .productInfoProvidedNotice(createProductInfoProvidedNotice())
                .build();
    }

    private static OriginAreaInfo createOriginAreaInfo() {
        return OriginAreaInfo.builder()
                .originAreaCode("00")
                .build();
    }

    private static AfterServiceInfo createAfterServiceInfo() {
        return AfterServiceInfo.builder()
                .afterServiceTelephoneNumber("02-1234-5678")
                .afterServiceGuideContent("A/S 전화번호는 02-1234-5678 입니다.")
                .build();
    }

    private static SmartstoreChannelProduct createSmartStoreChannelProduct() {
        return SmartstoreChannelProduct.builder()
                .naverShoppingRegistration(false)
                .channelProductDisplayStatusType(ChannelProductDisplayStatusType.SUSPENSION)
                .build();
    }

    private static WindowChannelProduct createWindowChannelProduct() {
        return WindowChannelProduct.builder()
                .naverShoppingRegistration(false)
                .channelNo(500277173)
                .build();
    }

    @Builder @AllArgsConstructor @Getter @Setter
    public static class OriginProduct {
        private StatusType statusType; // required
        private SaleType saleType;
        private String leafCategoryId; // required. 상품 등록 시에는 필수입니다. 표준형 옵션 카테고리 상품 수정 요청의 경우 CategoryId 변경 요청은 무시됩니다.
        private String name; // required
        private String detailContent; // 상품 수정 시에만 생략할 수 있습니다. 이 경우 기존에 저장된 상품 상세 정보 값이 유지됩니다.
        private int stockQuantity;
        private Image images; // required. 상품 이미지로 대표 이미지(1000x1000픽셀 권장)와 최대 9개의 추가 이미지 목록을 제공할 수 있습니다. 대표 이미지는 필수이고 추가 이미지는 선택 사항입니다. 이미지 URL은 반드시 상품 이미지 다건 등록 API로 이미지를 업로드하고 반환받은 URL 값을 입력해야 합니다.
        private int salePrice; // required.
        private DetailAttribute detailAttribute; // required. 원상품 상세 속성

        @Builder @AllArgsConstructor @Getter @Setter
        public static class Image {
            private RepresentativeImage representativeImage;
            private OptionalImage[] optionalImages;

            @Builder @AllArgsConstructor @Getter @Setter
            public static class RepresentativeImage {
                private String url;
            }

            @Builder @AllArgsConstructor @Getter @Setter
            public static class OptionalImage {
                private String url;
            }
        }
    }

    // 스마트스토어 채널상품 정보 구조체
    @Builder @AllArgsConstructor @Getter @Setter
    public static class SmartstoreChannelProduct {
        private String channelProductName;
        private Integer bbsSeq;
        private boolean storeKeepExclusiveProduct;

        private boolean naverShoppingRegistration; // required. 네이버쇼핑 등록 여부. 네이버 쇼핑 광고주가 아닌 경우에는 false로 저장됩니다.

        private ChannelProductDisplayStatusType channelProductDisplayStatusType; // required. 전시 상태 코드 (스마트스토어 채널 전용). ON, SUSPENSION만 입력 가능합니다.
    }

    @Builder @AllArgsConstructor @Getter @Setter
    public static class WindowChannelProduct {
        private boolean naverShoppingRegistration; // required. 네이버쇼핑 등록 여부. 네이버 쇼핑 광고주가 아닌 경우에는 false로 저장됩니다.
        private int channelNo; // required. 윈도 채널 상품 채널번호. 전시할 윈도 채널 선택
    }

    @Builder @AllArgsConstructor @Getter @Setter
    public static class DetailAttribute {
        private NaverShoppingSearchInfo naverShoppingSearchInfo; // 네이버쇼핑 검색 정보
        private AfterServiceInfo afterServiceInfo; // required. A/S 정보
        private OriginAreaInfo originAreaInfo; // required. 원산지 정보
        private boolean minorPurchasable; // required. 미성년자 구매 가능 여부. 성인 카테고리인 경우 불가능으로 입력해야 합니다.
        private ProductInfoProvidedNotice productInfoProvidedNotice; // required. 상품 정보 제공고시
    }

    @Builder @AllArgsConstructor @Getter @Setter
    public static class NaverShoppingSearchInfo {
        private Integer modelId;
        private String modelName;
        private String manufacturerName;
        private Integer brandId;
        private String brandName;
    }

    @Builder @AllArgsConstructor @Getter @Setter
    public static class AfterServiceInfo {
        private String afterServiceTelephoneNumber;
        private String afterServiceGuideContent;
    }

    @Builder @AllArgsConstructor @Getter @Setter
    public static class PurchaseQuantityInfo {
        private Integer minPurchaseQuantity;
        private Integer maxPurchaseQuantity;
        private Integer maxPurchaseQuantityPerOrder;
    }

    @Builder @AllArgsConstructor @Getter @Setter
    public static class OriginAreaInfo {
        private String originAreaCode;
        private String importer;
        private String content;
        private String plural;
    }

    @Builder @AllArgsConstructor @Getter @Setter
    public static class ProductInfoProvidedNotice {
        private ProductInfoProvidedNoticeType productInfoProvidedNoticeType;
        private Etc etc;

        @Builder @AllArgsConstructor @Getter @Setter
        public static class Etc {
            private String returnCostReason;
            private String noRefundReason;
            private String qualityAssuranceStandard;
            private String compensationProcedure;
            private String troubleShootingContents;
            private String itemName;
            private String modelName;
            private String manufacturer;
            private String afterServiceDirector;
        }

    }
}
