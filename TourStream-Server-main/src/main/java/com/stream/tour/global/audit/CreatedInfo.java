package com.stream.tour.global.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CreatedInfo {

    @Comment("등록 일자")
    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Comment("등록자")
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
}
