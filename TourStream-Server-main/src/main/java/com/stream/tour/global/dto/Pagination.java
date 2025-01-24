package com.stream.tour.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public class Pagination<T> {
    private List<T> data;
    private Long totalElements;
    private Boolean hasNext;
}
