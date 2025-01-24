package com.stream.tour.domain.test.service.impl;

import com.stream.tour.domain.test.repository.custom.TestCustomRepository;
import com.stream.tour.domain.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestCustomRepository testCustomRepository;
}
