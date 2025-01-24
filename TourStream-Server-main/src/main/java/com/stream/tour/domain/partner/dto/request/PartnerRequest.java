package com.stream.tour.domain.partner.dto.request;

import com.stream.tour.domain.partner.domain.Representative;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class PartnerRequest {

    @Schema(name = "representatives", description = "대표자명", required = true, maxLength = 50, minLength = 1)
    private List<Representative> representatives;

    @Schema(name = "name", description = "회사명", required = true, maxLength = 50, minLength = 1)
    private String name;

    @Schema(name = "phoneNumber", description = "휴대폰번호", required = true, maxLength = 50, minLength = 1)
    private String phoneNumber;

    @Schema(name = "email", description = "이메일", required = true, maxLength = 50, minLength = 1)
    private String email;

    @Schema(name = "password", description = "비밀번호", required = true, maxLength = 50, minLength = 1)
    private String password;

    @Schema(name = "corporateRegistrationNumber", description = "사업자 등록번호", required = true, maxLength = 50, minLength = 1)
    private String corporateRegistrationNumber;

    @Schema(name = "customerServiceContact", description = "고객센터 연락처", required = true, maxLength = 50, minLength = 1)
    private String customerServiceContact;

    @Schema(name = "operationStartTime", description = "오픈 시간", required = true, maxLength = 50, minLength = 1)
    private LocalTime operationStartTime;

    @Schema(name = "operationEndTime", description = "클로즈 시간", required = true, maxLength = 50, minLength = 1)
    private LocalTime operationEndTime;

    @Schema(name = "emergencyContact", description = "비상 연락처", required = true, maxLength = 50, minLength = 1)
    private String emergencyContact;

    /*
     * SAVA DTO
     */
    @Getter
    @Setter
    @Schema(name = "request", description = "파트너 저장 요청 데이터")
    public static class Save extends PartnerRequest{

    }

    /*
     * UPDATE DTO
     */
    @Getter
    @Setter
    public static class Update extends PartnerRequest{
        @Schema(name = "partnerId", description = "파트너 아이디", required = true, example = "1")
        @NotNull
        private Long partnerId;
    }
}
