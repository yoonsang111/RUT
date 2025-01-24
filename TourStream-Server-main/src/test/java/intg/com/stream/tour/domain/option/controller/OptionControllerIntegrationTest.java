package com.stream.tour.domain.option.controller;


import com.stream.tour.common.BaseIntegrationTest;
import com.stream.tour.common.utils.OptionTestUtils;
import com.stream.tour.common.utils.ProductTestUtils;
import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.option.infrastructure.OptionJpaRepository;
import com.stream.tour.domain.product.infrastructure.ProductJpaRepository;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class OptionControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired private OptionJpaRepository optionJpaRepository;
    @Autowired private ProductJpaRepository productRepository;
    private final String url = "/options";
    private ProductEntity product;
    private OptionEntity option;



    @BeforeEach
    public void setUp() throws Exception {
        product = ProductTestUtils.createProduct(partner, null, null);
        option = OptionTestUtils.createOption(product, null);
    }


//    @Test
//    @DisplayName("옵션 조회 성공")
//    void getOptions() throws Exception {
//        // given
//        productRepository.save(product);
//        optionRepository.save(option);
//
//        // when
//        mockMvc.perform(get(url + "/{productId}", product.getId()).header(AUTHORIZATION, authorizationValue))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].id").exists())
////                .andExpect(jsonPath("$.data[0].stockQuantity").value(option.getStockQuantity()))
////                .andReturn();
//                .andDo(print());
//    }
}
