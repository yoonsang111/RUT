package com.stream.tour.domain.product.controller;

import com.stream.tour.domain.file.dto.UploadFilesResponse;
import com.stream.tour.domain.product.controller.request.ProductRequest;
import com.stream.tour.domain.product.controller.response.ProductResponse;
import com.stream.tour.domain.product.dto.GetProductNameResponse;
import com.stream.tour.domain.product.dto.GetProductResponse;
import com.stream.tour.domain.product.dto.GetProductsResponse;
import com.stream.tour.domain.product.facade.ProductFacade;
import com.stream.tour.domain.product.service.ProductService;
import com.stream.tour.global.dto.ApiResult;
import com.stream.tour.global.jwt.annotation.JwtPartnerId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "상품 API", description = "상품 API")
@Validated
@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductFacade productFacade;
    private final ProductService productService;

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResult.class))),
    })
    @GetMapping
    public ApiResult<List<GetProductsResponse>> getProducts() {
        return ApiResult.createSuccessV2(productService.getProducts());
    }

    @Operation(summary = "상품 단건 & 옵션 조회", description = "상품 단건 맟 옵션을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 단건 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResult.class))),
    })
    @GetMapping("/{productId}")
    public ApiResult<GetProductResponse> getProduct(@PathVariable Long productId) {
        return ApiResult.createSuccessV2(productService.getProduct(productId));
    }

    @Operation(summary = "상품 및 옵션 등록", description = "상품/옵션을 등록한다. fileUrl에서는 상품 이미지 업로드에서 반환받은 fileUrl을 사용")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResult.class))),
    })
    @PostMapping
    public ApiResult<ProductResponse.Create> create(@Validated @RequestBody ProductRequest.Create request) {
        return ApiResult.createSuccessV2(productService.createProduct(request));
    }

    @Operation(summary = "상품 및 옵션 수정", description = "상품 및 옵션을 수정한다. fileUrl에서는 상품 이미지 업로드에서 반환받은 fileUrl을 사용")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResult.class))),
    })
    @PutMapping(value = "/{productId}")
    public ApiResult<ProductResponse.Update> update(@PathVariable Long productId, @Validated @RequestBody ProductRequest request) {
        return ApiResult.createSuccessV2(productService.update(productId, request));
    }

    @Operation(summary = "상품 판매 종료", description = "상품 판매를 종료한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 판매 종료 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
    })
    @PutMapping("/{productId}/close")
    public ApiResult<Void> closeSales(@PathVariable Long productId) {
        productService.closeSales(productId);
        return ApiResult.createSuccessWithNoContentV2();
    }

    @Operation(summary = "상품 복사", description = "기존 상품을 복사한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 복사 성공(복사된 상품의 id 반환)"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResult.class))),
    })
    @PostMapping("/{productId}/copy")
    public ApiResult<Long> copyProduct(@PathVariable Long productId) throws IOException {
        return ApiResult.createSuccessV2(productService.deepCopyProduct(productId));
    }

    @Operation(summary = "예약 현황 상품명 조회", description = "예약 현황에 필요한 상품명을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "예약 현황 상품명 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
    })
    @GetMapping("/names")
    public ApiResult<List<GetProductNameResponse>> getProductNames() {
        return ApiResult.createSuccessV2(productService.getProductNames());
    }

    @Operation(summary = "상품 이미지 업로드", description = "상품 이미지를 업로드한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 이미지 업로드 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
    })
    @PostMapping(value = "/files/upload")
    public ApiResult<List<UploadFilesResponse>> upload(@NotEmpty @Size(max = 20, min = 1) List<MultipartFile> files) {
        return ApiResult.createSuccessV2(productFacade.upload(files));
    }

    @Operation(summary = "상품 이미지 삭제", description = "상품 이미지를 삭제한다. productImageId가 여러 개 일때는 ,로 구분하여 전달")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 이미지 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema())),
    })
    @DeleteMapping(value = "/files/{productImageIds}")
    public ApiResult<Void> deleteProductImage(@JwtPartnerId Long partnerId, @PathVariable(name = "productImageIds") @Size(max = 20, min = 1)  List<Long> productImageIds) {
        productService.deleteProductImages(partnerId, productImageIds);
        return ApiResult.createSuccessWithNoContentV2();
    }
}
