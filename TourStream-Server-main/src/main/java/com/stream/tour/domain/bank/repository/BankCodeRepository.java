package com.stream.tour.domain.bank.repository;

import com.stream.tour.domain.bank.entity.BankCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankCodeRepository extends JpaRepository<BankCode, Integer> {
}
