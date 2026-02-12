package com.noaats.eunchae.service;

import com.noaats.eunchae.domain.Budget;
import com.noaats.eunchae.domain.ExpenseCategory;
import com.noaats.eunchae.dto.BudgetVsActual;
import com.noaats.eunchae.dto.DashboardSummary;
import com.noaats.eunchae.repository.BudgetRepository;
import com.noaats.eunchae.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("BudgetService 단위 테스트")
class BudgetServiceTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private BudgetService budgetService;

    private String yearMonth;

    @BeforeEach
    void setUp() {
        yearMonth = "2026-02";
    }

    @Test
    @DisplayName("월별 데이터가 있을 때 대시보드 요약을 올바르게 반환한다")
    void givenMonthlyData_whenGetDashboardSummary_thenReturnCorrectSums() {
        // given
        given(expenseRepository.sumByYearMonth(yearMonth)).willReturn(BigDecimal.valueOf(500000));
        given(expenseRepository.countByYearMonth(yearMonth)).willReturn(15L);

        Budget foodBudget = Budget.builder()
                .category(ExpenseCategory.FOOD)
                .amount(BigDecimal.valueOf(300000))
                .yearMonth(yearMonth)
                .build();
        Budget transportBudget = Budget.builder()
                .category(ExpenseCategory.TRANSPORT)
                .amount(BigDecimal.valueOf(200000))
                .yearMonth(yearMonth)
                .build();
        given(budgetRepository.findByYearMonth(yearMonth))
                .willReturn(List.of(foodBudget, transportBudget));

        // when
        DashboardSummary summary = budgetService.getDashboardSummary(yearMonth);

        // then
        assertThat(summary.getTotalExpense()).isEqualByComparingTo(BigDecimal.valueOf(500000));
        assertThat(summary.getTotalBudget()).isEqualByComparingTo(BigDecimal.valueOf(500000));
        assertThat(summary.getRemaining()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(summary.getTransactionCount()).isEqualTo(15);
    }

    @Test
    @DisplayName("데이터가 없을 때 대시보드 요약이 0을 반환한다")
    void givenNoData_whenGetDashboardSummary_thenReturnZeroes() {
        // given
        given(expenseRepository.sumByYearMonth(yearMonth)).willReturn(BigDecimal.ZERO);
        given(expenseRepository.countByYearMonth(yearMonth)).willReturn(0L);
        given(budgetRepository.findByYearMonth(yearMonth)).willReturn(Collections.emptyList());

        // when
        DashboardSummary summary = budgetService.getDashboardSummary(yearMonth);

        // then
        assertThat(summary.getTotalExpense()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(summary.getTotalBudget()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(summary.getTransactionCount()).isZero();
    }

    @Test
    @DisplayName("예산 vs 실제 조회 시 모든 카테고리에 대해 결과를 반환한다")
    void givenMixedCategories_whenGetBudgetVsActual_thenReturnAllCategories() {
        // given
        List<Object[]> categoryExpenses = Arrays.<Object[]>asList(
                new Object[]{ExpenseCategory.FOOD, BigDecimal.valueOf(250000)},
                new Object[]{ExpenseCategory.TRANSPORT, BigDecimal.valueOf(80000)}
        );
        given(expenseRepository.sumByCategory(yearMonth)).willReturn(categoryExpenses);

        Budget foodBudget = Budget.builder()
                .category(ExpenseCategory.FOOD)
                .amount(BigDecimal.valueOf(300000))
                .yearMonth(yearMonth)
                .build();
        given(budgetRepository.findByYearMonth(yearMonth)).willReturn(List.of(foodBudget));

        // when
        List<BudgetVsActual> result = budgetService.getBudgetVsActual(yearMonth);

        // then
        assertThat(result).hasSize(ExpenseCategory.values().length);

        BudgetVsActual food = result.stream()
                .filter(b -> b.getCategory() == ExpenseCategory.FOOD)
                .findFirst().orElseThrow();
        assertThat(food.getBudgetAmount()).isEqualByComparingTo(BigDecimal.valueOf(300000));
        assertThat(food.getActualAmount()).isEqualByComparingTo(BigDecimal.valueOf(250000));
    }

    @Test
    @DisplayName("80% 이상 사용한 카테고리만 알림으로 반환한다")
    void givenHighSpending_whenGetAlerts_thenReturnOver80Percent() {
        // given
        List<Object[]> categoryExpenses = Arrays.<Object[]>asList(
                new Object[]{ExpenseCategory.FOOD, BigDecimal.valueOf(270000)},
                new Object[]{ExpenseCategory.TRANSPORT, BigDecimal.valueOf(30000)}
        );
        given(expenseRepository.sumByCategory(yearMonth)).willReturn(categoryExpenses);

        Budget foodBudget = Budget.builder()
                .category(ExpenseCategory.FOOD)
                .amount(BigDecimal.valueOf(300000))
                .yearMonth(yearMonth)
                .build();
        Budget transportBudget = Budget.builder()
                .category(ExpenseCategory.TRANSPORT)
                .amount(BigDecimal.valueOf(100000))
                .yearMonth(yearMonth)
                .build();
        given(budgetRepository.findByYearMonth(yearMonth))
                .willReturn(List.of(foodBudget, transportBudget));

        // when
        List<BudgetVsActual> alerts = budgetService.getAlerts(yearMonth);

        // then
        assertThat(alerts).hasSize(1);
        assertThat(alerts.get(0).getCategory()).isEqualTo(ExpenseCategory.FOOD);
        assertThat(alerts.get(0).getPercentage()).isGreaterThanOrEqualTo(80);
    }

    @Test
    @DisplayName("사용률이 낮으면 알림이 비어있다")
    void givenLowSpending_whenGetAlerts_thenReturnEmpty() {
        // given
        List<Object[]> categoryExpenses = Arrays.<Object[]>asList(
                new Object[]{ExpenseCategory.FOOD, BigDecimal.valueOf(10000)}
        );
        given(expenseRepository.sumByCategory(yearMonth)).willReturn(categoryExpenses);

        Budget foodBudget = Budget.builder()
                .category(ExpenseCategory.FOOD)
                .amount(BigDecimal.valueOf(300000))
                .yearMonth(yearMonth)
                .build();
        given(budgetRepository.findByYearMonth(yearMonth)).willReturn(List.of(foodBudget));

        // when
        List<BudgetVsActual> alerts = budgetService.getAlerts(yearMonth);

        // then
        assertThat(alerts).isEmpty();
    }

    @Test
    @DisplayName("기존 예산이 있으면 금액을 업데이트한다")
    void givenExistingBudget_whenSaveBudgets_thenUpdateAmount() {
        // given
        Budget existing = Budget.builder()
                .category(ExpenseCategory.FOOD)
                .amount(BigDecimal.valueOf(200000))
                .yearMonth(yearMonth)
                .build();
        given(budgetRepository.findByCategoryAndYearMonth(ExpenseCategory.FOOD, yearMonth))
                .willReturn(Optional.of(existing));

        Map<ExpenseCategory, BigDecimal> amounts = new HashMap<>();
        amounts.put(ExpenseCategory.FOOD, BigDecimal.valueOf(350000));

        // when
        budgetService.saveBudgets(yearMonth, amounts);

        // then
        assertThat(existing.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(350000));
    }

    @Test
    @DisplayName("새 카테고리 예산이면 새로 생성한다")
    void givenNewBudget_whenSaveBudgets_thenCreateNew() {
        // given
        given(budgetRepository.findByCategoryAndYearMonth(ExpenseCategory.SHOPPING, yearMonth))
                .willReturn(Optional.empty());
        given(budgetRepository.save(any(Budget.class))).willAnswer(invocation -> invocation.getArgument(0));

        Map<ExpenseCategory, BigDecimal> amounts = new HashMap<>();
        amounts.put(ExpenseCategory.SHOPPING, BigDecimal.valueOf(150000));

        // when
        budgetService.saveBudgets(yearMonth, amounts);

        // then
        then(budgetRepository).should().save(any(Budget.class));
    }

    @Test
    @DisplayName("null 또는 음수 금액은 0으로 설정한다")
    void givenNullOrNegativeAmount_whenSaveBudgets_thenSetToZero() {
        // given
        Budget existing = Budget.builder()
                .category(ExpenseCategory.FOOD)
                .amount(BigDecimal.valueOf(200000))
                .yearMonth(yearMonth)
                .build();
        given(budgetRepository.findByCategoryAndYearMonth(ExpenseCategory.FOOD, yearMonth))
                .willReturn(Optional.of(existing));

        Map<ExpenseCategory, BigDecimal> amounts = new HashMap<>();
        amounts.put(ExpenseCategory.FOOD, BigDecimal.valueOf(-5000));

        // when
        budgetService.saveBudgets(yearMonth, amounts);

        // then
        assertThat(existing.getAmount()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("이전 달 예산이 있으면 현재 달로 복사한다")
    void givenPreviousMonthExists_whenCopyFromPreviousMonth_thenCopyAll() {
        // given
        String prevMonth = "2026-01";
        Budget prevBudget = Budget.builder()
                .category(ExpenseCategory.FOOD)
                .amount(BigDecimal.valueOf(300000))
                .yearMonth(prevMonth)
                .build();
        given(budgetRepository.findByYearMonth(prevMonth)).willReturn(List.of(prevBudget));
        given(budgetRepository.findByCategoryAndYearMonth(any(), any())).willReturn(Optional.empty());
        given(budgetRepository.save(any(Budget.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        budgetService.copyFromPreviousMonth(yearMonth);

        // then
        then(budgetRepository).should().save(any(Budget.class));
    }

    @Test
    @DisplayName("이전 달 예산이 없으면 예외를 던진다")
    void givenNoPreviousMonth_whenCopyFromPreviousMonth_thenThrowException() {
        // given
        given(budgetRepository.findByYearMonth("2026-01")).willReturn(Collections.emptyList());

        // when & then
        assertThatThrownBy(() -> budgetService.copyFromPreviousMonth(yearMonth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이전 달");
    }
}
