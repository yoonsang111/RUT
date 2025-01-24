package com.stream.tour.common.mock;

import com.stream.tour.global.service.UuidHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestUuidHolder implements UuidHolder {

    private final String uuid;

    @Override
    public String random() {
        return uuid;
    }
}
