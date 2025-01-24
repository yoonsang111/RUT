package com.stream.tour.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ExceptionDto {
    private String errorCode;
    private String errorDescription;
    private String message;
}
