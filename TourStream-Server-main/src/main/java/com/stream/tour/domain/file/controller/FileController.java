package com.stream.tour.domain.file.controller;

import com.stream.tour.global.storage.service.FileUtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileUtilsService fileUtilsService;

    /**
     * @see com.stream.tour.global.storage.service.FileUtilsService#loadAsMvcURI(String, String)
     */
    @GetMapping("/files/{encodedFilePath}/{encodedStoredFilename}")
    public ResponseEntity<Resource> serveFile(
            @PathVariable String encodedFilePath,
            @PathVariable String encodedStoredFilename
    ) {
        Resource resource = fileUtilsService.loadAsResource(encodedFilePath, encodedStoredFilename);

        if (resource == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(resource);
    }
}
