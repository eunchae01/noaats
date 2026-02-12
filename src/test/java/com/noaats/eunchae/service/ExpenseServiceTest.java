package com.noaats.eunchae.service;

import com.noaats.eunchae.domain.Expense;
import com.noaats.eunchae.domain.ExpenseCategory;
import com.noaats.eunchae.dto.ExpenseCreateRequest;
import com.noaats.eunchae.dto.ExpenseUpdateRequest;
import com.noaats.eunchae.exception.ResourceNotFoundException;
import com.noaats.eunchae.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("ExpenseService 단위 테스트")
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    private LocalDate startDate;
    private LocalDate endDate;
    private Pageable pageable;
    private Expense sampleExpense;

    @BeforeEach
    void setUp() {
        startDate = LocalDate.of(2026, 2, 1);
        endDate = LocalDate.of(2026, 2, 28);
        pageable = PageRequest.of(0, 10, Sort.by("expenseDate").descending());

        sampleExpense = Expense.builder()
                .amount(BigDecimal.valueOf(15000))
                .category(ExpenseCategory.FOOD)
                .description("점심 식사")
                .expenseDate(LocalDate.of(2026, 2, 10))
                .memo("회사 근처 식당")
                .build();
    }

    @Test
    @DisplayName("카테고리 필터가 있으면 카테고리별 필터링된 페이지를 반환한다")
    void givenCategoryFilter_whenGetExpenses_thenReturnFilteredPage() {
        // given
        ExpenseCategory category = ExpenseCategory.FOOD;
        Page<Expense> expectedPage = new PageImpl<>(List.of(sampleExpense));
        given(expenseRepository.findByExpenseDateBetweenAndCategory(startDate, endDate, category, pageable))
                .willReturn(expectedPage);

        // when
        Page<Expense> result = expenseService.getExpenses(startDate, endDate, category, pageable);

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getCategory()).isEqualTo(ExpenseCategory.FOOD);
        then(expenseRepository).should().findByExpenseDateBetweenAndCategory(startDate, endDate, category, pageable);
    }

    @Test
    @DisplayName("카테고리 필터가 없으면 전체 지출 페이지를 반환한다")
    void givenNoCategoryFilter_whenGetExpenses_thenReturnAllPage() {
        // given
        Page<Expense> expectedPage = new PageImpl<>(List.of(sampleExpense));
        given(expenseRepository.findByExpenseDateBetween(startDate, endDate, pageable))
                .willReturn(expectedPage);

        // when
        Page<Expense> result = expenseService.getExpenses(startDate, endDate, null, pageable);

        // then
        assertThat(result.getContent()).hasSize(1);
        then(expenseRepository).should().findByExpenseDateBetween(startDate, endDate, pageable);
    }

    @Test
    @DisplayName("유효한 ID로 조회하면 지출 내역을 반환한다")
    void givenValidId_whenGetExpense_thenReturnExpense() {
        // given
        Long id = 1L;
        given(expenseRepository.findById(id)).willReturn(Optional.of(sampleExpense));

        // when
        Expense result = expenseService.getExpense(id);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getDescription()).isEqualTo("점심 식사");
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회하면 예외를 던진다")
    void givenInvalidId_whenGetExpense_thenThrowException() {
        // given
        Long id = 999L;
        given(expenseRepository.findById(id)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> expenseService.getExpense(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("지출 내역을 찾을 수 없습니다");
    }

    @Test
    @DisplayName("유효한 요청으로 지출을 생성하면 저장된 지출을 반환한다")
    void givenValidRequest_whenCreate_thenSaveAndReturnExpense() {
        // given
        ExpenseCreateRequest request = new ExpenseCreateRequest();
        request.setAmount(BigDecimal.valueOf(25000));
        request.setCategory(ExpenseCategory.TRANSPORT);
        request.setDescription("택시비");
        request.setExpenseDate(LocalDate.of(2026, 2, 15));
        request.setMemo("야근 후 귀가");

        given(expenseRepository.save(any(Expense.class))).willReturn(sampleExpense);

        // when
        Expense result = expenseService.create(request);

        // then
        assertThat(result).isNotNull();
        then(expenseRepository).should().save(any(Expense.class));
    }

    @Test
    @DisplayName("유효한 요청으로 지출을 수정하면 업데이트된 지출을 반환한다")
    void givenValidRequest_whenUpdate_thenModifyExpense() {
        // given
        Long id = 1L;
        given(expenseRepository.findById(id)).willReturn(Optional.of(sampleExpense));

        ExpenseUpdateRequest request = new ExpenseUpdateRequest();
        request.setAmount(BigDecimal.valueOf(20000));
        request.setCategory(ExpenseCategory.FOOD);
        request.setDescription("저녁 식사");
        request.setExpenseDate(LocalDate.of(2026, 2, 10));
        request.setMemo("수정됨");

        // when
        Expense result = expenseService.update(id, request);

        // then
        assertThat(result.getDescription()).isEqualTo("저녁 식사");
        assertThat(result.getAmount()).isEqualByComparingTo(BigDecimal.valueOf(20000));
    }

    @Test
    @DisplayName("유효한 ID로 삭제하면 지출을 제거한다")
    void givenValidId_whenDelete_thenRemoveExpense() {
        // given
        Long id = 1L;
        given(expenseRepository.findById(id)).willReturn(Optional.of(sampleExpense));

        // when
        expenseService.delete(id);

        // then
        then(expenseRepository).should().delete(sampleExpense);
    }
}
