package com.stream.tour.domain.product.infrastructure.entity;

import com.stream.tour.domain.product.domain.ProductImage;
import com.stream.tour.domain.product.controller.request.ProductRequest;
import com.stream.tour.global.audit.BaseEntity;
import com.stream.tour.global.storage.StoredImageInfo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;

import java.util.List;
import java.util.stream.IntStream;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "product_image")
@Entity
public class ProductImageEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long id;

    @Comment("이미지 저장 경로")
    @Column(nullable = false)
    private String filePath;

    @Comment("원본 파일 명")
    @Column(length = 100)
    private String originalName;

    @Comment("저장 파일 명")
    @Column(length = 100)
    private String storedName;

    @Comment("파일 확장자")
    @Column(length = 5)
    private String extension;

    @Comment("파일 크기")
    private long size;

    @Accessors(fluent = true)
    @Comment("대표 이미지 여부")
    @Column(columnDefinition = "TINYINT", length = 1)
    private boolean isRepresentative;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public static ProductImageEntity from(ProductImage productImage) {
        ProductImageEntity productImageEntity = ProductImageEntity.builder()
                .id(productImage.getId())
                .filePath(productImage.getFilePath())
                .originalName(productImage.getOriginalName())
                .storedName(productImage.getStoredName())
                .extension(productImage.getExtension())
                .size(productImage.getSize())
                .isRepresentative(productImage.isRepresentative())
                .build();

        if (productImage.getProduct() != null) {
            productImageEntity.addProduct(ProductEntity.from(productImage.getProduct()));
        }

        return productImageEntity;
    }

    public ProductImage toModel() {
        return ProductImage.builder()
                .id(id)
                .product(product != null ? product.toModel() : null)
                .filePath(filePath)
                .originalName(originalName)
                .storedName(storedName)
                .extension(extension)
                .size(size)
                .isRepresentative(isRepresentative)
                .build();
    }

    //==생성 메서드==//
    public static List<ProductImageEntity> createProductImage(List<StoredImageInfo> storedImageInfos, List<ProductRequest.ProductImage> productImages) {
        return IntStream.range(0, storedImageInfos.size())
                .mapToObj(i -> createProductImage(storedImageInfos.get(i), productImages.get(i).getIsRepresentative()))
                .toList();
    }

    public static ProductImageEntity createProductImage(StoredImageInfo storedImageInfo, boolean isRepresentative) {
        return ProductImageEntity.builder()
                .filePath(storedImageInfo.getFilePath())
                .originalName(storedImageInfo.getOriginalFileName())
                .storedName(storedImageInfo.getStoredName())
                .extension(storedImageInfo.getExtension())
                .isRepresentative(isRepresentative)
                .build();
    }

    public static List<ProductImageEntity> createProductImage(List<StoredImageInfo> storedImageInfos) {
        return storedImageInfos.stream()
                .map(ProductImageEntity::createProductImage)
                .toList();
    }

    public static ProductImageEntity createProductImage(StoredImageInfo storedImageInfo) {
        return ProductImageEntity.builder()
                .filePath(storedImageInfo.getFilePath())
                .originalName(storedImageInfo.getOriginalFileName())
                .storedName(storedImageInfo.getStoredName())
                .extension(storedImageInfo.getExtension())
                .size(storedImageInfo.getSize())
                .build();
    }

    //==연관관계 메서드==//
    public void addProduct(ProductEntity product) {
        this.product = product;
    }

    public void deepCopyProductImage(ProductEntity product) {
        this.id = null;
        this.product = product;
    }
}
