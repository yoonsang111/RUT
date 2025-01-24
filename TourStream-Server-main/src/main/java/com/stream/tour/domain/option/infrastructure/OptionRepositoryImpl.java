package com.stream.tour.domain.option.infrastructure;

import com.stream.tour.domain.option.domain.Option;
import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.option.service.port.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class OptionRepositoryImpl implements OptionRepository {

    private final OptionJpaRepository optionJpaRepository;

    @Override
    public List<Option> findByProductId(Long productId) {
        return optionJpaRepository.findByProduct_Id(productId).stream()
                .map(OptionEntity::toModel)
                .toList();
    }

}
