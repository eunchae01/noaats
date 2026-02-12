package com.noaats.eunchae.dto;

import com.noaats.eunchae.domain.ExpenseCategory;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseUpdateRequest {

    @NotNull(message = "날짜를 입력해주세요")
    private LocalDate expenseDate;

    @NotNull(message = "카테고리를 선택해주세요")
    private ExpenseCategory category;

    @NotNull(message = "금액을 입력해주세요")
    @DecimalMin(value = "1", message = "금액은 1원 이상이어야 합니다")
    @DecimalMax(value = "999999999999", message = "금액이 너무 큽니다")
    private BigDecimal amount;

    @NotBlank(message = "설명을 입력해주세요")
    @Size(max = 100, message = "설명은 100자 이내로 입력해주세요")
    private String description;

    @Size(max = 200, message = "메모는 200자 이내로 입력해주세요")
    private String memo;
}
