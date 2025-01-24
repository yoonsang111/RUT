package com.stream.tour.domain.product.infrastructure;

import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.product.service.port.ProductRepository;
import com.stream.tour.global.exception.custom.children.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    @Override
    public Product findById(long productId) {
        return productJpaRepository.findById(productId).map(ProductEntity::toModel)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));
    }

    @Override
    public List<Product> findByIdIn(List<Long> productIds) {
        return productJpaRepository.findByIdIn(productIds).stream()
                .map(ProductEntity::toModel)
                .toList();
    }

    @Override
    public List<Product> findByPartnerId(long partnerId) {
        return productJpaRepository.findByPartner_Id(partnerId).stream()
                .map(ProductEntity::toModel)
                .toList();
    }

    @Override
    public Product create(Product product) {
        return productJpaRepository.save(ProductEntity.from(product)).toModel();
    }

    @Override
    public Product update(Product product) {
        return productJpaRepository.save(ProductEntity.from(product)).toModel();
    }

}
