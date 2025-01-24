package com.stream.tour.domain.option.service.port;

import com.stream.tour.domain.option.domain.Option;

import java.util.List;

public interface OptionRepository {

    List<Option> findByProductId(Long productId);
}
