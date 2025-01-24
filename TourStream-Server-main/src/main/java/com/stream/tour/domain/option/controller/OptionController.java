package com.stream.tour.domain.option.controller;

import com.stream.tour.domain.option.dto.UpdateOptionRequest;
import com.stream.tour.domain.option.facade.OptionFacade;
import com.stream.tour.global.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "옵션 API", description = "옵션 API")
@RestController
@RequestMapping("/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionFacade optionFacade;

//    @Operation(summary = "옵션 등록", description = "옵션 여러 개를 등록 한다.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "옵션 등록 성공"),
//            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResult.class))),
//            @ApiResponse(responseCode = "500", description = "옵션 등록 실패", content = @Content(schema = @Schema(implementation = ApiResult.class)))
//    })
//    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<ApiResult<Void>> saveOptions(@Parameter @RequestBody @Validated SaveOptionRequest request) {
//        optionFacade.saveOptions(request);
//        return ApiResult.createSuccessWithNoContent();
//    }


//    @Operation(summary = "옵션 조회", description = "상품에 해당 하는 옵션을 조회 한다.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "옵션 조회 성공"),
//            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResult.class))),
//            @ApiResponse(responseCode = "500", description = "옵션 조회 실패", content = @Content(schema = @Schema(implementation = ApiResult.class)))
//    })
//    @GetMapping("/{productId}")
//    public ResponseEntity<ApiResult<List<OptionResponse>>> getOptions(@PathVariable Long productId) {
//        List<OptionResponse> options = optionFacade.getOptions(productId);
//        return ApiResult.createSuccess(options);
//    }

    @Operation(summary = "옵션 수정", description = "옵션 여러 개를 수정 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "옵션 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResult.class))),
            @ApiResponse(responseCode = "500", description = "옵션 수정 실패", content = @Content(schema = @Schema(implementation = ApiResult.class)))
    })
    @PutMapping
    public ResponseEntity<ApiResult<Void>> updateOptions(@Parameter @RequestBody UpdateOptionRequest request) {
        optionFacade.updateOption(request);
        return ApiResult.createSuccessWithNoContent();
    }

//    @Operation(summary = "옵션 삭제", description = "옵션 여러 개를 삭제 한다.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "옵션 삭제 성공"),
//            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
//            @ApiResponse(responseCode = "500", description = "옵션 삭제 실패")
//    })
//    @DeleteMapping
//    public ResponseEntity<ApiResult<Void>> deleteOptions(@Parameter @RequestParam(name = "optionIds") List<Long> optionIds) {
//        optionFacade.deleteOption(optionIds);
//        return ApiResult.createSuccessWithNoContent();
//    }
}
