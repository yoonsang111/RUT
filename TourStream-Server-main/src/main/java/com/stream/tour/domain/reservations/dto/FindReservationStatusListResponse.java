package com.stream.tour.domain.reservations.dto;

import com.stream.tour.domain.option.domain.Option;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.reservations.domain.Reservation;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.stream.tour.global.utils.CollectorsUtil.getIdsFrom;
import static java.util.stream.Collectors.groupingBy;

@Data
public class FindReservationStatusListResponse {
    private Long productId;
    private String productName;
    private List<OptionDto> options;

    public FindReservationStatusListResponse(Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.options = OptionDto.from(product.getOptions());
    }

    @Data
    public static class OptionDto {
        private Long optionId;
        private String optionName;
        private int reservedCount;
        private int totalCount;
        private List<ReservationDto> reservations;

        public OptionDto(Option option) {

            List<ReservationDto> reservationDtos = new ArrayList<>();
            Map<LocalDate, List<Reservation>> reservationsByStartDate = option.getReservations().stream()
                    .collect(groupingBy(Reservation::getReservationStartDate));

            for (LocalDate startDate : reservationsByStartDate.keySet()) {
                reservationDtos.addAll(ReservationDto.from(reservationsByStartDate.get(startDate)));
            }

            this.optionId = option.getId();
            this.optionName = option.getName();
            this.reservations = reservationDtos;
            this.totalCount = option.getReservations().size();
            this.reservedCount = (int) option.getReservations().stream()
                    .filter(Reservation::isReserved)
                    .count();
        }

        public static List<OptionDto> from(List<Option> options) {
            return options.stream()
                    .map(OptionDto::new)
                    .toList();
        }
    }

    @Getter @Setter
    public static class ReservationDto {
        private List<Long> reservationIds;
        private LocalDate date;
        private boolean hasNewReservation;
        private int reservedCount;
        private int totalCount;

        @Builder
        public ReservationDto(List<Long> reservationIds, LocalDate date, boolean hasNewReservation, int reservedCount, int totalCount) {
            this.reservationIds = reservationIds;
            this.date = date;
            this.hasNewReservation = hasNewReservation;
            this.reservedCount = reservedCount;
            this.totalCount = totalCount;
        }

        public static List<ReservationDto> from(List<Reservation> reservations) {
            List<Long> reservationIds = getIdsFrom(reservations, Reservation::getId);
            int reservedCount = (int) reservations.stream().filter(Reservation::isReserved).count();
            int totalCount = reservations.size();

            return reservations.stream()
                    .map(reservation -> ReservationDto.builder()
                            .reservationIds(reservationIds)
                            .date(reservation.getReservationStartDate())
                            .hasNewReservation(reservation.isReserved())
                            .reservedCount(reservedCount)
                            .totalCount(totalCount)
                            .build())
                    .toList();
        }
    }
}
