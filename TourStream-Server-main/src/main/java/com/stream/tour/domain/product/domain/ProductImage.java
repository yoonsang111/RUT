package com.stream.tour.domain.product.domain;

import com.stream.tour.domain.product.infrastructure.entity.ProductImageEntity;
import com.stream.tour.global.enums.Directory;
import com.stream.tour.global.enums.FileType;
import com.stream.tour.global.service.ClockHolder;
import com.stream.tour.global.service.UuidHolder;
import com.stream.tour.global.storage.StoredImageInfo;
import com.stream.tour.global.storage.service.StorageService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Getter
public class ProductImage {
    private final Long id;
    private Product product;
    private final String filePath; // 이미지 저장 경로
    private final String originalName; // 원본 파일 명
    private final String storedName; // 저장 파일 명
    private final String extension; // 파일 확장자
    private final long size; // 파일 크기
    private final boolean isRepresentative; // 대표 이미지 여부

    @Builder
    public ProductImage(Long id, Product product, String filePath, String originalName, String storedName, String extension, long size, boolean isRepresentative) {
        this.id = id;
        this.product = product;
        this.filePath = filePath;
        this.originalName = originalName;
        this.storedName = storedName;
        this.extension = extension;
        this.size = size;
        this.isRepresentative = isRepresentative;
    }

    // 연관관계 세팅
    public void addProduct(Product product) {
        this.product = product;
    }

    public static List<ProductImageEntity> from(List<ProductImage> productImages) {
        return productImages.stream()
                .map(ProductImageEntity::from)
                .toList();
    }

    public static List<ProductImage> of(List<StoredImageInfo> storedImageInfoList) {
        return storedImageInfoList.stream()
                .map(ProductImage::from)
                .toList();
    }

    public static ProductImage from(StoredImageInfo storedImageInfo) {
        return ProductImage.builder()
                .filePath(storedImageInfo.getFilePath())
                .originalName(storedImageInfo.getOriginalFileName())
                .storedName(storedImageInfo.getStoredName())
                .extension(storedImageInfo.getExtension())
                .size(storedImageInfo.getSize())
                .build();
    }


    public ProductImage updateRepresentative(boolean isRepresentative) {
        return ProductImage.builder()
                .id(id)
                .filePath(filePath)
                .originalName(originalName)
                .storedName(storedName)
                .extension(extension)
                .size(size)
                .isRepresentative(isRepresentative)
                .build();
    }

    public static List<ProductImage> copyRealImages(List<ProductImage> productImages, UuidHolder uuidHolder, ClockHolder clockHolder, StorageService storageService) {
        List<ProductImage> newProductImages = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            Resource resource = storageService.loadAsResource(
                    Base64.getEncoder().encodeToString(productImage.getFilePath().getBytes()),
                    Base64.getEncoder().encodeToString(productImage.getStoredName().getBytes())
            );

            try {
                File file = resource.getFile();
                StoredImageInfo storedImageInfo = storageService.storeImage(file, FileType.IMAGE, Directory.PRODUCT, uuidHolder, clockHolder);
                ProductImage newProductImage = ProductImage.from(storedImageInfo);
                newProductImages.add(newProductImage);
            } catch (IOException e) {
                throw new IllegalStateException("상품 이미지를 찾을 수 없습니다. 상품명: " + resource.getFilename());
            }
        }

        return newProductImages;
    }
}
