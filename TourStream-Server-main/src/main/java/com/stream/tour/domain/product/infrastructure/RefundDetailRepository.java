package com.stream.tour.domain.product.infrastructure;

import com.stream.tour.domain.product.infrastructure.entity.RefundDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundDetailRepository extends JpaRepository<RefundDetailEntity, Long> {

    List<RefundDetailEntity> findByProduct_Id(Long productId);

    void deleteByProduct_Id(Long productId);
}
