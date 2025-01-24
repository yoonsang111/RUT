package com.stream.tour.domain.reservations.service.impl;

import com.stream.tour.domain.reservations.dto.ExcelFile;
import com.stream.tour.domain.reservations.repository.ExcelFileRepository;
import com.stream.tour.domain.reservations.service.ExcelFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExcelFileServiceImpl implements ExcelFileService {

    private final ExcelFileRepository excelFileRepository;

    @Override
    public void uploadExcelFile(ExcelFile excelFile) {
        excelFileRepository.save(excelFile);
    }
}
