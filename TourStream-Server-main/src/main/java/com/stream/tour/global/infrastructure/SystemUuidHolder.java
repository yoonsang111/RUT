package com.stream.tour.global.infrastructure;

import com.stream.tour.global.service.UuidHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SystemUuidHolder implements UuidHolder {

    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}
