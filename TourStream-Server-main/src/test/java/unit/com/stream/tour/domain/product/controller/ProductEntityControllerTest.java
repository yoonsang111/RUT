package com.stream.tour.domain.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.tour.common.utils.ProductTestUtils;
import com.stream.tour.domain.product.facade.ProductFacade;
import com.stream.tour.global.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(ProductController.class)
class ProductEntityControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private WebApplicationContext ctx;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ProductFacade productFacade;
    @MockBean private TokenProvider tokenProvider;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @DisplayName("saveProduct(): 상품을 등록한다.")
    @Test
    public void testSaveProduct() throws Exception {
        // given
//        given(productFacade.saveProduct(anyLong() ,any(ProductRequest.Create.class)))
//                .willReturn(new ProductResponse.Create(1L));

        // when then
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductTestUtils.createSaveRequest())))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.productId").value(1L))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("정상적으로 처리되었습니다."));
    }

    @DisplayName("closeSales(): 상품 판매를 마감한다.")
    @Test
    public void testCloseSales() throws Exception {
        mockMvc.perform(put("/products/1/close").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("정상적으로 처리되었습니다."));
    }
}