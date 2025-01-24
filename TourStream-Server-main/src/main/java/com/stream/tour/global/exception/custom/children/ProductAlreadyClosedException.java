package com.stream.tour.global.exception.custom.children;

import com.stream.tour.global.exception.custom.CustomException;

public class ProductAlreadyClosedException extends CustomException {

    public ProductAlreadyClosedException(Long productId) {
        super("이미 마감된 상품입니다. 상품 ID: " + productId);
    }
}
