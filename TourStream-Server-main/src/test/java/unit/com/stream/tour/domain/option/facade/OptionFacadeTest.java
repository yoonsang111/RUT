package com.stream.tour.domain.option.facade;

import com.stream.tour.domain.exchangeRate.service.ExchangeRateService;
import com.stream.tour.domain.option.dto.SaveOptionRequest;
import com.stream.tour.domain.option.dto.UpdateOptionRequest;
import com.stream.tour.domain.option.service.OptionService;
import com.stream.tour.domain.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OptionFacadeTest {

    @InjectMocks
    OptionFacade optionFacade;
    @Mock ProductService productService;

    @Mock ExchangeRateService exchangeRateService;

    @Mock OptionService optionService;

//    @DisplayName("saveOptions(): 상품의 옵션을 저장한다.")
//    @Test
//    void saveOptions() {
//        // Given
//        Long productId = 1L;
//        SaveOptionRequest saveOptionRequest = new SaveOptionRequest();
//        saveOptionRequest.setProductId(productId);
//        List<SaveOptionRequest.OptionRequest> options = new ArrayList<>();
//
//        SaveOptionRequest.OptionRequest optionRequest = createOptionSaveRequestDto();
//
//        options.add(optionRequest);
//        saveOptionRequest.setOptions(options);
//
//        Product product = ProductTestUtils.createProduct(null, null, null);
//        when(productService.findById(productId)).thenReturn(product);
//
//
//        // When
//        optionFacade.saveOptions(saveOptionRequest);
//
//        // Then
//        verify(productService, times(1)).findById(productId);
//        verify(exchangeRateService, times(1)).findAll();
//        verify(optionService, times(1)).saveOption(any(Option.class));
//        verify(optionService, times(1)).saveSubOptions(anyList());
//    }

//    @DisplayName("getOptions(): 상품 id에 맞는 옵션을 조회한다.")
//    @Test
//    void getOptions() {
//        //given
//        Long productId = 1L;
//        Option option = OptionTestUtils.createTestOption(ProductTestUtils.createProduct(null, null, null), null);
//        ReflectionTestUtils.setField(option, "id", 1L);
//        List<Option> options = List.of(option);
//
//
//        Option subOption = OptionTestUtils.createTestOption(ProductTestUtils.createProduct(null, null, null), null);
//        ReflectionTestUtils.setField(subOption, "parent", option);
//        List<Option> subOptions = List.of(subOption);
//
//        when(optionService.findByProductId(anyLong())).thenReturn(options);
//        when(optionService.findByParentOptionId(anyList())).thenReturn(subOptions);
//
//        // when
////        optionFacade.getOptions(productId);
//
//        // then
//        verify(optionService, times(1)).findByProductId(productId);
//        verify(optionService, times(subOptions.size())).findByParentOptionId(anyList());
//    }
//
//    @DisplayName("updateOption(): 옵션을 수정한다.")
//    @Test
//    void updateOption() {
//        // given
//        Long optionId = 1L;
//        Option option = OptionTestUtils.createTestOption(ProductTestUtils.createProduct(null, null, null), null);
//        List<UpdateOptionRequest> optionRequestDto = createOptionRequestDto();
//
//        when(optionService.findById(optionId)).thenReturn(option);
//
//        // when
////        optionFacade.updateOption(optionRequestDto);
//
//        // then
//        verify(exchangeRateService, times(1)).findAll();
//        verify(optionService, times(optionRequestDto.size())).findById(optionId);
//    }

    @DisplayName("deleteOption(): 옵션을 삭제한다.")
    @Test
    void deleteOption(){
        // given
        List<Long> optionIds = List.of(1L, 2L);

        //when
        optionFacade.deleteOption(optionIds);

        //then
        verify(optionService, times(1)).deleteAllByOptionIds(optionIds);
    }



    private SaveOptionRequest.OptionRequest createOptionSaveRequestDto() {
        SaveOptionRequest.OptionRequest optionRequest = new SaveOptionRequest.OptionRequest();
        optionRequest.setSiteCurrency("USD");
        optionRequest.setPlatformCurrency("KRW");
        optionRequest.setStockQuantity(10);
        optionRequest.setSalesStatus("SALE");
        optionRequest.setSitePrice(BigDecimal.valueOf(10000));
        optionRequest.setPlatformPrice(BigDecimal.valueOf(10000));
        optionRequest.setSalesStartDate(LocalDate.of(2020, 1, 1));
        optionRequest.setSalesEndDate(LocalDate.of(2020, 12, 31));
        optionRequest.setApplicationDay("HOLIDAY");
        optionRequest.setSubOptions(new ArrayList<>());
        optionRequest.setName("테스트 옵션");
        return optionRequest;
    }

    private List<UpdateOptionRequest> createOptionRequestDto() {
        List<UpdateOptionRequest> optionRequestList = new ArrayList<>();
        UpdateOptionRequest optionRequest = new UpdateOptionRequest();
//        optionRequest.setOptionId(1L);
//        optionRequest.setName("테스트 옵션 수정");
//        optionRequest.setSitePrice(BigDecimal.valueOf(11000));
//        optionRequest.setPlatformPrice(BigDecimal.valueOf(11000));
//        optionRequest.setStockQuantity(10);
//        optionRequest.setSalesStatus("SALE");
//        optionRequest.setSalesStartDate(LocalDate.of(2020, 1, 1));
//        optionRequest.setSalesEndDate(LocalDate.of(2020, 2, 28));
//        optionRequest.setApplicationDay("HOLIDAY");
//        optionRequest.setSiteCurrency("USD");
//        optionRequest.setPlatformCurrency("KRW");

        optionRequestList.add(optionRequest);
        return optionRequestList;
    }

}