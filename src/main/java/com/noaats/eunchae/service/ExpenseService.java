package com.noaats.eunchae.service;

import com.noaats.eunchae.domain.Expense;
import com.noaats.eunchae.domain.ExpenseCategory;
import com.noaats.eunchae.dto.ExpenseCreateRequest;
import com.noaats.eunchae.dto.ExpenseUpdateRequest;
import com.noaats.eunchae.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    // 지출 목록 (필터 + 페이징)
    public Page<Expense> getExpenses(LocalDate startDate, LocalDate endDate,
                                     ExpenseCategory category, Pageable pageable) {
        if (category != null) {
            return expenseRepository.findByExpenseDateBetweenAndCategory(
                    startDate, endDate, category, pageable);
        }
        return expenseRepository.findByExpenseDateBetween(startDate, endDate, pageable);
    }

    // 단건 조회
    public Expense getExpense(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("지출 내역을 찾을 수 없습니다. id=" + id));
    }

    // 생성
    @Transactional
    public Expense create(ExpenseCreateRequest request) {
        return expenseRepository.save(request.toEntity());
    }

    // 수정
    @Transactional
    public Expense update(Long id, ExpenseUpdateRequest request) {
        Expense expense = getExpense(id);
        expense.update(
                request.getAmount(),
                request.getCategory(),
                request.getDescription(),
                request.getExpenseDate(),
                request.getMemo()
        );
        return expense;
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        Expense expense = getExpense(id);
        expenseRepository.delete(expense);
    }

    // 필터된 지출 합계 계산
    public BigDecimal calculateTotal(LocalDate startDate, LocalDate endDate,
                                     ExpenseCategory category) {
        List<Expense> expenses;
        if (category != null) {
            expenses = expenseRepository.findByExpenseDateBetweenAndCategory(
                    startDate, endDate, category, Pageable.unpaged()).getContent();
        } else {
            expenses = expenseRepository.findByExpenseDateBetween(
                    startDate, endDate, Pageable.unpaged()).getContent();
        }
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
