package com.stream.tour.domain.option.infrastructure.custom;

import com.stream.tour.domain.option.entity.OptionEntity;

import java.util.List;

public interface OptionCustomRepository {
    List<OptionEntity> findByParentOptionId(List<Long> parentOptionIds);
}
