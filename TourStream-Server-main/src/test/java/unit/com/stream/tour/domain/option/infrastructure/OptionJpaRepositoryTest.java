package com.stream.tour.domain.option.infrastructure;

import com.stream.tour.common.TestConfig;
import com.stream.tour.common.utils.OptionTestUtils;
import com.stream.tour.common.utils.ProductTestUtils;
import com.stream.tour.domain.option.entity.OptionEntity;
import com.stream.tour.domain.product.infrastructure.entity.ProductEntity;
import com.stream.tour.global.audit.AuditAwareImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({AuditAwareImpl.class, TestConfig.class})
class OptionJpaRepositoryTest {

    @Autowired
    OptionJpaRepository optionJpaRepository;

    @DisplayName("상품 아이디와 부모 옵션 아이디로 옵션을 조회할 수 있다.")
    @Test
    void findByProduct_IdAndAndParent_Id() {
        ProductEntity product = ProductTestUtils.createProduct(null, null, null);
        ReflectionTestUtils.setField(product, "id", 1L);

        OptionEntity parentOption = OptionTestUtils.createOption(product, null);
        optionJpaRepository.save(parentOption);

        // 자식 옵션을 생성하고 저장
        OptionEntity option = OptionTestUtils.createOption(product, null);
        ReflectionTestUtils.setField(option, "parent", parentOption);
        optionJpaRepository.save(option);


        // 부모 옵션 아이디로 자식 옵션을 조회해서 빈 값이 아닌지 확인
        List<OptionEntity> options = optionJpaRepository.findByProduct_IdAndAndParent_Id(product.getId(), parentOption.getId());
        assertThat(options).isNotEmpty();
    }

    @DisplayName("옵션 아이디 리스트로 옵션을 삭제할 수 있다.")
    @Test
    void deleteAllByIdIn() {
        ProductEntity product = ProductTestUtils.createProduct(null, null, null);
        ReflectionTestUtils.setField(product, "id", 1L);

        IntStream.rangeClosed(1, 3).forEach(i -> {
            OptionEntity option = OptionTestUtils.createOption(product, null);
            ReflectionTestUtils.setField(option, "id", (long) i);
            optionJpaRepository.save(option);
        });

        List<Long> optionIds = Arrays.asList(1L, 2L, 3L);
        optionJpaRepository.deleteAllByIdIn(optionIds);

        OptionEntity option = optionJpaRepository.findById(1L).orElse(null);
        assertThat(option).isNull();
    }
}