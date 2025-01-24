package com.stream.tour.domain.option.entity;

import com.stream.tour.domain.exchangeRate.entity.ExchangeRate;
import com.stream.tour.domain.option.domain.Option;
import com.stream.tour.domain.option.dto.OptionRequest;
import com.stream.tour.domain.option.dto.OptionResponse;
import com.stream.tour.domain.option.dto.UpdateOptionRequest;
import com.stream.tour.domain.option.enums.ApplicationDay;
import com.stream.tour.domain.option.enums.SalesStatus;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.domain.reservations.infrastructure.entity.ReservationEntity;
import com.stream.tour.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "options")
@Entity
public class OptionEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Comment("옵션명")
    private String name;

    @Comment("재고 수량")
    private int stockQuantity;

    @Comment("판매 상태")
    @Enumerated(EnumType.STRING)
    private SalesStatus salesStatus;

    // 사이트 판매가 통화
    @Comment("사이트 판매가 통화")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_currency_id")
    private ExchangeRate siteCurrency;

    // 플랫폼 판매가 통화
    @Comment("플랫폼 판매가 통화")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platform_currency_id")
    private ExchangeRate platformCurrency;

    @Comment("사이트 판매가")
    private BigDecimal sitePrice;

    @Comment("플랫폼 판매가")
    private BigDecimal platformPrice;

    @Comment("판매 시작 일자")
    private LocalDate salesStartDate;

    @Comment("판매 마감 일자")
    private LocalDate salesEndDate;

    @Comment("적용 요일")
    @Enumerated(EnumType.STRING)
    private ApplicationDay applicationDay;

    @Comment("특정 일자")
    private LocalDate specificDate;

    // 같은 테이블 안에서 계층 구조 표현하기
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_option_id")
    private OptionEntity parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<OptionEntity> children = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "option", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReservationEntity> reservationEntities = new ArrayList<>();

    //==생성 메서드==//
    public static OptionEntity createOption(ProductEntity product, OptionRequest request, OptionEntity parentOption, ExchangeRate siteCurrencyId, ExchangeRate platformCurrencyId) {


        OptionEntity option = new OptionEntity();
        option.product = product;
        option.name = request.getName();
        option.stockQuantity = request.getStockQuantity();
        option.salesStatus = SalesStatus.valueOf(request.getSalesStatus());
        option.siteCurrency = siteCurrencyId;
        option.platformCurrency = platformCurrencyId;
        option.sitePrice = request.getSitePrice();
        option.platformPrice = request.getPlatformPrice();
        option.salesStartDate = request.getSalesStartDate();
        option.salesEndDate = request.getSalesEndDate();
        option.applicationDay = request.getApplicationDay() != null ? ApplicationDay.valueOf(request.getApplicationDay()) : null;
//        option.specificDate = LocalDate.parse(request.getSpecificDate());
        option.parent = parentOption != null ? parentOption : null;
        return option;
    }

    public static OptionEntity createOption(ProductEntity product, UpdateOptionRequest.Update request, OptionEntity parentOption, ExchangeRate siteCurrencyId, ExchangeRate platformCurrencyId) {


        OptionEntity option = new OptionEntity();
        option.product = product;
        option.name = request.getName();
        option.stockQuantity = request.getStockQuantity();
        option.salesStatus = SalesStatus.valueOf(request.getSalesStatus());
        option.siteCurrency = siteCurrencyId;
        option.platformCurrency = platformCurrencyId;
        option.sitePrice = request.getSitePrice();
        option.platformPrice = request.getPlatformPrice();
        option.salesStartDate = request.getSalesStartDate();
        option.salesEndDate = request.getSalesEndDate();
        option.applicationDay = request.getApplicationDay() != null ? ApplicationDay.valueOf(request.getApplicationDay()) : null;
//        option.specificDate = LocalDate.parse(request.getSpecificDate());
        option.parent = parentOption != null ? parentOption : null;
        return option;
    }

    public static OptionResponse toDto(OptionEntity option, String siteCurrencyNm, String platformCurrencyNm) {
        return OptionResponse.builder()
                .id(option.getId())
                .parentOptionId(option.getParent() != null ? option.getParent().getId() : null)
                .name(option.getName())
                .salesStatus(option.getSalesStatus().toString())
                .stockQuantity(option.getStockQuantity())
                .siteCurrency(siteCurrencyNm)
                .platformCurrency(platformCurrencyNm)
                .sitePrice(option.getSitePrice())
                .platformPrice(option.getPlatformPrice())
                .salesStartDate(option.getSalesStartDate())
                .salesEndDate(option.getSalesEndDate())
                .applicationDay(option.getApplicationDay() != null ? option.getApplicationDay().toString() : null)
                .specificDate(option.getSpecificDate())
                .build();
    }

    public void deepCopyOption(ProductEntity product) {
        this.id = null;
        this.product = product;
        this.reservationEntities.forEach(reservation -> reservation.deepCopyReservation(this));
    }

    public void updateOption(UpdateOptionRequest.Update request, ExchangeRate siteCurrencyId, ExchangeRate platformCurrencyId) {
        this.name = request.getName() ;
        this.stockQuantity = request.getStockQuantity();
        this.salesStatus = SalesStatus.valueOf(request.getSalesStatus());
        this.siteCurrency = siteCurrencyId;
        this.platformCurrency = platformCurrencyId;
        this.sitePrice = request.getSitePrice();
        this.platformPrice = request.getPlatformPrice();
        this.salesStartDate = request.getSalesStartDate();
        this.salesEndDate = request.getSalesEndDate();
        this.applicationDay = request.getApplicationDay() != null ?  ApplicationDay.valueOf(request.getApplicationDay()) : null;
        this.specificDate = request.getSpecificDate();
    }

    public Option toModel() {
        return Option.builder()
                .id(id)
                .product(product.toModel())
                .name(name)
                .stockQuantity(stockQuantity)
                .salesStatus(salesStatus)
                .siteCurrency(siteCurrency)
                .platformCurrency(platformCurrency)
                .sitePrice(sitePrice)
                .platformPrice(platformPrice)
                .salesStartDate(salesStartDate)
                .salesEndDate(salesEndDate)
                .applicationDay(applicationDay)
                .specificDate(specificDate)
                .parent(parent != null ? parent.toModel() : null)
                .children(children.stream().map(OptionEntity::toModel).toList())
                .build();
    }
}
