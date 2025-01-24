package com.stream.tour.domain.product.controller;


import com.stream.tour.common.BaseIntegrationTest;
import com.stream.tour.common.utils.ProductTestUtils;
import com.stream.tour.domain.product.infrastructure.ProductImageJpaRepository;
import com.stream.tour.domain.product.infrastructure.ProductJpaRepository;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.product.infrastructure.entity.ProductImageEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductEntityControllerIntgTest extends BaseIntegrationTest {

    @Autowired
    private ProductJpaRepository productRepository;

    @Autowired
    private ProductImageJpaRepository productImageRepository;


    @DisplayName("getProducts(): 상품 목록을 가져온다.")
    @Test
    void getGetProducts() throws Exception {
        // given
        // when
        MvcResult mvcResult = mockMvc.perform(
                        get("/products")
                                .header(AUTHORIZATION, authorizationValue)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andReturn();

        // then
        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        objectMapper.readTree(body).get("data").forEach(data -> {
            Long productId = Long.parseLong(String.valueOf(data.get("productId")));
            ProductEntity savedProduct = productRepository.findById(productId).orElseThrow();
            ProductImageEntity productImage = getRepresentativeImageFrom(savedProduct.getProductImages());

            assertThat(data.get("productId").asLong()).isEqualTo(savedProduct.getId());
            assertThat(data.get("productName").asText()).isEqualTo(savedProduct.getName());
            assertThat(data.get("productImageName").asText()).isEqualTo(productImage.getOriginalName());
            assertThat(data.get("isClosed").asBoolean()).isEqualTo(savedProduct.isClosed());
            assertThat(data.get("productImageUrl").asText())
                    .isEqualTo(makeProductImageUrl(productImage));
        });
    }

    @DisplayName("getProduct(): 상품을 가져온다.")
    @Test
    void testGetProduct() throws Exception {
        // given
        mockMvc.perform(
                        get("/products/" + 1)
                                .header(AUTHORIZATION, authorizationValue)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.productId").value(1))
                .andExpect(jsonPath("$.data.productName").value("제주 여행"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @DisplayName("saveProduct(): 상품을 등록한다.")
    @Test
    void testSaveProduct() throws Exception {
        mockMvc.perform(
                        post("/products")
                                .header(AUTHORIZATION, authorizationValue)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtils.createSaveRequest()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.productId").exists())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("정상적으로 처리되었습니다."));
    }

    @DisplayName("updateProduct(): 상품을 수정한다.")
    @Test
    void testUpdateProduct() throws Exception {
        // given
        String body = """
                {
                    "productName": "수정된 제주 여행",
                    "productImages": [
                        {
                            "productImageUrl": "http://localhost:8080/files/2021/10/10/MTIzNDU2Nzg5MDkxMjM0NTY3ODkwOTEyMzQ1Njc4OTA5MTIzNDU2Nzg5MDkxMjM0NTY3ODkwOTEyMzQ1Njc4OTA5MTIzNDU2Nzg5MDkxMjM0NTY3ODkwOTEyMzQ1Njc4OTA5MTIzNDU2Nzg5MDkxMjM0NTY3ODkwOTEyMzQ1Njc4OTA5MTIzNDU2Nzg5MDkxMjM0NTY3ODkwOTEyMzQ1Njc4OTA5MTIzNDU2Nzg5MDkxMjM0NTY3ODkwOTEyMzQ1Njc4OTA5MTIzNDU2Nzg5MDkxMjM0NTY3ODkwOTEyMzQ1Njc4OTA5MTIzNDU2Nzg5MDkxMjM0NTY3ODkwOTEyMzQ1Njc4OTA5MTIzNDU2Nzg5MDkxMjM0NTY3ODkwOTEyMzQ1Njc4OTA5",
                            "isRepresentative": true
                        }
                    ],
                    "description": "수정된 상품 설명",
                    "notice": "수정된 공지사항",
                    "content": "수정된 상품 내용",
                    "tourCourse": "수정된 투어 코스",
                    "includedContent": "수정된 포함 사항",
                    "excludedContent": "수정된 불포함 사항",
                    "otherContent": "수정된 기타 사항",
                    "minDepartureNumber": 2,
                    "refundDetails": [
                        {
                            "refundPolicy": "RATE",
                            "value": 0.2,
                            "startNumber": 1,
                            "endNumber": 10
                        }
                    ],
                    "transportation": "PICKUP",
                    "pickupType": "MEETING_PLACE",
                    "pickupInformation": "수정된 픽업 정보",
                    "pickupLocation": "수정된 픽업 장소",
                    "paymentPlan": "FULL_PRE_PAYMENT",
                    "inquiryType": "DIRECT",
                    "isGuided": false,
                    "customerServiceContact": "010-5678-1234",
                    "operationStartTime": "13:30",
                    "operationEndTime": "14:30",
                    "emergencyContact": "010-5678-1234"
                }
                """;

        // when
        mockMvc.perform(
                        put("/products/" + 1)
                                .header(AUTHORIZATION, accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.productId").exists())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("정상적으로 처리되었습니다."));

        // then
        ProductEntity updatedProduct = productRepository.findById(1L).orElseThrow();
        assertAll(() -> {
            assertThat(updatedProduct.getName()).isEqualTo("수정된 제주 여행");
            assertThat(updatedProduct.getDescription()).isEqualTo("수정된 상품 설명");
        });
    }

    @DisplayName("copyProduct(): 상품을 복사한다.")
    @Test
    void testCopyProduct_shouldSuccess() throws Exception {
        // given
        Resource resource = new ClassPathResource("static/images/product/banana_milk.jpeg");
        String body = mockMvc.perform(
                        multipart("/products/files/upload")
                                .file(new MockMultipartFile("files", "banana_milk.jpeg", MediaType.IMAGE_JPEG.getType(), resource.getInputStream()))
                )
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.defaultCharset());

        final String fileUrl = objectMapper.readTree(body).get("data").get(0).get("fileUrl").asText();
        System.out.println("fileUrl = " + fileUrl);

        // when
        mockMvc.perform(post(String.format("/products/%s/copy", 1))
                        .header(AUTHORIZATION, accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.status").value("SUCCESS"));

        // then

    }

    @DisplayName("getProductNames(): 상품 이름을 가져온다.")
    @Test
    void testGetProductNames() throws Exception {
        // given
        // when
        MvcResult mvcResult = mockMvc.perform(get("/products/names")
                        .header(AUTHORIZATION, accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andReturn();

        // then
        String body = mvcResult.getResponse().getContentAsString();
        objectMapper.readTree(body).get("data").forEach(data -> {
            assertThat(data.get("productId")).isNotNull();
            assertThat(data.get("productName")).isNotNull();
            assertThat(data.get("hasNewReservation")).isNotNull();
        });
    }

    @DisplayName("uploadProductImage(): 상품 이미지를 업로드한다.")
    @Test
    void testUpload() throws Exception {
        // given
        LocalDate now = LocalDate.now();
        String filePath = String.format("product/%s/%s/%s", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        ClassPathResource resource = new ClassPathResource("static/images/product/banana_milk.jpeg");

        // when
        String body = mockMvc.perform(multipart("/products/files/upload")
                        .file(new MockMultipartFile("files", resource.getFilename(), "images/jpg", resource.getContentAsByteArray()))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        objectMapper.readTree(body).get("data").forEach(data -> {
            assertThat(data.get("productImageId").asText()).isEqualTo("1");
            assertThat(data.get("fileUrl").asText()).startsWith(
                    "http://localhost:8080/files/%s".formatted(
                            Base64.getEncoder().encodeToString(filePath.getBytes())
                    )
            );
        });

        // then
        ProductImageEntity productImage = productImageRepository.findById(1L).get();
        assertAll(() -> {
            assertThat(productImage.getOriginalName()).isEqualTo("banana_milk.jpeg");
            assertThat(productImage.getFilePath()).isEqualTo(filePath);
            assertThat(productImage.getExtension()).isEqualTo("jpeg");;
            assertThat(productImage.getStoredName()).isNotNull();
            assertThat(productImage.isRepresentative()).isFalse();
            assertThat(productImage.getSize()).isEqualTo(resource.contentLength());
            assertThat(productImage.getProduct()).isNull();
        });

    }

    @DisplayName("deleteProductImages(): 상품 이미지를 삭제한다.")
    @Test
    void testDeleteProductImages() throws Exception {
        mockMvc.perform(
                        delete("/products/files/1")
                                .header(AUTHORIZATION, authorizationValue)
                )
                .andExpect(status().isOk());
    }

    @DisplayName("deleteProductImages(): 상품 이미지를 여러 건 삭제한다.")
    @Test
    void testDeleteMultiProductImages() throws Exception {
        mockMvc.perform(
                        delete("/products/files/1")
                                .header(AUTHORIZATION, authorizationValue)
                )
                .andExpect(status().isOk());
    }


    private ProductImageEntity getRepresentativeImageFrom(List<ProductImageEntity> productImages) {
        return productImages.stream()
                .filter(ProductImageEntity::isRepresentative)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("대표 이미지가 존재하지 않습니다."));
    }

    private String makeProductImageUrl(ProductImageEntity productImage) {
        return "http://localhost:8080/files/" +
                Base64.getEncoder().encodeToString(productImage.getFilePath().getBytes()) + "/" +
                Base64.getEncoder().encodeToString(productImage.getStoredName().getBytes());
    }
}
