package com.stream.tour.domain.option.service;

import com.stream.tour.domain.option.domain.Option;
import com.stream.tour.domain.option.entity.OptionEntity;

import java.util.List;

public interface OptionService {
    OptionEntity saveOption(OptionEntity option);
    void saveSubOptions(List<OptionEntity> options);

    OptionEntity findById(Long optionId);

    List<OptionEntity> findByProductId(Long productId);
    List<Option> findByProductIdIn(List<Long> productIds);

    List<OptionEntity> findByParentOptionId(List<Long> parentOptionIds);

    void deleteAllByOptionIds(List<Long> optionIds);

    void deleteByProductId(Long productId);

    List<Option> findAllByIdIn(List<Long> optionIds);
}
