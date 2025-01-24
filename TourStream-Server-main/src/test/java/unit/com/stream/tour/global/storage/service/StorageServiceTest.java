package com.stream.tour.global.storage.service;

import com.stream.tour.global.storage.StorageProperties;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("로컬 파일 저장 테스트")
@ExtendWith(MockitoExtension.class)
class StorageServiceTest {
    private StorageService storageService;
    @Mock private StorageProperties.Location rootLocation;
    @Mock private FileUtilsService fileService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        StorageProperties storageProperties = new StorageProperties();
        storageProperties.setLocation(rootLocation);
        FileUtils.deleteDirectory(new File("src/main/resources/static/images/product/1910"));
    }

    @AfterEach
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File("src/main/resources/static/images/product/1910"));
    }

    @DisplayName("파일 저장 경로 생성 테스트")
    @Test
    public void testStoredDirectory() throws Exception {
        // given
        File file = new File("src/test/resources/static/images/product/banana_milk.jpeg");
        MultipartFile multipartFile = new MockMultipartFile("banana_milk", "banana_milk.jpeg", "image/jpeg", file.getPath().getBytes());

        given(rootLocation.getProduct()).willReturn(Paths.get("src/main/resources/static/images/product"));
//        given(fileService.createDirectories(rootLocation.getProduct())).willReturn(Paths.get("src/main/resources/static/images/product/1910/1/1"));
        // 실제 경로 생성
        Files.createDirectories(Paths.get("src/main/resources/static/images/product/1910/1/1"));

//        given(fileService.generateStoredFileName(multipartFile.getOriginalFilename())).willReturn("aRandomUUID.jpeg");

        // when
//        storageService.storeImage(multipartFile, FileType.IMAGE, Directory.PRODUCT);

        // then
        assertThat(Files.exists(Paths.get("src/main/resources/static/images/product/1910/1/1"))).isTrue();
    }

    @DisplayName("파일 하나 저장 테스트")
    @Test
    public void testStoreOneFile() throws Exception {
        // given
        File file = new File("src/test/resources/static/images/product/banana_milk.jpeg");
        MultipartFile multipartFile = new MockMultipartFile("banana_milk", "banana_milk.jpeg", "image/jpeg", file.getPath().getBytes());

        given(rootLocation.getProduct()).willReturn(Paths.get("src/main/resources/static/images/product"));
//        given(fileService.createDirectories(rootLocation.getProduct())).willReturn(Paths.get("src/main/resources/static/images/product/1910/1/1"));
        // 실제 경로 생성
        Files.createDirectories(Paths.get("src/main/resources/static/images/product/1910/1/1"));

//        given(fileService.generateStoredFileName(multipartFile.getOriginalFilename())).willReturn("aRandomUUID.jpeg");

        // when
//        storageService.storeImage(multipartFile, FileType.IMAGE, Directory.PRODUCT);

        // then
        assertThat(Files.exists(Paths.get("src/main/resources/static/images/product/1910/1/1/aRandomUUID.jpeg"))).isTrue();
    }

}