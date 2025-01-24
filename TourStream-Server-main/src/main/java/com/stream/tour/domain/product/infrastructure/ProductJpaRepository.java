package com.stream.tour.domain.product.infrastructure;

import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.product.infrastructure.custom.ProductCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long>, ProductCustomRepository {
    List<ProductEntity> findByIdIn(List<Long> productIds);
    List<ProductEntity> findByPartner_Id(Long partnerId);

    List<ProductEntity> findByPartner_IdOrderByName(Long partnerId);

}
