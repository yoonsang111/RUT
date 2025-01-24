package com.stream.tour.domain.option.service.port;

import com.stream.tour.domain.option.domain.OptionHistory;
import com.stream.tour.domain.option.entity.OptionHistoryEntity;

import java.util.List;

public interface OptionHistoryRepository {
    OptionHistory save(OptionHistory optionHistory);

    void saveAll(List<OptionHistory> optionHistories);
}
