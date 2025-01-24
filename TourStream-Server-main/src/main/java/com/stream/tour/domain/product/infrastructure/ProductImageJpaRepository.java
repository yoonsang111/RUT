package com.stream.tour.domain.product.infrastructure;

import com.stream.tour.domain.product.infrastructure.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageJpaRepository extends JpaRepository<ProductImageEntity, Long> {

    List<ProductImageEntity> findByStoredNameIn(List<String> fileUrls);

    List<ProductImageEntity> findByIdIn(List<Long> productImageIds);

    List<ProductImageEntity> findByProduct_Id(Long productId);
}
