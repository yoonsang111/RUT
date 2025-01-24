package com.stream.tour.domain.option.service.impl;

import com.stream.tour.domain.option.domain.Option;
import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.option.infrastructure.OptionJpaRepository;
import com.stream.tour.domain.option.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OptionServiceImpl implements OptionService {
    private final OptionJpaRepository optionJpaRepository;

    @Transactional
    @Override
    public OptionEntity saveOption(OptionEntity option) {
        return optionJpaRepository.save(option);
    }

    @Transactional
    @Override
    public void saveSubOptions(List<OptionEntity> options) {
        optionJpaRepository.saveAll(options);
    }

    @Override
    public OptionEntity findById(Long optionId) {
        return optionJpaRepository.findById(optionId).orElseThrow();
    }

    @Override
    public List<OptionEntity> findByProductId(Long productId) {
        return optionJpaRepository.findByProduct_IdAndAndParent_Id(productId, null);
    }

    @Override
    public List<Option> findByProductIdIn(List<Long> productIds) {
        return null; // optionRepository.findByProduct_IdIn(productIds);
    }

    @Override
    public List<OptionEntity> findByParentOptionId(List<Long> parentOptionIds) {
        return optionJpaRepository.findByParentOptionId(parentOptionIds);
    }

    @Override
    public void deleteAllByOptionIds(List<Long> optionIds) {
        optionJpaRepository.deleteAllByIdIn(optionIds);
    }

    @Override
    public void deleteByProductId(Long productId) {
        optionJpaRepository.deleteByProduct_Id(productId);
    }

    @Override
    public List<Option> findAllByIdIn(List<Long> optionIds) {
        return null ; // optionRepository.findAllByIdIn(optionIds);
    }
}
