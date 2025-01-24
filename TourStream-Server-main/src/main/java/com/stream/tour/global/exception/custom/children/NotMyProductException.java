package com.stream.tour.global.exception.custom.children;

import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.global.exception.custom.CustomException;

import java.util.List;

public class NotMyProductException extends CustomException {
    public NotMyProductException(Partner partner, List<Product> products) {
        super("파트너가 소유한 상품이 아닙니다. 파트너 ID: " + partner.getId() + ", 상품 ID: " + products.stream().map(Product::getId).toList());
    }

    public NotMyProductException(Long partnerId, Product product) {
        super("파트너가 소유한 상품이 아닙니다. 파트너 ID: " + partnerId + ", 상품 ID: " + product.getId());
    }
}
