package com.stream.tour.domain.option.infrastructure.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.option.entity.QOptionEntity;
import com.stream.tour.domain.option.infrastructure.custom.OptionCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class OptionCustomRepositoryImpl implements OptionCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<OptionEntity> findByParentOptionId(List<Long> parentOptionIds) {
        QOptionEntity option = QOptionEntity.optionEntity;
        return queryFactory.selectFrom(option)
                .where(option.parent.id.in(parentOptionIds))
                .fetch();
    }
}
