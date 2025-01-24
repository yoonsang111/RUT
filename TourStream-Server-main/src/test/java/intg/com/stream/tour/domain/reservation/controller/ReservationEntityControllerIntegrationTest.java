package com.stream.tour.domain.reservation.controller;

import com.stream.tour.common.BaseIntegrationTest;
import com.stream.tour.common.utils.*;
import com.stream.tour.domain.bank.entity.BankCode;
import com.stream.tour.domain.bank.repository.BankCodeRepository;
import com.stream.tour.domain.customer.entity.Customer;
import com.stream.tour.domain.customer.repository.CustomerRepository;
import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.product.infrastructure.ProductJpaRepository;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.reservations.entity.ReservationCustomerEntity;
import com.stream.tour.domain.reservations.infrastructure.entity.ReservationEntity;
import com.stream.tour.domain.reservations.infrastructure.ReservationCustomerJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservationEntityControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired private ReservationCustomerJpaRepository reservationCustomerJpaRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private BankCodeRepository bankCodeRepository;
    @Autowired private ProductJpaRepository productRepository;

    BankCode bankCode;
    Customer customer;
    ReservationCustomerEntity reservationCustomer;
    ReservationEntity reservationEntity;
    OptionEntity option;
    ProductEntity product;


    @BeforeEach
    void before() {
        bankCode = bankCodeRepository.save(BankCodeTestUtils.createBankCode());
        customer = customerRepository.save(CustomerTestUtils.createCustomer());
        reservationCustomer = reservationCustomerJpaRepository.save(ReservationCustomerTestUtils.createReservationCustomer(customer));

        reservationEntity = ReservationTestUtils.createReservation(List.of(reservationCustomer), customer, option, bankCode);
        option = OptionTestUtils.createOption(product, List.of(reservationEntity));
        product = productRepository.save(ProductTestUtils.createProduct(partner, List.of(option), null));
        ReflectionTestUtils.setField(partner, "products", List.of(product));
    }

    @DisplayName("findReservationStatus(): 예약 현황 단건 조회")
    @Test
    void testFindReservationStatus() throws Exception {
        mockMvc.perform(get("/reservations/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, authorizationValue)
                        .param("reservationIds", reservationEntity.getId().toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("정상적으로 처리되었습니다."));
    }


    @DisplayName("findReservationStatusList(): 예약 현황 조회")
    @Test
    void testFindReservationStatusList() throws Exception {
        mockMvc.perform(
                get("/reservations/status-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, authorizationValue)
                        .param("productIds", "1,2,3")
                        .param("startDate", "2023-10-15")
                        .param("numberOfDaysToFetch", "1")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

//    @Test
//    public void testUpdateReservationDate() throws Exception {
//        Long partnerId = 1L;
//        Long reservationId = 1L;
//        LocalDate reservationStartDate = LocalDate.now();
//        LocalDate reservationEndDate = LocalDate.now().plusDays(1);
//
//        mockMvc.perform(put("/reservations/status/" + reservationId + "/date")
//                .header("Authorization", "Bearer " + accessToken)
//                .param("partnerId", partnerId.toString())
//                .param("reservationStartDate", reservationStartDate.toString())
//                .param("reservationEndDate", reservationEndDate.toString())
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

}