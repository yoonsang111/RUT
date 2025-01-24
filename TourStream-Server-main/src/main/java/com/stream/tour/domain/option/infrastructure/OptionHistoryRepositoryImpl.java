package com.stream.tour.domain.option.infrastructure;

import com.stream.tour.domain.option.domain.OptionHistory;
import com.stream.tour.domain.option.entity.OptionHistoryEntity;
import com.stream.tour.domain.option.service.port.OptionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OptionHistoryRepositoryImpl implements OptionHistoryRepository {
    private final OptionHistoryJpaRepository optionHistoryJpaRepository;

    @Override
    public OptionHistory save(OptionHistory optionHistory) {
        return optionHistoryJpaRepository.save(OptionHistoryEntity.from(optionHistory)).toModel();
    }

    @Override
    public void saveAll(List<OptionHistory> optionHistories) {
        optionHistoryJpaRepository.saveAll(OptionHistoryEntity.from(optionHistories));
    }
}
