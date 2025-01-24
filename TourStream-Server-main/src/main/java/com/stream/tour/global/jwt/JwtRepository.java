package com.stream.tour.global.jwt;

import com.stream.tour.global.jwt.entity.JwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<JwtEntity, Long> {

    Optional<JwtEntity> findByPartnerId(Long partnerId);
    Optional<JwtEntity> findByAccessToken(String accessToken);
}
