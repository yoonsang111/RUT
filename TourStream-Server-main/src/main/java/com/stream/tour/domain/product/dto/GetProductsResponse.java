package com.stream.tour.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stream.tour.domain.product.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GetProductsResponse {

    private Long productId;
    private String productName;

    @JsonProperty("isClosed")
    private boolean isClosed;
    private String productImageUrl;
    private String productImageName;

    public GetProductsResponse(Product product, Map<Long, String> productIdFileUrlMap) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.isClosed = product.isClosed();

        product.getProductImages().forEach(productImage -> {
            if (productImage.isRepresentative()) {
                this.productImageUrl = productIdFileUrlMap.get(product.getId());
                this.productImageName = productImage.getOriginalName();
            }
        });
    }

    public static List<GetProductsResponse> from(List<Product> products, Map<Long, String> productIdFileUrlMap) {
        return products.stream()
                .map(product -> new GetProductsResponse(product, productIdFileUrlMap))
                .toList();
    }
}
