package com.stream.tour.domain.option.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(OptionController.class) // 해당 컨트롤러로 MockMvc를 생성
@MockBean(JpaMetamodelMappingContext.class) // JPA와 관련된 빈들은 목으로 등록(@EnableJpaAuditing 사용시 필요)
@WithMockUser("user1") // 인증된 모의(가짜) 사용자를 만들어서 사용
class OptionControllerTest {
//
//    ObjectMapper objectMapper;
//
//    public OptionControllerTest() {
//        objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//    }
//
//    @Autowired MockMvc mockMvc;
//    @MockBean OptionFacade optionFacade;
//    @MockBean private TokenProvider tokenProvider;
//
//    @Test
//    @DisplayName("옵션 조회 성공 - 서브 옵션 x")
//    void getOptions() throws Exception {
//        // given
//        Long productId = 1L;
//        List<OptionResponse> optionResponse = List.of(OptionResponse.builder()
//                .id(1L)
//                .name("오슬롭")
//                .salesStatus("SALE")
//                .stockQuantity(100)
//                .siteCurrency("KRW")
//                .platformCurrency("KRW")
//                .sitePrice(BigDecimal.valueOf(10000))
//                .platformPrice(BigDecimal.valueOf(10000))
//                .salesStartDate(LocalDate.now())
//                .applicationDay("TOTAL")
//                .specificDate(LocalDate.of(2023, 9, 30))
//                .build());
//        given(optionFacade.getOptions(productId)).willReturn(optionResponse);
//
//        // when
//        ResultActions resultActions = mockMvc.perform(get("/options/{productId}", productId));
//
//        // then
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("SUCCESS"))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("옵션 등록 성공")
//    void saveOptions() throws Exception {
//        //given
//        SaveOptionRequest.OptionRequest subOptionRequest = new SaveOptionRequest.OptionRequest();
//        subOptionRequest.setName("오슬롭2");
//        subOptionRequest.setSalesStatus("SALE");
//        subOptionRequest.setStockQuantity(50);
//        subOptionRequest.setSiteCurrency("KRW");
//        subOptionRequest.setPlatformCurrency("KRW");
//        subOptionRequest.setSitePrice(BigDecimal.valueOf(10000));
//        subOptionRequest.setPlatformPrice(BigDecimal.valueOf(10000));
//        subOptionRequest.setSalesStartDate(LocalDate.now());
//        subOptionRequest.setApplicationDay("MONDAY");
//        subOptionRequest.setSpecificDate(LocalDate.of(2023, 9, 20));
//
//        SaveOptionRequest.OptionRequest optionRequest = new SaveOptionRequest.OptionRequest();
//        optionRequest.setName("오슬롭");
//        optionRequest.setSalesStatus("SALE");
//        optionRequest.setStockQuantity(100);
//        optionRequest.setSiteCurrency("KRW");
//        optionRequest.setPlatformCurrency("KRW");
//        optionRequest.setSitePrice(BigDecimal.valueOf(10000));
//        optionRequest.setPlatformPrice(BigDecimal.valueOf(10000));
//        optionRequest.setSalesStartDate(LocalDate.now());
//        optionRequest.setApplicationDay("TODAY");
//        optionRequest.setSpecificDate(LocalDate.of(2023, 9, 30));
//        optionRequest.setSubOptions(List.of(subOptionRequest));
//
//        SaveOptionRequest saveOptionRequest = new SaveOptionRequest();
//        saveOptionRequest.setProductId(1L);
//        saveOptionRequest.setOptions(List.of(optionRequest));
//
//        // when
//        ResultActions resultActions = mockMvc.perform(post("/options")
//                .content(objectMapper.writeValueAsString(saveOptionRequest))
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(csrf()));
//
//        // then
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("SUCCESS"))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("옵션 수정 성공")
//    void updateOptions() throws Exception {
//        // given
//        UpdateOptionRequest updateOptionRequest = new UpdateOptionRequest();
//        updateOptionRequest.setOptionId(1L);
//        updateOptionRequest.setName("오슬롭 이름 수정");
//        updateOptionRequest.setSalesStatus("SALE");
//        updateOptionRequest.setStockQuantity(100);
//        updateOptionRequest.setSiteCurrency("KRW");
//        updateOptionRequest.setPlatformCurrency("KRW");
//        updateOptionRequest.setSitePrice(BigDecimal.valueOf(10000));
//        updateOptionRequest.setPlatformPrice(BigDecimal.valueOf(10000));
//        updateOptionRequest.setSalesStartDate(LocalDate.now());
//        updateOptionRequest.setApplicationDay("TODAY");
//        updateOptionRequest.setSpecificDate(LocalDate.of(2023, 9, 30));
//        updateOptionRequest.setSiteCurrencyId(1);
//        updateOptionRequest.setPlatformCurrencyId(1);
//
//        List<UpdateOptionRequest> updateOptionRequests = List.of(updateOptionRequest);
//
//        // when
//        ResultActions resultActions = mockMvc.perform(put("/options")
//                .content(objectMapper.writeValueAsString(updateOptionRequests))
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(csrf()));
//
//        // then
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("SUCCESS"))
//                .andDo(print());
//
//    }
//
//    @Test
//    @DisplayName("옵션 삭제 성공")
//    void deleteOptions() throws Exception {
//        //given
//        List<Long> optionIds = List.of(1L, 2L, 3L);
//        String param = optionIds.stream().map(Object::toString).collect(Collectors.joining(",")); // 각 요소를 문자열로 변환한 후 변환된 문자열들을 ,로 구분하여 결합
//
//        // when
//        ResultActions resultActions = mockMvc.perform(delete("/options")
//                .param("optionIds", param)
//                .with(csrf()));
//
//        // then
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("SUCCESS"))
//                .andDo(print());
//    }
}