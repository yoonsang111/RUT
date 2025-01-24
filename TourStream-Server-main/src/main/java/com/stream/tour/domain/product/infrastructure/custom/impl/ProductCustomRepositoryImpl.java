package com.stream.tour.domain.product.infrastructure.custom.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stream.tour.domain.product.infrastructure.custom.ProductCustomRepository;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;



@RequiredArgsConstructor
@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    public void detach(ProductEntity product) {
        entityManager.detach(product);
    }

    @Override
    public List<ProductEntity> findByPartnerIdAndProductId(List<Long> productIds) {
        return null;
//                queryFactory
//                .selectFrom(product)
//                .where(inProductId(productIds))
//                .fetch();

    }

    /**
     * partnerId가 일치하는 Product를 조회한다.
     */
    private BooleanExpression eqPartnerId(Long partnerId) {
        return null; // partnerId != null ? product.partner.id.eq(partnerId) : null;
    }

    private BooleanExpression inProductId(List<Long> productIds) {
        return null; // !productIds.isEmpty() ? product.id.in(productIds) : null;
    }
}
