package com.stream.tour.domain.option.facade;

import com.stream.tour.domain.exchangeRate.entity.ExchangeRate;
import com.stream.tour.domain.exchangeRate.service.ExchangeRateService;
import com.stream.tour.domain.option.domain.Option;
import com.stream.tour.domain.option.domain.OptionHistory;
import com.stream.tour.domain.option.dto.UpdateOptionRequest;
import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.option.service.OptionHistoryService;
import com.stream.tour.domain.option.service.OptionService;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OptionFacade {

    private final ProductService productService;
    private final ExchangeRateService exchangeRateService;
    private final OptionHistoryService optionHistoryService;
    private final OptionService optionService;

    @Transactional
    public void updateOption(UpdateOptionRequest request) {

        // 통화코드
        Map<String, ExchangeRate> exchangeRateMap = getExchangeRateMap();

        // 상품
        ProductEntity product = null; // productService.findById(request.getProductId());

        List<OptionHistory> optionHistories = new ArrayList<>();

        // 삭제할 옵션 id로 조회해서 option history에 넣기
        if(!request.getDeleteOptionIds().isEmpty() && request.getUpdateOptions() != null) {
            List<Option> findOptions = optionService.findAllByIdIn(request.getDeleteOptionIds());
            findOptions.forEach(option -> {
                optionHistories.add(OptionHistory.createOptionHistory(option, 1)); // TODO version 고치기
            });

            // TODO option 수정된 객체도 저장하려면 아래로 위치 변경할 것
            // optionHistory 저장
            optionHistoryService.saveAllOptionHistory(optionHistories);

            // 삭제할꺼 지우기
//            optionService.deleteAllByOptionIds(request.getDeleteOptionIds());
        }


        // 모두 삭제
        optionService.deleteByProductId(request.getProductId());


        // 옵션 새로 저장
        request.getUpdateOptions().forEach(option -> {

            // 통화 코드 ID 조회
            ExchangeRate siteCurrency = exchangeRateMap.get(option.getSiteCurrency());
            ExchangeRate platformCurrency = exchangeRateMap.get(option.getPlatformCurrency());

            OptionEntity parentOption = optionService.saveOption(OptionEntity.createOption(product, option, null, siteCurrency, platformCurrency));
            List<OptionEntity> subOptions = new ArrayList<>();

            if (!option.getSubOptions().isEmpty() && option.getSubOptions() != null) {
                option.getSubOptions().forEach(sub -> {
                    ExchangeRate subSiteCurrency = exchangeRateMap.get(sub.getSiteCurrency());
                    ExchangeRate subPlatformCurrency = exchangeRateMap.get(sub.getPlatformCurrency());

                    subOptions.add(OptionEntity.createOption(product, sub, parentOption, subSiteCurrency, subPlatformCurrency));
                });

                optionService.saveSubOptions(subOptions);
            }
        });

    }

    @Transactional
    public void deleteOption(List<Long> optionIds) {
        optionService.deleteAllByOptionIds(optionIds);
    }




    // ====== 내부 메소드 ====== //
    private Map<String, ExchangeRate> getExchangeRateMap() {
        List<ExchangeRate> exchangeRateList = exchangeRateService.findAll();
        Map<String, ExchangeRate> exchangeMap = exchangeRateList.stream()
                .collect(Collectors.toMap(ExchangeRate::getCurrencyCode, exchangeRate -> exchangeRate, (oldValue, newValue) -> newValue)); // 같은 key 값이 있을 수 있으므로 마지막에 있는 값으로 덮어씌운다.
        return exchangeMap;
    }

/*    private Integer getCurrencyCodeId(String request) {
        return exchangeRateService.findByCode(request).getId();
    }

    private String getCurrencyCode(Integer codeId) {
        return exchangeRateService.findByCodeById(codeId).getCurrencyCode();
    }*/

    //    @Transactional
//    public void saveOptions(SaveOptionRequest saveOptionRequests) {
//        List<Option> options = new ArrayList<>();
//        Product product = productService.findById(saveOptionRequests.getProductId());
//
//        Map<String, ExchangeRate> exchangeMap = getExchangeRateMap();
//
//
//        saveOptionRequests.getOptions().forEach(request -> {
//            // 상위 옵션
//            // 하위 옵션에 상위 옵션을 넣어줘야 하기 때문에 먼저 상위 옵션을 저장한다.
//            ExchangeRate siteCurrency = exchangeMap.get(request.getSiteCurrency());
//            ExchangeRate platformCurrency = exchangeMap.get(request.getPlatformCurrency());
//
//            Option parentOption = optionService.saveOption(Option.createOption(product, request, null, siteCurrency, platformCurrency));
//
//            // 하위 옵션
//            request.getSubOptions().forEach(sub -> {
//                ExchangeRate subSiteCurrency = exchangeMap.get(sub.getSiteCurrency());
//                ExchangeRate subPlatformCurrency = exchangeMap.get(sub.getPlatformCurrency());
//
//                options.add(Option.createOption(product, sub, parentOption, subSiteCurrency, subPlatformCurrency));
//            });
//        });
//
//        optionService.saveSubOptions(options);
//    }
//
//    @Transactional(readOnly = true)
//    public List<OptionResponse> getOptions(Long productId) {
//
//        // 상위 옵션 조회
//        List<Option> options = optionService.findByProductId(productId);
//
//        // 상위 옵션 dto로 변환
//        List<OptionResponse> result = options.stream().map(option ->
//                option.toDto(option, option.getSiteCurrency().getCurrencyCode(), option.getPlatformCurrency().getCurrencyCode()))
//                .toList();
//
//        // 상위 옵션 id 추출
//        List<Long> optionIds = options.stream().map(Option::getId).toList();
//
//        // 상위 옵션 id에 맞는 하위 옵션 조회
//        List<Option> subOptions = optionService.findByParentOptionId(optionIds);
//
//        // 하위 옵션 dto로 변환
//        List<OptionResponse> subResult = subOptions.stream().map(option ->
//                option.toDto(option, option.getSiteCurrency().getCurrencyCode(), option.getPlatformCurrency().getCurrencyCode()))
//                .toList();
//
//        // 상위 옵션 id을 기준으로 하위 옵션 그룹핑
//        Map<Long, List<OptionResponse>> subOptionMap = subResult.stream().collect(Collectors.groupingBy(option -> option.getParentOptionId()));
//
//        // dto에 하위 옵션 셋팅
//        result.forEach(option -> option.setSubOptions(subOptionMap.get(option.getId())));
//
//        return result;
//    }
}
