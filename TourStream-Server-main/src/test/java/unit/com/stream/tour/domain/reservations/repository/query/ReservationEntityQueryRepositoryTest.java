package com.stream.tour.domain.reservations.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stream.tour.common.TestConfig;
import com.stream.tour.common.utils.PartnerTestUtils;
import com.stream.tour.common.utils.ProductTestUtils;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import com.stream.tour.domain.partner.infrastructure.PartnerJpaRepository;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.product.infrastructure.ProductJpaRepository;
import com.stream.tour.domain.reservations.dto.FindReservationRequest;
import com.stream.tour.domain.reservations.dto.FindReservationResponse;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationEntityQueryRepositoryTest {

    @Autowired TestEntityManager testEntityManager;
    @Autowired
    PartnerJpaRepository partnerJpaRepository;
    @Autowired
    ProductJpaRepository productRepository;

    private ReservationQueryRepository reservationQueryRepository;

    @BeforeEach
    public void setUp() throws Exception {
        EntityManager entityManager = testEntityManager.getEntityManager();
        reservationQueryRepository = new ReservationQueryRepository(new JPAQueryFactory(entityManager));
    }

    @Test
    public void 아무_조건이_없는_경우() throws Exception {
        // given
        PartnerEntity partner = savePartner();
        saveProduct(partner);

        // parameters
        FindReservationRequest request = createFindReservationRequest();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<FindReservationResponse> reservations = reservationQueryRepository.findReservations(partner.getId(), request, pageable);

        reservations.forEach(reservation -> {
            System.out.println("reservation = " + reservation);
        });

        // then
    }

    private void saveProduct(PartnerEntity partner) {
        ProductEntity product = ProductTestUtils.createProduct(partner, null, null);
        productRepository.save(product);
    }

    private PartnerEntity savePartner() {
        PartnerEntity partner = PartnerTestUtils.createPartner();
        partnerJpaRepository.save(partner);
        return partner;
    }

    private static FindReservationRequest createFindReservationRequest() {
        FindReservationRequest request = new FindReservationRequest();
        ReflectionTestUtils.setField(request, "partnerId", 1L);
        return request;
    }


}