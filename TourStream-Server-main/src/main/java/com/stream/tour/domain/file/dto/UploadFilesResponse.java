package com.stream.tour.domain.file.dto;

import com.stream.tour.domain.product.domain.ProductImage;
import com.stream.tour.global.storage.StoredImageInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class UploadFilesResponse {
    private Long productImageId;
    private String fileUrl;

    public UploadFilesResponse(StoredImageInfo storedImageInfo, List<ProductImage> productImages) {
        Map<String, Long> storedNameIdMap = productImages.stream()
                .collect(Collectors.toMap(
                        ProductImage::getStoredName,
                        ProductImage::getId
                ));

        this.productImageId = storedNameIdMap.get(storedImageInfo.getStoredName());
        this.fileUrl = storedImageInfo.getFullUrl();
    }

}
