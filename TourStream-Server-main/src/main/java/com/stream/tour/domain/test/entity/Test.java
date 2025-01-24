package com.stream.tour.domain.test.entity;

import jakarta.persistence.*;

@Entity
public class Test {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long id;

}
