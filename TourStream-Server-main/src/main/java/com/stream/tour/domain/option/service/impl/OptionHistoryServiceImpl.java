package com.stream.tour.domain.option.service.impl;

import com.stream.tour.domain.option.domain.OptionHistory;
import com.stream.tour.domain.option.entity.OptionHistoryEntity;
import com.stream.tour.domain.option.infrastructure.OptionHistoryJpaRepository;
import com.stream.tour.domain.option.service.OptionHistoryService;
import com.stream.tour.domain.option.service.port.OptionHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OptionHistoryServiceImpl implements OptionHistoryService {
    private final OptionHistoryRepository optionHistoryRepository;

    @Override
    public void saveAllOptionHistory(List<OptionHistory> optionHistories) {
        optionHistoryRepository.saveAll(optionHistories);
    }
}
