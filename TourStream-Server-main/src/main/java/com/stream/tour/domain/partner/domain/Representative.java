package com.stream.tour.domain.partner.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stream.tour.domain.partner.dto.request.PartnerRequest;
import com.stream.tour.domain.partner.infrastructure.entity.PartnerEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Representative {

    private Long id;
    private String name;
    private int sortOrder;
    private Partner partner;
}
