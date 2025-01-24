package com.stream.tour.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.reservations.domain.Reservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
public class GetProductNameResponse {

    @Schema(description = "상품 ID", type = "number")
    private Long productId;

    @Schema(description = "상품명", type = "string")
    private String productName;

    @Accessors(fluent = true)
    @JsonProperty("hasNewReservation")
    @Schema(description = "새로운 예약이 있는지 여부", type = "boolean")
    private boolean hasNewReservation;

    public GetProductNameResponse(Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.hasNewReservation = product.getOptions().stream()
                .anyMatch(option -> option.getReservations().stream()
                        .anyMatch(Reservation::isNewReservation));
    }

    public static List<GetProductNameResponse> from(List<Product> products) {
        return products.stream()
                .map(GetProductNameResponse::new)
                .toList();
    }
}
