package com.stream.tour.domain.option.infrastructure;

import com.stream.tour.domain.option.entity.OptionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OptionHistoryJpaRepository extends JpaRepository<OptionHistoryEntity, Long> {
    @Query("SELECT COALESCE(MAX(oh.id), 0) FROM OptionHistoryEntity oh")
    Long findMaxOptionHistoryId();
}
