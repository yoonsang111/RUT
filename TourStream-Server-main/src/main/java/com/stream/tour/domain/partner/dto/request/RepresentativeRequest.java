package com.stream.tour.domain.partner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
public class RepresentativeRequest {

    @Schema(hidden = true)
    private Long partnerId;

    @Schema(name = "name", description = "대표자명", required = true, maxLength = 50, minLength = 1)
    @NotBlank
    @Length(max = 50, min = 1)
    private List<String> name;
}
