package com.stream.tour.common;

import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

public class JpaTestAudit {

    public static void setBaseEntity(Object entity) {
        ReflectionTestUtils.setField(entity, "createdBy", "ADMIN");
        ReflectionTestUtils.setField(entity, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(entity, "updatedBy", "ADMIN");
        ReflectionTestUtils.setField(entity, "updatedAt", LocalDateTime.now());
    }

    public static void setCreatedInfo(Object entity) {
        ReflectionTestUtils.setField(entity, "createdBy", "ADMIN");
        ReflectionTestUtils.setField(entity, "createdAt", LocalDateTime.now());
    }
}
