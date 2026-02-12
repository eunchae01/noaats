package com.noaats.eunchae.budget.dto;

import com.noaats.eunchae.expense.domain.ExpenseCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class BudgetSaveRequest {

    private String yearMonth;
    private Map<ExpenseCategory, BigDecimal> amounts;
}
