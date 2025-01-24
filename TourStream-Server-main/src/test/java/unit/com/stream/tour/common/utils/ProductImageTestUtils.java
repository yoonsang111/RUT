package com.stream.tour.common.utils;

import com.stream.tour.domain.product.infrastructure.entity.ProductImageEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

public class ProductImageTestUtils {

    public static ProductImageEntity createProductImage() {
        return Arrays.stream(ProductImageEntity.class.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .map(constructor -> {
                    constructor.setAccessible(true);
                    ProductImageEntity productImage = null;
                    try {
                        productImage = (ProductImageEntity) constructor.newInstance();
                        ReflectionTestUtils.setField(productImage, "filePath", "product/2024/1/20");
                        ReflectionTestUtils.setField(productImage, "originalName", "IMG_0531.jpg");
                        ReflectionTestUtils.setField(productImage, "storedName", "de96299d-cea2-42ab-ba6e-a2f84265da21.jpg");
                        ReflectionTestUtils.setField(productImage, "extension", "jpg");
                        ReflectionTestUtils.setField(productImage, "size", 3763067L);
                        ReflectionTestUtils.setField(productImage, "isRepresentative", true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return productImage;
                })
                .findAny().orElseThrow();
    }
}
