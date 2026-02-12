package com.noaats.eunchae.dto;

import com.noaats.eunchae.domain.ExpenseCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BudgetVsActual DTO 단위 테스트")
class BudgetVsActualTest {

    @Test
    @DisplayName("예산이 0일 때 퍼센티지는 0을 반환한다")
    void givenZeroBudget_whenGetPercentage_thenReturnZero() {
        // given
        BudgetVsActual bva = new BudgetVsActual(
                ExpenseCategory.FOOD, BigDecimal.ZERO, BigDecimal.valueOf(50000));

        // when
        int percentage = bva.getPercentage();

        // then
        assertThat(percentage).isZero();
    }

    @Test
    @DisplayName("100% 이상 사용 시 progressColor가 'over'를 반환한다")
    void givenOverBudget_whenGetProgressColor_thenReturnOver() {
        // given
        BudgetVsActual bva = new BudgetVsActual(
                ExpenseCategory.FOOD, BigDecimal.valueOf(200000), BigDecimal.valueOf(250000));

        // when
        String color = bva.getProgressColor();

        // then
        assertThat(color).isEqualTo("over");
    }

    @Test
    @DisplayName("80~99% 사용 시 progressColor가 'caution'을 반환한다")
    void givenCautionLevel_whenGetProgressColor_thenReturnCaution() {
        // given
        BudgetVsActual bva = new BudgetVsActual(
                ExpenseCategory.TRANSPORT, BigDecimal.valueOf(100000), BigDecimal.valueOf(85000));

        // when
        String color = bva.getProgressColor();

        // then
        assertThat(color).isEqualTo("caution");
    }

    @Test
    @DisplayName("80% 미만 사용 시 textColor가 'success'를 반환한다")
    void givenSafeLevel_whenGetTextColor_thenReturnSuccess() {
        // given
        BudgetVsActual bva = new BudgetVsActual(
                ExpenseCategory.SHOPPING, BigDecimal.valueOf(300000), BigDecimal.valueOf(100000));

        // when
        String color = bva.getTextColor();

        // then
        assertThat(color).isEqualTo("success");
    }

    @Test
    @DisplayName("예산과 실제 지출의 차이를 올바르게 계산한다")
    void givenBudgetAndActual_whenGetRemaining_thenReturnDifference() {
        // given
        BudgetVsActual bva = new BudgetVsActual(
                ExpenseCategory.EDUCATION, BigDecimal.valueOf(500000), BigDecimal.valueOf(300000));

        // when
        BigDecimal remaining = bva.getRemaining();

        // then
        assertThat(remaining).isEqualByComparingTo(BigDecimal.valueOf(200000));
    }
}
