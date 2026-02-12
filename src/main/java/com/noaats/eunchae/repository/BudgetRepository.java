package com.noaats.eunchae.repository;

import com.noaats.eunchae.domain.Budget;
import com.noaats.eunchae.domain.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    // 특정 월의 모든 예산
    List<Budget> findByYearMonth(String yearMonth);

    // 특정 월 + 카테고리 예산
    Optional<Budget> findByCategoryAndYearMonth(ExpenseCategory category, String yearMonth);
}
