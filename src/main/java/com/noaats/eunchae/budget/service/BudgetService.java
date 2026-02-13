package com.noaats.eunchae.budget.service;

import com.noaats.eunchae.budget.domain.Budget;
import com.noaats.eunchae.expense.domain.ExpenseCategory;
import com.noaats.eunchae.pattern.dto.BudgetVsActual;
import com.noaats.eunchae.dashboard.dto.DashboardSummary;
import com.noaats.eunchae.budget.repository.BudgetRepository;
import com.noaats.eunchae.expense.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;

    // 대시보드 요약
    public DashboardSummary getDashboardSummary(String yearMonth) {
        log.debug("대시보드 요약 조회 - yearMonth: {}", yearMonth);
        BigDecimal totalExpense = expenseRepository.sumByYearMonth(yearMonth);
        BigDecimal totalBudget = budgetRepository.findByYearMonth(yearMonth).stream()
                .map(Budget::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal remaining = totalBudget.subtract(totalExpense);
        long count = expenseRepository.countByYearMonth(yearMonth);

        log.debug("대시보드 요약 - 총지출: {}, 총예산: {}, 잔여: {}, 건수: {}",
                totalExpense, totalBudget, remaining, count);
        return new DashboardSummary(totalExpense, totalBudget, remaining, count);
    }

    // 예산 vs 실제 (카테고리별)
    public List<BudgetVsActual> getBudgetVsActual(String yearMonth) {
        // 카테고리별 실제 지출
        Map<ExpenseCategory, BigDecimal> actualMap = new HashMap<>();
        for (Object[] row : expenseRepository.sumByCategory(yearMonth)) {
            actualMap.put((ExpenseCategory) row[0], (BigDecimal) row[1]);
        }

        // 예산 목록
        Map<ExpenseCategory, BigDecimal> budgetMap = new HashMap<>();
        for (Budget b : budgetRepository.findByYearMonth(yearMonth)) {
            budgetMap.put(b.getCategory(), b.getAmount());
        }

        // 모든 카테고리에 대해 BudgetVsActual 생성
        List<BudgetVsActual> result = new ArrayList<>();
        for (ExpenseCategory cat : ExpenseCategory.values()) {
            BigDecimal budget = budgetMap.getOrDefault(cat, BigDecimal.ZERO);
            BigDecimal actual = actualMap.getOrDefault(cat, BigDecimal.ZERO);
            result.add(new BudgetVsActual(cat, budget, actual));
        }
        return result;
    }

    // 예산 초과/근접 알림 목록
    public List<BudgetVsActual> getAlerts(String yearMonth) {
        return getBudgetVsActual(yearMonth).stream()
                .filter(bva -> bva.getBudgetAmount().compareTo(BigDecimal.ZERO) > 0)
                .filter(bva -> bva.getPercentage() >= 80)
                .sorted((a, b) -> Integer.compare(b.getPercentage(), a.getPercentage()))
                .toList();
    }

    // 예산 저장 (일괄)
    @Transactional
    public void saveBudgets(String yearMonth, Map<ExpenseCategory, BigDecimal> amounts) {
        log.info("예산 일괄 저장 - yearMonth: {}, 카테고리 수: {}", yearMonth, amounts.size());
        for (Map.Entry<ExpenseCategory, BigDecimal> entry : amounts.entrySet()) {
            ExpenseCategory category = entry.getKey();
            BigDecimal amount = entry.getValue();

            if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
                amount = BigDecimal.ZERO;
            }

            Budget budget = budgetRepository
                    .findByCategoryAndYearMonth(category, yearMonth)
                    .orElse(null);

            if (budget != null) {
                budget.updateAmount(amount);
                log.debug("예산 수정 - 카테고리: {}, 금액: {}", category, amount);
            } else {
                budgetRepository.save(Budget.builder()
                        .category(category)
                        .amount(amount)
                        .yearMonth(yearMonth)
                        .build());
                log.debug("예산 신규 등록 - 카테고리: {}, 금액: {}", category, amount);
            }
        }
        log.info("예산 일괄 저장 완료 - yearMonth: {}", yearMonth);
    }

    // 지난달 예산 복사
    @Transactional
    public void copyFromPreviousMonth(String targetYearMonth) {
        log.info("이전 달 예산 복사 시작 - targetYearMonth: {}", targetYearMonth);
        // 이전 월 계산
        int year = Integer.parseInt(targetYearMonth.substring(0, 4));
        int month = Integer.parseInt(targetYearMonth.substring(5, 7));
        if (month == 1) {
            year--;
            month = 12;
        } else {
            month--;
        }
        String prevMonth = String.format("%04d-%02d", year, month);

        List<Budget> prevBudgets = budgetRepository.findByYearMonth(prevMonth);
        if (prevBudgets.isEmpty()) {
            log.warn("이전 달 예산 없음 - prevMonth: {}", prevMonth);
            throw new IllegalArgumentException("이전 달(" + prevMonth + ") 예산이 없습니다.");
        }

        Map<ExpenseCategory, BigDecimal> amounts = new HashMap<>();
        for (Budget b : prevBudgets) {
            amounts.put(b.getCategory(), b.getAmount());
        }
        saveBudgets(targetYearMonth, amounts);
        log.info("이전 달 예산 복사 완료 - {} → {}", prevMonth, targetYearMonth);
    }
}
