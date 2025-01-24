package com.stream.tour.common.utils;

import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalTime;
import java.util.Arrays;

public class PartnerTestUtils {

    public static PartnerEntity createPartner() {
        return Arrays.stream(PartnerEntity.class.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .map(constructor -> {
                    constructor.setAccessible(true);
                    PartnerEntity partner = null;
                    try {
                        partner = (PartnerEntity) constructor.newInstance();
//                        ReflectionTestUtils.setField(partner, "representatives", List.of(representative));
//                        ReflectionTestUtils.setField(partner, "products", products);
                        ReflectionTestUtils.setField(partner, "name", "partnerA");
                        ReflectionTestUtils.setField(partner, "corporateRegistrationNumber", "010-1234-5678");
                        ReflectionTestUtils.setField(partner, "name", "홍길동");
                        ReflectionTestUtils.setField(partner, "phoneNumber", "010-1234-5678");
                        ReflectionTestUtils.setField(partner, "email", "test@test.com");
                        ReflectionTestUtils.setField(partner, "password", "1234");
                        ReflectionTestUtils.setField(partner, "passwordChanged", false);
                        ReflectionTestUtils.setField(partner, "customerServiceContact", "010-1234-1234");
                        ReflectionTestUtils.setField(partner, "operationStartTime", LocalTime.of(9, 0));
                        ReflectionTestUtils.setField(partner, "operationEndTime", LocalTime.of(18, 0));
                        ReflectionTestUtils.setField(partner, "emergencyContact", "010-1234-1234");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return partner;
                })
                .findAny().orElseThrow();
    }
}
