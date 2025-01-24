package com.stream.tour.domain.option.entity;

import com.stream.tour.domain.option.domain.OptionHistory;
import com.stream.tour.domain.option.enums.ApplicationDay;
import com.stream.tour.domain.option.enums.SalesStatus;
import com.stream.tour.global.audit.CreatedInfo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "option_history")
public class OptionHistoryEntity extends CreatedInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_history_id")
    private Long id;

    private Long optionId;

    private Long productId;

    private String name;

    private int stockQuantity;

    @Enumerated(EnumType.STRING)
    private SalesStatus salesStatus;

    private int siteCurrencyId;

    private int platformCurrencyId;

    private BigDecimal sitePrice;

    private BigDecimal platformPrice;

    private LocalDate salesStartDate;

    private LocalDate salesEndDate;

    @Enumerated(EnumType.STRING)
    private ApplicationDay applicationDay;

    private LocalDate specificDate;

    @Comment("옵션 수정 버전(한번의 수정에 대한 버전을 기록")
    private Long version;

    public static OptionHistoryEntity from(OptionHistory optionHistory) {
        OptionHistoryEntity optionHistoryEntity = new OptionHistoryEntity();
        optionHistoryEntity.id = optionHistory.getId();
        optionHistoryEntity.optionId  = optionHistory.getOptionId();
        optionHistoryEntity.name = optionHistory.getName();
        optionHistoryEntity.stockQuantity = optionHistory.getStockQuantity();
        optionHistoryEntity.salesStatus = optionHistory.getSalesStatus();
        optionHistoryEntity.productId = optionHistory.getProductId();
        optionHistoryEntity.applicationDay = optionHistory.getApplicationDay();
        optionHistoryEntity.platformCurrencyId = optionHistory.getPlatformCurrencyId();
        optionHistoryEntity.platformPrice = optionHistory.getPlatformPrice();
        optionHistoryEntity.siteCurrencyId = optionHistory.getSiteCurrencyId();
        optionHistoryEntity.sitePrice = optionHistory.getSitePrice();
        optionHistoryEntity.salesStartDate = optionHistory.getSalesStartDate();
        optionHistoryEntity.salesEndDate = optionHistory.getSalesEndDate();
        optionHistoryEntity.specificDate = optionHistory.getSpecificDate();
        optionHistoryEntity.version = optionHistory.getVersion();

        return optionHistoryEntity;
    }

    public static List<OptionHistoryEntity> from(List<OptionHistory> optionHistories) {
        return optionHistories.stream()
                .map(OptionHistoryEntity::from)
                .collect(Collectors.toList());
    }

    public OptionHistory toModel() {
        return OptionHistory.builder()
                .id(id)
                .optionId(optionId)
                .productId(productId)
                .name(name)
                .stockQuantity(stockQuantity)
                .salesStatus(salesStatus)
                .applicationDay(applicationDay)
                .siteCurrencyId(siteCurrencyId)
                .sitePrice(sitePrice)
                .platformCurrencyId(platformCurrencyId)
                .platformPrice(platformPrice)
                .salesStartDate(salesStartDate)
                .salesEndDate(salesEndDate)
                .specificDate(specificDate)
                .version(version)
                .build();
    }

}
