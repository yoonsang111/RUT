package com.stream.tour.domain.naver.naver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NaverUrl {
      OAUTH2_TOKEN("인증 토큰 발급 요청", "/v1/oauth2/token")
    , INQUIRIES("고객 문의 내역", "/v1/pay-user/inquiries")
    , CHANNELS("계정으로 채널 정보 조회", "/v1/seller/channels")
    , CATEGORIES("전체 카테고리 조회", "/v1/categories")
    , SAVE_PRODUCT("상품 등록", "/v2/products")
    , PRODUCT_IMAGES_UPLOAD("상품 이미지 다건 등록", "/v1/product-images/upload")
    , ORIGIN_AREAS("원산지 코드 정보 전체 조회", "/v1/product-origin-areas")
    ;

    private String description;
    private String url;
}
