package com.stream.tour.domain.product.service;

import com.stream.tour.domain.product.controller.request.ProductRequest;
import com.stream.tour.domain.product.controller.response.ProductResponse;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.dto.GetProductNameResponse;
import com.stream.tour.domain.product.dto.GetProductResponse;
import com.stream.tour.domain.product.dto.GetProductsResponse;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product findById(Long productId);

    List<GetProductsResponse> getProducts();

    GetProductResponse getProduct(Long productId);

    List<Product> findByProductIds(List<Long> productIds);

    List<Product> findByPartnerId(Long partnerId);

    ProductResponse.Create createProduct(ProductRequest.Create request);

    void closeSales(Long productId);

    ProductResponse.Update update(Long productId, ProductRequest request);

    Long deepCopyProduct(Long productId) throws IOException;

    List<GetProductNameResponse> getProductNames();

    void deleteProductImages(Long partnerId, List<Long> productImageIds);
}
