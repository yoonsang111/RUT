package com.stream.tour.domain.product.service;

import com.stream.tour.domain.product.domain.RefundDetail;

import java.util.List;

public interface RefundDetailService {
    void deleteByProduct_Id(Long productId);

    List<RefundDetail> findByProductId(Long productId);
}
