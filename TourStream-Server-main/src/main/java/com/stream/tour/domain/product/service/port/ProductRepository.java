package com.stream.tour.domain.product.service.port;

import com.stream.tour.domain.product.domain.Product;

import java.util.List;

public interface ProductRepository {
    Product findById(long productId);
    List<Product> findByIdIn(List<Long> productIds);
    List<Product> findByPartnerId(long partnerId);
    Product create(Product product);
    Product update(Product product);
}
