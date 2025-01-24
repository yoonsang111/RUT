package com.stream.tour.domain.test.repository;

import com.stream.tour.domain.test.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {

}
