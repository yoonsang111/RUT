package com.stream.tour.domain.product.service.impl;

import com.stream.tour.domain.auth.service.AuthService;
import com.stream.tour.domain.option.dto.OptionResponse;
import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.option.service.OptionService;
import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.partner.service.port.PartnerRepository;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.dto.GetProductResponse;
import com.stream.tour.domain.product.dto.GetProductsResponse;
import com.stream.tour.domain.product.service.ProductQueryService;
import com.stream.tour.domain.product.service.port.ProductImageRepository;
import com.stream.tour.domain.product.service.port.ProductRepository;
import com.stream.tour.global.exception.custom.children.NotMyProductException;
import com.stream.tour.global.storage.service.FileUtilsService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Builder
@RequiredArgsConstructor
@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final AuthService authService;
    private final OptionService optionService;
    private final FileUtilsService fileUtilsService;

    private final PartnerRepository partnerRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;


    @Override
    public Product findById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<GetProductsResponse> getProducts(long partnerId) {
        List<Product> products = this.findByPartnerId(partnerId);
        Map<Long, String> productIdFileUrlMap = Product.makeProductIdfileUrlMap(products, fileUtilsService);
        return GetProductsResponse.from(products, productIdFileUrlMap);
    }

    @Override
    public GetProductResponse getProduct(Long partnerId, Long productId) {
        Product product = this.findById(productId);
        product.getProductImages().addAll(productImageRepository.findByProductId(product.getId()));

        // 내 상품인지 확인
        Partner partner = partnerRepository.getById(partnerId);
        if (!Objects.equals(product.getPartner().getId(), authService.getPartnerId())) {
            throw new NotMyProductException(authService.getPartnerId(), product);
        }

        Map<Long, String> productImageIdFileUrlMap = new HashMap<>();
        product.getProductImages().forEach(productImage -> {
            URI fileUri = fileUtilsService.loadAsMvcURI(
                    productImage.getFilePath(),
                    productImage.getStoredName());
            productImageIdFileUrlMap.put(productImage.getId(), fileUri.toString());
        });

        return GetProductResponse.from(product, productImageIdFileUrlMap, getOptions(productId));
    }

    private List<OptionResponse> getOptions(Long productId) {

        // 상위 옵션 조회
        List<OptionEntity> options = optionService.findByProductId(productId);

        // 상위 옵션 dto로 변환
        List<OptionResponse> result = options.stream().map(option ->
                        option.toDto(option, option.getSiteCurrency().getCurrencyCode(), option.getPlatformCurrency().getCurrencyCode()))
                .toList();

        // 상위 옵션 id 추출
        List<Long> optionIds = options.stream().map(OptionEntity::getId).toList();

        // 상위 옵션 id에 맞는 하위 옵션 조회
        List<OptionEntity> subOptions = optionService.findByParentOptionId(optionIds);

        // 하위 옵션 dto로 변환
        List<OptionResponse> subResult = subOptions.stream().map(option ->
                        option.toDto(option, option.getSiteCurrency().getCurrencyCode(), option.getPlatformCurrency().getCurrencyCode()))
                .toList();

        // 상위 옵션 id을 기준으로 하위 옵션 그룹핑
        Map<Long, List<OptionResponse>> subOptionMap = subResult.stream().collect(Collectors.groupingBy(option -> option.getParentOptionId()));

        // dto에 하위 옵션 셋팅
        result.forEach(option -> option.setSubOptions(subOptionMap.get(option.getId())));

        return result;
    }


    @Override
    public List<Product> findByProductIds(List<Long> productIds) {
        return productRepository.findByIdIn(productIds);
    }

    @Override
    public List<Product> findByPartnerId(Long partnerId) {
        return productRepository.findByPartnerId(partnerId);
    }

    @Override
    public List<Product> findByPartnerIdOrderByName(Long partnerId) {
        return productRepository.findByPartnerId(partnerId).stream()
                .sorted(comparing(Product::getName))
                .toList();
    }
}
