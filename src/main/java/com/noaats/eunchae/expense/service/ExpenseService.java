package com.noaats.eunchae.expense.service;

import com.noaats.eunchae.expense.domain.Expense;
import com.noaats.eunchae.expense.domain.ExpenseCategory;
import com.noaats.eunchae.expense.dto.ExpenseCreateRequest;
import com.noaats.eunchae.expense.dto.ExpenseUpdateRequest;
import com.noaats.eunchae.common.exception.ResourceNotFoundException;
import com.noaats.eunchae.expense.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    // 지출 목록 (필터 + 페이징)
    public Page<Expense> getExpenses(LocalDate startDate, LocalDate endDate,
                                     ExpenseCategory category, Pageable pageable) {
        log.debug("지출 목록 조회 - 기간: {} ~ {}, 카테고리: {}", startDate, endDate, category);
        if (category != null) {
            return expenseRepository.findByExpenseDateBetweenAndCategory(
                    startDate, endDate, category, pageable);
        }
        return expenseRepository.findByExpenseDateBetween(startDate, endDate, pageable);
    }

    // 단건 조회
    public Expense getExpense(Long id) {
        log.debug("지출 단건 조회 - id: {}", id);
        return expenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("지출 내역을 찾을 수 없음 - id: {}", id);
                    return new ResourceNotFoundException("지출 내역을 찾을 수 없습니다. id=" + id);
                });
    }

    // 생성
    @Transactional
    public Expense create(ExpenseCreateRequest request) {
        log.info("지출 생성 - 카테고리: {}, 금액: {}, 설명: {}",
                request.getCategory(), request.getAmount(), request.getDescription());
        Expense saved = expenseRepository.save(request.toEntity());
        log.info("지출 생성 완료 - id: {}", saved.getId());
        return saved;
    }

    // 수정
    @Transactional
    public Expense update(Long id, ExpenseUpdateRequest request) {
        log.info("지출 수정 - id: {}, 카테고리: {}, 금액: {}", id, request.getCategory(), request.getAmount());
        Expense expense = getExpense(id);
        expense.update(
                request.getAmount(),
                request.getCategory(),
                request.getDescription(),
                request.getExpenseDate(),
                request.getMemo()
        );
        log.info("지출 수정 완료 - id: {}", id);
        return expense;
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        log.info("지출 삭제 - id: {}", id);
        Expense expense = getExpense(id);
        expenseRepository.delete(expense);
        log.info("지출 삭제 완료 - id: {}", id);
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
        BigDecimal total = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.debug("지출 합계 계산 - 기간: {} ~ {}, 합계: {}", startDate, endDate, total);
        return total;
    }
}
