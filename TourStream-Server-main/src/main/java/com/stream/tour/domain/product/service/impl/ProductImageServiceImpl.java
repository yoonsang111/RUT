package com.stream.tour.domain.product.service.impl;

import com.stream.tour.domain.product.controller.request.ProductRequest;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.domain.ProductImage;
import com.stream.tour.domain.product.service.ProductImageService;
import com.stream.tour.domain.product.service.port.ProductImageRepository;
import com.stream.tour.global.storage.service.FileUtilsService;
import com.stream.tour.global.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final StorageService storageService;
    private final FileUtilsService fileUtilsService;

    @Transactional
    @Override
    public void deleteProductImages(Product product) {
        // 기존 파일 삭제
        product.getProductImages().forEach(productImage ->
                storageService.deleteImage(productImage.getFilePath(), productImage.getStoredName())
        );

        productImageRepository.deleteAll(product.getProductImages());
    }

    @Transactional
    @Override
    public List<ProductImage> saveAll(List<ProductImage> productImages) {
        return productImageRepository.saveAll(productImages);
    }

    public List<ProductImage> findByStoredNameIn(List<String> storedNames) {
        return productImageRepository.findByStoredNameIn(storedNames);
    }

    @Transactional
    @Override
    public void deleteAllById(List<Long> productImageIds) {
        productImageRepository.deleteAllById(productImageIds);
        log.info("productImage 총 {} 건 삭제", productImageIds.size());
    }

    @Transactional
    @Override
    public void updateRepresentative(List<ProductImage> productImages, List<ProductRequest.ProductImage> requestProductImages) {
        ProductRequest.ProductImage requestProductImage = ProductRequest.findRepresentativeImage(requestProductImages);
        String storedFilename = fileUtilsService.getStoredFileNameFrom(requestProductImage.getProductImageUrl());

        productImages.forEach(productImage -> {
            if (productImage.getStoredName().equals(storedFilename)) {
                productImage = productImage.updateRepresentative(true);
                productImageRepository.update(productImage);
            } else {
                productImage = productImage.updateRepresentative(false);
                productImageRepository.update(productImage);
            }
        });
    }

    @Override
    public List<ProductImage> findByIdIn(List<Long> productImageIds) {
        return productImageRepository.findByIdIn(productImageIds);
    }

    @Override
    public List<ProductImage> findByProductId(Long productId) {
        return productImageRepository.findByProductId(productId);
    }
}
