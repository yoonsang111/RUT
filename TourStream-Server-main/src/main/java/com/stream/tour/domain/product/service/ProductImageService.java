package com.stream.tour.domain.product.service;

import com.stream.tour.domain.product.controller.request.ProductRequest;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.domain.ProductImage;

import java.util.List;

public interface ProductImageService {
    void deleteProductImages(Product product);

    List<ProductImage> saveAll(List<ProductImage> productImages);

    List<ProductImage> findByStoredNameIn(List<String> storedNames);

    void deleteAllById(List<Long> productImageIds);

    void updateRepresentative(List<ProductImage> productImages, List<ProductRequest.ProductImage> requestProductImages);

    List<ProductImage> findByIdIn(List<Long> productImageIds);
    List<ProductImage> findByProductId(Long productId);
}
