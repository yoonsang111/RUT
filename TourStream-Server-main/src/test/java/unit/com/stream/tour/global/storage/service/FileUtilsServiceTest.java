package com.stream.tour.global.storage.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class FileUtilsServiceTest {

    FileUtilsService fileUtilsService = null; // new FileUtilsService(new BizClock(), new StorageProperties());

    @Test
    void testGetStoredFileNameFrom() throws Exception {
        // given
        String fileUrl = "https:/tour-bucket.s3.amazonaws.com/tour-bucket/2023/12/29/0ed62ed984644eb9862d1563a7caa0b6.jpg";
        String storedName = "0ed62ed984644eb9862d1563a7caa0b6.jpg";

        // when
        String storedFileNameFrom = fileUtilsService.getStoredFileNameFrom(fileUrl);

        // then
        Assertions.assertThat(storedFileNameFrom).isEqualTo(storedName);
    }

    @Test
    void testLoadAsMvcURI() {
        // given
        String filePath = "product/2024/3/5";
        String storedFilename = "50d0935b-3a42-4cb4-8bce-736e214ec214.jpeg";

        // when
        String mvcURI = fileUtilsService.loadAsMvcURI(filePath, storedFilename).toString();

        // then
        Assertions.assertThat(mvcURI).isEqualTo("/files/dGVzdC5qcGc=/MjAyMy8xMi8yOQ==/MGVkNjJlZDk4NDY0NGViOTg2MmQxNTYzYTdjYWEwYjYuanBn");
    }

}