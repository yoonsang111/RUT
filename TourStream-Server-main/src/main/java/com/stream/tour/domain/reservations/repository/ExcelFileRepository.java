package com.stream.tour.domain.reservations.repository;

import com.stream.tour.domain.reservations.dto.ExcelFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelFileRepository extends JpaRepository<ExcelFile, Long> {
}
