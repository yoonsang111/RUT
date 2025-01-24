package com.stream.tour.domain.product.service.port;

import com.stream.tour.domain.product.domain.ProductImage;

import java.util.List;

public interface ProductImageRepository {
    ProductImage update(ProductImage productImage);

    List<ProductImage> findByIdIn(List<Long> productImageIds);

    void deleteAll(List<ProductImage> productImages);

    List<ProductImage> saveAll(List<ProductImage> productImages);

    List<ProductImage> findByStoredNameIn(List<String> storedNames);

    void deleteAllById(List<Long> productImageIds);

    List<ProductImage> findByProductId(Long productId);
}
