package com.stream.tour.domain.option.service;

import com.stream.tour.domain.option.domain.OptionHistory;
import com.stream.tour.domain.option.entity.OptionHistoryEntity;

import java.util.List;

public interface OptionHistoryService {
    void saveAllOptionHistory(List<OptionHistory> optionHistories);
}
