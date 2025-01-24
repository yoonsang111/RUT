package com.stream.tour.domain.product.domain;

import com.stream.tour.global.infrastructure.SystemClockHolder;
import com.stream.tour.global.storage.StorageProperties;
import com.stream.tour.global.storage.service.FileUtilsService;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    private Base64.Encoder encoder = Base64.getEncoder();

    @Test
    void makeProductIdfileUrlMap은_productId와_대표_상품의_fileUrl을_매핑한_map을_반환한다() {
        // given
        StorageProperties storageProperties = new StorageProperties();
        StorageProperties.Location location = new StorageProperties.Location();
        location.setProduct("/product");
        storageProperties.setLocation(location);

        FileUtilsService fileUtilsService = FileUtilsService.builder()
                .clock(Clock.systemDefaultZone())
                .clockHolder(new SystemClockHolder())
                .storageProperties(storageProperties)
                .host("http://localhost:8080")
                .build();

        ProductImage productImage = ProductImage.builder()
                .filePath("product/2024/3/18")
                .storedName("2007fbf9-a6fb-48fd-8b7c-c4df2b0c9be3.jpg")
                .isRepresentative(true)
                .build();

        Product product = Product.builder()
                .id(1L)
                .productImages(List.of(productImage))
                .build();

        // when
        Map<Long, String> productIdfileUrlMap = Product.makeProductIdfileUrlMap(List.of(product), fileUtilsService);

        // then
        assertThat(productIdfileUrlMap).containsEntry(1L, "http://localhost:8080/files/" + encoder.encodeToString("product/2024/3/18".getBytes()) + "/" + encoder.encodeToString("2007fbf9-a6fb-48fd-8b7c-c4df2b0c9be3.jpg".getBytes()));
    }

}