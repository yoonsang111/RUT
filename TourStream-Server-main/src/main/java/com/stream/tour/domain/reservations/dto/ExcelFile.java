package com.stream.tour.domain.reservations.dto;

import com.stream.tour.global.audit.CreatedInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelFile extends CreatedInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    private Long reservationId;

    private String originalFileName;

    private String storedFileName;

    private String fileExtension;

    private String filePath;


    public static ExcelFile createExcelFile(Long reservationId ,String originalFileName, String storedFileName, String fileExtension, String filePath) {
        return new ExcelFile(null, reservationId, originalFileName, storedFileName, fileExtension, filePath);
    }
}
