package com.stream.tour.domain.option.infrastructure;

import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.option.infrastructure.custom.OptionCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionJpaRepository extends JpaRepository<OptionEntity, Long>, OptionCustomRepository {
    List<OptionEntity> findByProduct_IdAndAndParent_Id(Long productId, Long parentOptionId);

    void deleteAllByIdIn(List<Long> optionIds);

    List<OptionEntity> findAllByIdIn(List<Long> optionIds);

    void deleteByProduct_Id(Long productId);

    List<OptionEntity> findByProduct_IdIn(List<Long> productIds);

    List<OptionEntity> findByProduct_Id(Long productId);
}
