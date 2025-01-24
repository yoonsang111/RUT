package com.stream.tour.domain.product.infrastructure;

import com.stream.tour.domain.product.domain.ProductImage;
import com.stream.tour.domain.product.infrastructure.entity.ProductImageEntity;
import com.stream.tour.domain.product.service.port.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductImageRepositoryImpl implements ProductImageRepository {

    private final ProductImageJpaRepository productImageJpaRepository;


    @Override
    public ProductImage update(ProductImage productImage) {
        return productImageJpaRepository.save(ProductImageEntity.from(productImage)).toModel();
    }

    @Override
    public List<ProductImage> findByIdIn(List<Long> productImageIds) {
        return productImageJpaRepository.findByIdIn(productImageIds).stream()
                .map(ProductImageEntity::toModel)
                .toList();
    }

    @Override
    public void deleteAll(List<ProductImage> productImages) {
        productImageJpaRepository.deleteAll(productImages.stream()
                .map(ProductImageEntity::from)
                .toList());
    }

    @Override
    public List<ProductImage> saveAll(List<ProductImage> productImages) {
        List<ProductImageEntity> productImageEntities = productImageJpaRepository.saveAll(productImages.stream()
                .map(ProductImageEntity::from)
                .toList()
        );

        return productImageEntities.stream()
                .map(ProductImageEntity::toModel)
                .toList();
    }

    @Override
    public List<ProductImage> findByStoredNameIn(List<String> storedNames) {
        return productImageJpaRepository.findByStoredNameIn(storedNames).stream()
                .map(ProductImageEntity::toModel)
                .toList();
    }

    @Override
    public void deleteAllById(List<Long> productImageIds) {
        productImageJpaRepository.deleteAllById(productImageIds);
    }

    @Override
    public List<ProductImage> findByProductId(Long productId) {
        return productImageJpaRepository.findByProduct_Id(productId).stream()
                .map(ProductImageEntity::toModel)
                .toList();
    }
}
