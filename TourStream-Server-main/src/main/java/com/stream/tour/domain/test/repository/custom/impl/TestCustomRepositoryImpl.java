package com.stream.tour.domain.test.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stream.tour.domain.test.repository.custom.TestCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TestCustomRepositoryImpl implements TestCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
}
