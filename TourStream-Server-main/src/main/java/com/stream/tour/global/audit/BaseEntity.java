package com.stream.tour.global.audit;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity extends CreatedInfo {

    @Comment("수정 일자")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Comment("수정자")
    @LastModifiedBy
    private String updatedBy;
}
