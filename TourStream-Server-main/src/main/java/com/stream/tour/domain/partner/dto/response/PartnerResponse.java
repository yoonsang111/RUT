package com.stream.tour.domain.partner.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PartnerResponse {

    @Schema(name = "partnerId", description = "등록된 파트너 ID", required = true, example = "1")
    private Long partnerId;

    public static class Save extends PartnerResponse{
        public Save(Long partnerId){
            super(partnerId);
        }
    }

    public static class Update extends PartnerResponse{
        public Update(Long partnerId){
            super(partnerId);
        }
    }
}
