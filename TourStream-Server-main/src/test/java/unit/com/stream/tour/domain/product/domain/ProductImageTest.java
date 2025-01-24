package com.stream.tour.domain.product.domain;

import com.stream.tour.common.mock.FakeFileUtilsService;
import com.stream.tour.common.mock.FakeStorageService;
import com.stream.tour.common.mock.TestClockHolder;
import com.stream.tour.common.mock.TestUuidHolder;
import com.stream.tour.global.storage.service.StorageService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductImageTest {

    private FakeFileUtilsService fakeFileUtilsService = new FakeFileUtilsService(null, null, "http://localhost:8080");
    private StorageService storageService = new FakeStorageService(fakeFileUtilsService);

    @DisplayName("ProductImage의 실제 이미지를 복사한다.")
    @Test
    void testCopyRealImages() throws IOException {
        // given
        TestUuidHolder testUuidHolder = new TestUuidHolder("bbbbbbbb-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        TestClockHolder testClockHolder = new TestClockHolder(LocalDate.of(2000, 2, 2), LocalDateTime.of(2000, 2, 2, 0, 0));

        ProductImage productImage = ProductImage.builder()
                .id(1L)
                .filePath("static/images/product")
                .originalName("phone.jpeg")
                .storedName("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa.jpeg")
                .extension("jpeg")
                .size(100L)
                .isRepresentative(true)
                .build();

        // when
        List<ProductImage> productImages = ProductImage.copyRealImages(List.of(productImage), testUuidHolder, testClockHolder, storageService);

        // then
        ProductImage newProductImage = productImages.get(0);
        assertAll(() -> {
            assertThat(newProductImage.getId()).isNull();
            assertThat(newProductImage.getFilePath()).isEqualTo("product/2000/2/2");
            assertThat(newProductImage.getOriginalName()).isEqualTo("phone.jpeg");
            assertThat(newProductImage.getStoredName()).isEqualTo("bbbbbbbb-aaaa-aaaa-aaaa-aaaaaaaaaaaa.jpeg");
            assertThat(new File("product/2000/2/2/bbbbbbbb-aaaa-aaaa-aaaa-aaaaaaaaaaaa.jpeg")).exists();
        });

        // clean up
        FileUtils.deleteDirectory(new File("product"));
    }
}