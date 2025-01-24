package com.stream.tour.domain.product.infrastructure.custom;

import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;

import java.util.List;

public interface ProductCustomRepository {
    void detach(ProductEntity product);

    List<ProductEntity> findByPartnerIdAndProductId(List<Long> productIds);
}
