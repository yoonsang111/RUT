package com.stream.tour.domain.product.service.impl;

import com.stream.tour.domain.auth.service.AuthService;
import com.stream.tour.domain.exchangeRate.entity.ExchangeRate;
import com.stream.tour.domain.exchangeRate.service.ExchangeRateService;
import com.stream.tour.domain.option.dto.OptionRequest;
import com.stream.tour.domain.option.dto.OptionResponse;
import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.option.infrastructure.OptionJpaRepository;
import com.stream.tour.domain.option.service.OptionService;
import com.stream.tour.domain.option.service.port.OptionRepository;
import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.partner.service.PartnerService;
import com.stream.tour.domain.product.controller.request.ProductRequest;
import com.stream.tour.domain.product.controller.response.ProductResponse;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.domain.ProductImage;
import com.stream.tour.domain.product.dto.GetProductNameResponse;
import com.stream.tour.domain.product.dto.GetProductResponse;
import com.stream.tour.domain.product.dto.GetProductsResponse;
import com.stream.tour.domain.product.infrastructure.ProductJpaRepository;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.product.service.ProductImageService;
import com.stream.tour.domain.product.service.ProductService;
import com.stream.tour.domain.product.service.RefundDetailService;
import com.stream.tour.domain.product.service.port.ProductImageRepository;
import com.stream.tour.domain.product.service.port.ProductRepository;
import com.stream.tour.domain.reservations.service.ReservationService;
import com.stream.tour.domain.reservations.service.port.ReservationRepository;
import com.stream.tour.global.exception.custom.children.*;
import com.stream.tour.global.properties.ApiProperties;
import com.stream.tour.global.service.ClockHolder;
import com.stream.tour.global.service.UuidHolder;
import com.stream.tour.global.storage.service.FileUtilsService;
import com.stream.tour.global.storage.service.StorageService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Slf4j
@Transactional(readOnly = true)
@Builder
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final AuthService authService;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    private final StorageService storageService;
    private final FileUtilsService fileUtilsService;
    private final ProductImageService productImageService;
    private final PartnerService partnerService;
    private final OptionService optionService;
    private final ReservationService reservationService;
    private final ExchangeRateService exchangeRateService;
    private final RefundDetailService refundDetailService;

    private final ApiProperties apiProperties;
    private final OptionJpaRepository optionJpaRepository;
    private final OptionRepository optionRepository;
    private final ReservationRepository reservationRepository;

    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;
    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product findById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<GetProductsResponse> getProducts() {
        List<Product> products = findByPartnerId(authService.getPartnerId());
        products.forEach(product -> product.getProductImages().addAll(productImageRepository.findByProductId(product.getId())));

        Map<Long, String> productIdFileUrlMap = Product.makeProductIdfileUrlMap(products, fileUtilsService);
        return GetProductsResponse.from(products, productIdFileUrlMap);
    }

    @Override
    public GetProductResponse getProduct(Long productId) {
        Product product = this.findById(productId);
        product.getProductImages().addAll(productImageService.findByProductId(product.getId()));

        // 내 상품인지 확인
        if (!product.isMyProduct(authService.getPartnerId())) {
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

    public List<OptionResponse> getOptions(Long productId) {

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

    @Transactional
    @Override
    public ProductResponse.Create createProduct(ProductRequest.Create request) {

        // 여러 개의 대표 이미지가 있는 경우
        if (request.hasMultiRepresentativeImages()) {
            throw new MultiRepresentativeImageException();
        }

        // 외부 파일 소스가 있는 경우
        if (request.containsExternalFileSources(apiProperties.getTourStream().getHost())) {
            throw new ExternalFileSourceException();
        }

        List<String> storedNames = fileUtilsService.extractStoredFilenameFrom(request.getProductImageUrls());
        List<ProductImage> productImages = productImageService.findByStoredNameIn(storedNames);
        productImageService.updateRepresentative(productImages, request.getProductImages());

        Partner partner = partnerService.getById(authService.getPartnerId());
        Product product = Product.from(request, partner, productImages);
        product = productRepository.create(product);

        for (ProductImage productImage : productImages) {
            productImage.addProduct(product);
            productImageRepository.update(productImage);
        }

        // 옵션 저장
        saveOptions(request.getOptions(), product.getId());

        return new ProductResponse.Create(product.getId());
    }

    @Transactional
    @Override
    public void closeSales(Long productId) {
        Product product = productRepository.findById(productId);

        // 이미 판매 종료된 상품인지 확인
        if (product.isClosed()) {
            throw new ProductAlreadyClosedException(productId);
        }

        product = product.closeSales();
        productRepository.update(product);
    }

    @Transactional
    @Override
    public ProductResponse.Update update(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId);
        Partner partner = partnerService.getById(authService.getPartnerId());

        // 내 상품인지 확인
        if (!partner.isMyProduct(product)) {
            throw new AccessDeniedException();
        }

        // RefundDetail 업데이트 시 기존 데이터는 다 삭제하고 새로운 데이터로 업데이트
        refundDetailService.deleteByProduct_Id(product.getId());

        // 대표 상품 수정
        productImageService.updateRepresentative(product.getProductImages(), request.getProductImages());
        List<ProductImage> addedProductImages = productImageService.findByStoredNameIn(request.getProductImageUrls());

        Product updatedProduct = product.updateProduct(request, addedProductImages);

        productRepository.update(updatedProduct);

        return new ProductResponse.Update(updatedProduct.getId());
    }

    @Transactional
    @Override
    public Long deepCopyProduct(Long productId) {
        Product product = productRepository.findById(productId);
        product.getProductImages().addAll(productImageRepository.findByProductId(product.getId()));
        product.getRefundDetails().addAll(refundDetailService.findByProductId(product.getId()));
        product.getOptions().addAll(optionRepository.findByProductId(product.getId()));

        // 내 상품인지 확인
        if (!product.isMyProduct(authService.getPartnerId())) {
            throw new AccessDeniedException();
        }

        Product deepCopyProduct = product.deepCopyProduct();
        deepCopyProduct = productRepository.create(deepCopyProduct);

        // 상품 이미지 복사
        List<ProductImage> newProductImages = ProductImage.copyRealImages(product.getProductImages(), uuidHolder, clockHolder, storageService);
        for (ProductImage newProductImage : newProductImages) {
            newProductImage.addProduct(deepCopyProduct);
            productImageRepository.update(newProductImage);
        }
        return deepCopyProduct.getId();
    }

    @Override
    public List<GetProductNameResponse> getProductNames() {
        List<Product> products = productRepository.findByPartnerId(authService.getPartnerId()).stream()
                .sorted(comparing(Product::getName))
                .toList();

        // set options
        products.forEach(product -> product.getOptions().addAll(optionRepository.findByProductId(product.getId())));

        // set reservations
        products.stream().flatMap(product -> product.getOptions().stream())
                .forEach(option -> option.getReservations().addAll(reservationRepository.findByOptionId(option.getId())));

        return GetProductNameResponse.from(products);
    }

    @Transactional
    public void saveOptions(List<OptionRequest> optionRequests, Long productId) {
        List<OptionEntity> options = new ArrayList<>();

        ProductEntity product = null; // productService.findById(productId);

        Map<String, ExchangeRate> exchangeMap = getExchangeRateMap();


        optionRequests.forEach(request -> {
            // 상위 옵션
            // 하위 옵션에 상위 옵션을 넣어줘야 하기 때문에 먼저 상위 옵션을 저장한다.
            ExchangeRate siteCurrency = exchangeMap.get(request.getSiteCurrency());
            ExchangeRate platformCurrency = exchangeMap.get(request.getPlatformCurrency());

            OptionEntity parentOption = optionService.saveOption(OptionEntity.createOption(product, request, null, siteCurrency, platformCurrency));

            // 하위 옵션
            if (request.getSubOptions() != null) {
                request.getSubOptions().forEach(sub -> {
                    ExchangeRate subSiteCurrency = exchangeMap.get(sub.getSiteCurrency());
                    ExchangeRate subPlatformCurrency = exchangeMap.get(sub.getPlatformCurrency());

                    options.add(OptionEntity.createOption(product, sub, parentOption, subSiteCurrency, subPlatformCurrency));
                });
            }
        });

        optionService.saveSubOptions(options);
    }

    @Override
    public void deleteProductImages(Long partnerId, List<Long> productImageIds) {
        List<ProductImage> productImages = productImageService.findByIdIn(productImageIds);

        List<Long> partnerIds = productImages.stream()
                .map(ProductImage::getProduct)
                .filter(Objects::nonNull)
                .map(Product::getPartner)
                .map(Partner::getId)
                .distinct()
                .toList();

        // 내 상품인지 확인
        if (partnerIds.size() > 1 || (!partnerIds.isEmpty() && !partnerIds.contains(partnerId))) {
            throw new AccessDeniedException();
        }

        productImages.forEach(productImage ->
                storageService.deleteImage(productImage.getFilePath(), productImage.getStoredName())
        );
        productImageService.deleteAllById(productImageIds);
    }

    // ====== 내부 메소드 ====== //
    private Map<String, ExchangeRate> getExchangeRateMap() {
        List<ExchangeRate> exchangeRateList = exchangeRateService.findAll();
        Map<String, ExchangeRate> exchangeMap = exchangeRateList.stream()
                .collect(Collectors.toMap(ExchangeRate::getCurrencyCode, exchangeRate -> exchangeRate, (oldValue, newValue) -> newValue)); // 같은 key 값이 있을 수 있으므로 마지막에 있는 값으로 덮어씌운다.
        return exchangeMap;
    }
}
