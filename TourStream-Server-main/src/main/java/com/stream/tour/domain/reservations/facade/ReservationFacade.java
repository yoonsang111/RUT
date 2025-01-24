package com.stream.tour.domain.reservations.facade;

import com.stream.tour.domain.option.domain.Option;
import com.stream.tour.domain.option.service.OptionService;
import com.stream.tour.domain.partner.domain.Partner;
import com.stream.tour.domain.partner.service.PartnerService;
import com.stream.tour.domain.product.domain.Product;
import com.stream.tour.domain.product.service.ProductService;
import com.stream.tour.domain.reservations.domain.Reservation;
import com.stream.tour.domain.reservations.dto.*;
import com.stream.tour.domain.reservations.service.ExcelFileService;
import com.stream.tour.domain.reservations.service.ReservationService;
import com.stream.tour.global.exception.custom.children.NotMyProductException;
import com.stream.tour.global.exception.custom.children.NotMyReservationException;
import com.stream.tour.global.service.ClockHolder;
import com.stream.tour.global.storage.service.StorageService;
import com.stream.tour.global.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static com.stream.tour.global.utils.CollectorsUtil.getIdsFrom;
import static com.stream.tour.global.utils.CollectorsUtil.groupBy;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReservationFacade {

    private final PartnerService partnerService;
    private final ProductService productService;
    private final OptionService optionService;
    private final ReservationService reservationService;
    private final ExcelFileService excelFileService;
    private final StorageService storageService;
    private final ClockHolder clockHolder;

    public List<FindReservationStatusListResponse> findReservationStatusList(Long partnerId, FindReservationStatusRequest request) {
        List<Product> products = productService.findByProductIds(request.getProductIds());

        // set options
        List<Option> options = optionService.findByProductIdIn(request.getProductIds());
        Map<Long, List<Option>> optionsByProductId = groupBy(options, (Option option) -> option.getProduct().getId());
        products.forEach(product -> product.getOptions().addAll(optionsByProductId.get(product.getId())));

        // set reservations
        List<Reservation> reservations = reservationService.findByOptionIdInAndReservationStartDateBetween(
                getIdsFrom(options, Option::getId),
                request.getStartDate(),
                request.getNumberOfDaysToFetch());
        Map<Long, List<Reservation>> reservationsByOptionId = groupBy(reservations, (Reservation reservation) -> reservation.getOption().getId());
        options.forEach(option -> option.getReservations().addAll(reservationsByOptionId.get(option.getId())));

        // this is security check
        Partner partner = partnerService.getById(partnerId);
        if (!partner.isMyProduct(products)) {
            throw new NotMyProductException(partner, products);
        }

        return products.stream()
                .map(FindReservationStatusListResponse::new)
                .toList();
    }

    public List<FindReservationStatusResponse> findReservationStatus(Long partnerId, List<Long> reservationIds) {
        List<Reservation> reservations = reservationService.findByIdIn(reservationIds);

        // this is security check
        Partner partner = partnerService.getById(partnerId);
        if (!partner.isMyReservation(reservations)) {
            throw new NotMyReservationException(partner, reservations);
        }

        return reservations.stream()
                .map(FindReservationStatusResponse::new)
                .toList();
    }

    @Transactional
    public Long updateReservationDate(Long partnerId, Long reservationId, UpdateReservationDateRequest request) {
        Partner partner = partnerService.getById(partnerId);
        Reservation reservation = reservationService.findById(reservationId);

        // this is security check
        if (!partner.isMyReservation(reservation)) {
            throw new NotMyReservationException(partner, reservation);
        }

        return reservationService.updateReservationDate(reservationId, request);
    }

    public List<FindReservationResponse> findReservations(Long partnerId, FindReservationRequest request, Pageable pageable) {
        return reservationService.findByIdIn(partnerId, request, pageable);
    }

    @Transactional
    public void uploadExcelFile(Long reservationId, MultipartFile excelFile) {

        String fileExtension = excelFile.getOriginalFilename().substring(excelFile.getOriginalFilename().lastIndexOf(".") + 1);

        boolean isValid = FileUtil.isExcelFile(fileExtension);

        // TODO 커스텀 예외로 변경
        if (!isValid) {
            throw new IllegalStateException("엑셀 파일만 업로드 가능합니다.");
        }

        String originalFilename = excelFile.getOriginalFilename().substring(0, excelFile.getOriginalFilename().lastIndexOf("."));
        String storedFileName = FileUtil.getUuid();

        Path filePath = storageService.storeExcelFile(excelFile, storedFileName, clockHolder);

        ExcelFile excel = ExcelFile.createExcelFile(reservationId, originalFilename, storedFileName, fileExtension, filePath.toString());

        excelFileService.uploadExcelFile(excel);
    }


}