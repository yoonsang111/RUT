//package com.stream.tour.domain.option.entity;
//
//import com.stream.tour.domain.option.enums.ApplicationDay;
//import com.stream.tour.domain.option.enums.SalesStatus;
//import com.stream.tour.global.audit.CreatedInfo;
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.Comment;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//@Builder(access = AccessLevel.PRIVATE)
//@Getter
//@Entity
//public class OptionHistory extends CreatedInfo {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "option_history_id")
//    private Long id;
//
//    private Long optionId;
//
//    private Long productId;
//
//    private String name;
//
//    private int stockQuantity;
//
//    @Enumerated(EnumType.STRING)
//    private SalesStatus salesStatus;
//
//    private int siteCurrencyId;
//
//    private int platformCurrencyId;
//
//    private BigDecimal sitePrice;
//
//    private BigDecimal platformPrice;
//
//    private LocalDate salesStartDate;
//
//    private LocalDate salesEndDate;
//
//    @Enumerated(EnumType.STRING)
//    private ApplicationDay applicationDay;
//
//    private LocalDate specificDate;
//
//    @Comment("옵션 수정 버전(한번의 수정에 대한 버전을 기록")
//    private Long version;
//
//    public static OptionHistory createOptionHistory(OptionEntity option, long version) {
//        return OptionHistory.builder()
//                .optionId(option.getId())
//                .productId(option.getProduct().getId())
//                .name(option.getName())
//                .stockQuantity(option.getStockQuantity())
//                .salesStatus(option.getSalesStatus())
//                .siteCurrencyId(option.getSiteCurrency().getId())
//                .sitePrice(option.getSitePrice())
//                .platformCurrencyId(option.getPlatformCurrency().getId())
//                .platformPrice(option.getPlatformPrice())
//                .salesStartDate(option.getSalesStartDate())
//                .salesEndDate(option.getSalesEndDate())
//                .applicationDay(option.getApplicationDay())
//                .specificDate(option.getSpecificDate())
//                .version(version)
//                .build();
//    }
//}
