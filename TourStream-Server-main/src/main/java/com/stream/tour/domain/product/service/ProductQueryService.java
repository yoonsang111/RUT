package com.stream.tour.domain.product.service;

import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.dto.GetProductResponse;
import com.stream.tour.domain.product.dto.GetProductsResponse;

import java.util.List;

public interface ProductQueryService {

    Product findById(Long productId);

    List<GetProductsResponse> getProducts(long partnerId);

    GetProductResponse getProduct(Long partnerId, Long productId);

    List<Product> findByProductIds(List<Long> productIds);

    List<Product> findByPartnerId(Long partnerId);

    List<Product> findByPartnerIdOrderByName(Long partnerId);
}
