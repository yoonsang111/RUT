package com.stream.tour.common.mock;

import com.stream.tour.global.storage.StorageProperties;
import com.stream.tour.global.storage.service.FileUtilsService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class FakeFileUtilsService extends FileUtilsService {

    public FakeFileUtilsService(StorageProperties storageProperties, String bucketName, String host) {
        super(storageProperties, bucketName, host);
    }

    @Override
    public Resource loadAsResource(String filePath, String storedFilename) {
        return new ClassPathResource("static/images/product/phone.jpeg");
    }
}
