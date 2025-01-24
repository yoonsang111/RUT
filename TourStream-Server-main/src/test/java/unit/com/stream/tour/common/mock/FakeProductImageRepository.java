package com.stream.tour.common.mock;

import com.stream.tour.domain.product.domain.ProductImage;
import com.stream.tour.domain.product.service.port.ProductImageRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class FakeProductImageRepository implements ProductImageRepository {

    private AtomicLong atomicLong = new AtomicLong(1L);
    private List<ProductImage> data = new CopyOnWriteArrayList<>();

    public void save(ProductImage productImage) {
        if (productImage.getId() == null || productImage.getId() == 0) {
            ProductImage newProductImage = ProductImage.builder()
                    .id(atomicLong.getAndIncrement())
                    .filePath(productImage.getFilePath())
                    .originalName(productImage.getOriginalName())
                    .storedName(productImage.getStoredName())
                    .extension(productImage.getExtension())
                    .size(productImage.getSize())
                    .isRepresentative(productImage.isRepresentative())
                    .build();
            data.add(newProductImage);
        } else {
            data.add(productImage);
        }
    }

    @Override
    public ProductImage update(ProductImage productImage) {
        return null;
    }

    @Override
    public List<ProductImage> findByIdIn(List<Long> productImageIds) {
        return null;
    }

    @Override
    public void deleteAll(List<ProductImage> productImages) {

    }

    @Override
    public List<ProductImage> saveAll(List<ProductImage> productImages) {
        return null;
    }

    @Override
    public List<ProductImage> findByStoredNameIn(List<String> storedNames) {
        return null;
    }

    @Override
    public void deleteAllById(List<Long> productImageIds) {

    }

    @Override
    public List<ProductImage> findByProductId(Long productId) {
        return data.stream()
                .filter(productImage -> Objects.equals(productImage.getProduct().getId(), productId))
                .toList();
    }
}
