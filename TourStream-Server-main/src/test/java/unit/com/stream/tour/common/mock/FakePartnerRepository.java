package com.stream.tour.common.mock;

import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.partner.service.port.PartnerRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class FakePartnerRepository implements PartnerRepository {
    private AtomicLong atomicLong = new AtomicLong(1L);

    private List<Partner> data = new CopyOnWriteArrayList<>();

    @Override
    public void save(Partner partner) {
        if (partner.getId() == null || partner.getId() == 0) {
            Partner newPartner = Partner.builder()
                    .id(atomicLong.getAndIncrement())
                    .corporateRegistrationNumber(partner.getCorporateRegistrationNumber())
                    .name(partner.getName())
                    .phoneNumber(partner.getPhoneNumber())
                    .email(partner.getEmail())
                    .password(partner.getPassword())
                    .passwordChanged(partner.isPasswordChanged())
                    .customerServiceContact(partner.getCustomerServiceContact())
                    .operationStartTime(partner.getOperationStartTime())
                    .operationEndTime(partner.getOperationEndTime())
                    .emergencyContact(partner.getEmergencyContact())
                    .build();
            data.add(newPartner);
        } else {
            data.add(partner);
        }
    }

    @Override
    public Partner getById(Long partnerId) {
        return null;
    }

    @Override
    public Partner getByEmail(String email) {
        return null;
    }

    @Override
    public Optional<Partner> findById(Long partnerId) {
        return data.stream()
                .filter(partner -> Objects.equals(partner.getId(), partnerId))
                .findAny();
    }

    @Override
    public Optional<Partner> findByEmail(String email) {
        return null;
    }
}
