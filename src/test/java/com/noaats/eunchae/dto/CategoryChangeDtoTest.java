package com.noaats.eunchae.dto;

import com.noaats.eunchae.domain.ExpenseCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CategoryChangeDto 단위 테스트")
class CategoryChangeDtoTest {

    @Test
    @DisplayName("지출이 증가했을 때 isIncrease가 true를 반환한다")
    void givenIncrease_whenCheckStatus_thenIsIncreaseTrue() {
        // given
        CategoryChangeDto dto = new CategoryChangeDto(
                ExpenseCategory.FOOD,
                BigDecimal.valueOf(200000),
                BigDecimal.valueOf(300000));

        // when & then
        assertThat(dto.isIncrease()).isTrue();
        assertThat(dto.isDecrease()).isFalse();
        assertThat(dto.getChange()).isEqualByComparingTo(BigDecimal.valueOf(100000));
        assertThat(dto.getChangeRate()).isEqualTo(50.0);
    }

    @Test
    @DisplayName("지출이 감소했을 때 isDecrease가 true를 반환한다")
    void givenDecrease_whenCheckStatus_thenIsDecreaseTrue() {
        // given
        CategoryChangeDto dto = new CategoryChangeDto(
                ExpenseCategory.TRANSPORT,
                BigDecimal.valueOf(100000),
                BigDecimal.valueOf(60000));

        // when & then
        assertThat(dto.isDecrease()).isTrue();
        assertThat(dto.isIncrease()).isFalse();
        assertThat(dto.getChange()).isEqualByComparingTo(BigDecimal.valueOf(-40000));
        assertThat(dto.getChangeRate()).isEqualTo(-40.0);
    }

    @Test
    @DisplayName("변화율이 5% 미만이면 isNeutral이 true를 반환한다")
    void givenSmallChange_whenCheckNeutral_thenIsNeutralTrue() {
        // given
        CategoryChangeDto dto = new CategoryChangeDto(
                ExpenseCategory.SHOPPING,
                BigDecimal.valueOf(100000),
                BigDecimal.valueOf(103000));

        // when & then
        assertThat(dto.isNeutral()).isTrue();
        assertThat(dto.getChangeRate()).isLessThan(5.0);
    }

    @Test
    @DisplayName("이전 금액이 0일 때 현재 금액이 있으면 변화율 100%, 없으면 0%를 반환한다")
    void givenZeroPrev_whenCalculateRate_thenReturn100OrZero() {
        // given
        CategoryChangeDto withCurrent = new CategoryChangeDto(
                ExpenseCategory.HEALTH,
                BigDecimal.ZERO,
                BigDecimal.valueOf(50000));

        CategoryChangeDto withoutCurrent = new CategoryChangeDto(
                ExpenseCategory.EDUCATION,
                BigDecimal.ZERO,
                BigDecimal.ZERO);

        // when & then
        assertThat(withCurrent.getChangeRate()).isEqualTo(100.0);
        assertThat(withoutCurrent.getChangeRate()).isEqualTo(0.0);
    }
}
