package com.stream.tour.domain.product.service.impl;

import com.stream.tour.domain.product.domain.RefundDetail;
import com.stream.tour.domain.product.infrastructure.RefundDetailRepository;
import com.stream.tour.domain.product.infrastructure.entity.RefundDetailEntity;
import com.stream.tour.domain.product.service.RefundDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RefundDetailServiceImpl implements RefundDetailService {

    private final RefundDetailRepository refundDetailRepository;

    @Transactional
    @Override
    public void deleteByProduct_Id(Long productId) {
        refundDetailRepository.deleteByProduct_Id(productId);
    }

    @Override
    public List<RefundDetail> findByProductId(Long productId) {
        return refundDetailRepository.findByProduct_Id(productId).stream()
                .map(RefundDetailEntity::toModel)
                .toList();
    }
}
