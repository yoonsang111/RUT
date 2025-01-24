package com.stream.tour.domain.reservations.facade;

import com.stream.tour.common.utils.PartnerTestUtils;
import com.stream.tour.common.utils.ProductTestUtils;
import com.stream.tour.common.utils.ReservationTestUtils;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.domain.partner.infrastructure.PartnerJpaRepository;
import com.stream.tour.domain.product.infrastructure.ProductJpaRepository;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.reservations.dto.FindReservationStatusListResponse;
import com.stream.tour.domain.reservations.dto.FindReservationStatusRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReservationEntityFacadeTest {

    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private PartnerJpaRepository partnerJpaRepository;

    @Autowired
    private ProductJpaRepository productRepository;

    @Transactional
    @Test
    public void findReservationStatusTest() throws Exception {
        // given
        PartnerEntity partner = PartnerTestUtils.createPartner();
        partnerJpaRepository.save(partner);
        ProductEntity product = ProductTestUtils.createProduct(partner, null, null);
        productRepository.save(product);
        ReflectionTestUtils.setField(partner, "products", List.of(product));

        FindReservationStatusRequest request = ReservationTestUtils.createFindReservationStatusRequest(partner.getId(), product.getId());

        // when
        List<FindReservationStatusListResponse> reservationStatusList = reservationFacade.findReservationStatusList(partner.getId(), request);

        // then
        reservationStatusList.forEach(reservationStatus -> {
            assertThat(reservationStatus.getProductId()).isEqualTo(product.getId());
            assertThat(reservationStatus.getProductName()).isEqualTo(product.getName());
            reservationStatus.getOptions().forEach(option -> {
                assertThat(option.getOptionId()).isEqualTo(product.getOptions().get(0).getId());
                assertThat(option.getOptionName()).isEqualTo(product.getOptions().get(0).getName());
                assertThat(option.getReservedCount()).isEqualTo(1);
                assertThat(option.getTotalCount()).isEqualTo(1);
                option.getReservations().forEach(reservation -> {
//                    assertThat(reservation.getReservationId()).isEqualTo(product.getOptions().get(0).getReservations().get(0).getId());
                    assertThat(reservation.getDate()).isEqualTo(product.getOptions().get(0).getReservationEntities().get(0).getReservationStartDate());
                    assertThat(reservation.getReservedCount()).isEqualTo(1);
                    assertThat(reservation.getTotalCount()).isEqualTo(1);
                });
            });
        });
    }
}