package com.stream.tour.domain.contact.controller;

import com.stream.tour.domain.contact.dto.ContactRequest;
import com.stream.tour.domain.contact.facade.ContactFacade;
import com.stream.tour.global.dto.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactFacade contactFacade;

    @Operation(summary = "문의하기", description = "문의하기를 등록한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "문의하기 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResult.class))),
            @ApiResponse(responseCode = "500", description = "옵션 등록 실패", content = @Content(schema = @Schema(implementation = ApiResult.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResult<Void>> createContact(@Valid @RequestBody ContactRequest request) throws Exception {
        contactFacade.createContact(request);
        return ApiResult.createSuccessWithNoContent();
    }
}
