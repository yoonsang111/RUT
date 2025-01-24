package com.stream.tour.domain.product.service.impl;

import com.stream.tour.common.mock.FakePartnerRepository;
import com.stream.tour.common.mock.FakeProductImageRepository;
import com.stream.tour.common.mock.FakeProductRepository;
import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.domain.ProductImage;
import com.stream.tour.domain.product.dto.GetProductsResponse;
import com.stream.tour.domain.product.enums.InquiryType;
import com.stream.tour.domain.product.enums.PaymentPlan;
import com.stream.tour.domain.product.enums.PickupType;
import com.stream.tour.domain.product.enums.Transportation;
import com.stream.tour.global.infrastructure.SystemClockHolder;
import com.stream.tour.global.storage.StorageProperties;
import com.stream.tour.global.storage.service.FileUtilsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductServiceImplTest {



}