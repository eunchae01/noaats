package com.noaats.eunchae.expense.repository;

import com.noaats.eunchae.expense.domain.Expense;
import com.noaats.eunchae.expense.domain.ExpenseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // 날짜 범위 + 카테고리 필터 (페이징)
    Page<Expense> findByExpenseDateBetweenAndCategory(
            LocalDate startDate, LocalDate endDate, ExpenseCategory category, Pageable pageable);

    // 날짜 범위 필터 (페이징)
    Page<Expense> findByExpenseDateBetween(
            LocalDate startDate, LocalDate endDate, Pageable pageable);

    // 특정 월의 지출 목록
    @Query("SELECT e FROM Expense e WHERE FUNCTION('FORMATDATETIME', e.expenseDate, 'yyyy-MM') = :yearMonth ORDER BY e.expenseDate DESC")
    List<Expense> findByYearMonth(@Param("yearMonth") String yearMonth);

    // 카테고리별 지출 합계 (특정 월)
    @Query("SELECT e.category, SUM(e.amount) FROM Expense e " +
           "WHERE FUNCTION('FORMATDATETIME', e.expenseDate, 'yyyy-MM') = :yearMonth " +
           "GROUP BY e.category")
    List<Object[]> sumByCategory(@Param("yearMonth") String yearMonth);

    // 특정 월 총지출
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
           "WHERE FUNCTION('FORMATDATETIME', e.expenseDate, 'yyyy-MM') = :yearMonth")
    BigDecimal sumByYearMonth(@Param("yearMonth") String yearMonth);

    // 특정 월 거래 건수
    @Query("SELECT COUNT(e) FROM Expense e " +
           "WHERE FUNCTION('FORMATDATETIME', e.expenseDate, 'yyyy-MM') = :yearMonth")
    long countByYearMonth(@Param("yearMonth") String yearMonth);

    // 월별 총지출 (최근 N개월)
    @Query("SELECT FUNCTION('FORMATDATETIME', e.expenseDate, 'yyyy-MM'), SUM(e.amount) " +
           "FROM Expense e " +
           "WHERE e.expenseDate >= :startDate " +
           "GROUP BY FUNCTION('FORMATDATETIME', e.expenseDate, 'yyyy-MM') " +
           "ORDER BY FUNCTION('FORMATDATETIME', e.expenseDate, 'yyyy-MM')")
    List<Object[]> monthlyTotals(@Param("startDate") LocalDate startDate);

    // 카테고리별 월별 지출 (최근 N개월)
    @Query("SELECT FUNCTION('FORMATDATETIME', e.expenseDate, 'yyyy-MM'), e.category, SUM(e.amount) " +
           "FROM Expense e " +
           "WHERE e.expenseDate >= :startDate " +
           "GROUP BY FUNCTION('FORMATDATETIME', e.expenseDate, 'yyyy-MM'), e.category " +
           "ORDER BY FUNCTION('FORMATDATETIME', e.expenseDate, 'yyyy-MM')")
    List<Object[]> categoryMonthlyTotals(@Param("startDate") LocalDate startDate);

    // 최근 N건 지출
    List<Expense> findTop5ByOrderByExpenseDateDescIdDesc();
}
