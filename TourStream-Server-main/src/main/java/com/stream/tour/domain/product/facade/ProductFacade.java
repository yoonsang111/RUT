package com.stream.tour.domain.product.facade;

import com.stream.tour.domain.file.dto.UploadFilesResponse;
import com.stream.tour.domain.option.dto.OptionResponse;
import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.option.service.OptionService;
import com.stream.tour.domain.product.domain.ProductImage;
import com.stream.tour.domain.product.service.ProductImageService;
import com.stream.tour.global.enums.Directory;
import com.stream.tour.global.enums.FileType;
import com.stream.tour.global.service.ClockHolder;
import com.stream.tour.global.service.UuidHolder;
import com.stream.tour.global.storage.StoredImageInfo;
import com.stream.tour.global.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Component
public class ProductFacade {

    private final StorageService storageService;
    private final ProductImageService productImageService;
    private final OptionService optionService;
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;

    public List<UploadFilesResponse> upload(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            if (file.getOriginalFilename().length() > 200) {
                throw new IllegalArgumentException("파일명은 200자 이하로 입력해주세요.");
            }
        }

        List<StoredImageInfo> storedImageInfos = storageService.storeImages(files, FileType.IMAGE, Directory.PRODUCT, uuidHolder, clockHolder);
        List<ProductImage> productImages = productImageService.saveAll(ProductImage.of(storedImageInfos));

        return storedImageInfos.stream()
                .map(storedImageInfo -> new UploadFilesResponse(storedImageInfo, productImages))
                .toList();
    }

    // ===== 옵션 로직 ===== //
    @Transactional(readOnly = true)
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
}
