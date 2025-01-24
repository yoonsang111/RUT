package com.stream.tour.domain.product.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {
    @Schema(name = "productId", description = "등록된 상품 ID", required = true, example = "1")
    private Long productId;

    @Schema(name = "ProductResponse.Save", description = "상품 등록 응답")
    public static class Create extends ProductResponse {
        public Create(Long productId) {
            super(productId);
        }
    }

    @Schema(name = "ProductResponse.Update", description = "상품 수정 응답")
    public static class Update extends ProductResponse {
        public Update(Long productId) {
            super(productId);
        }
    }
}
