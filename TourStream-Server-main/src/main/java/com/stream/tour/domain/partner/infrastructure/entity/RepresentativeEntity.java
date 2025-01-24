package com.stream.tour.domain.partner.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stream.tour.domain.partner.domain.Representative;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "representative")
public class RepresentativeEntity {

    @JsonIgnore
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "representative_id")
    private Long id;

    @Comment("대표자 명")
    @Column(nullable = false)
    private String name;

    @Comment("정렬 순서")
    @Column(nullable = false)
    private int sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private PartnerEntity partner;

    //==생성 메서드==//
    public static List<RepresentativeEntity> from(List<Representative> representatives) {
        return representatives.stream()
                .map(r -> from(r.getId(), r.getName(), r.getSortOrder()))
                .toList();
    }

    public static RepresentativeEntity from(Long id, String name, int sortOrder) {
        return RepresentativeEntity.builder()
                .id(id)
                .name(name)
                .sortOrder(sortOrder)
                .build();
    }

    //==연관 관계 메서드==//
    public void addPartner(PartnerEntity partner) {
        this.partner = partner;
    }

    public Representative toModel() {
        return Representative.builder()
                .id(id)
                .name(name)
                .sortOrder(sortOrder)
                .partner(partner.toModel())
                .build();
    }
}
