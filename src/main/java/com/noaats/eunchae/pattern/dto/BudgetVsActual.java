package com.noaats.eunchae.pattern.dto;

import com.noaats.eunchae.expense.domain.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@AllArgsConstructor
public class BudgetVsActual {

    private ExpenseCategory category;
    private BigDecimal budgetAmount;
    private BigDecimal actualAmount;

    public BigDecimal getRemaining() {
        return budgetAmount.subtract(actualAmount);
    }

    public int getPercentage() {
        if (budgetAmount.compareTo(BigDecimal.ZERO) == 0) return 0;
        return actualAmount.multiply(BigDecimal.valueOf(100))
                .divide(budgetAmount, 0, RoundingMode.HALF_UP)
                .intValue();
    }

    // 진행바 색상 결정: safe(~79%), caution(80~99%), over(100%~)
    public String getProgressColor() {
        int pct = getPercentage();
        if (pct >= 100) return "over";
        if (pct >= 80) return "caution";
        return "safe";
    }

    // 텍스트 색상: success(~79%), warning(80~99%), danger(100%~)
    public String getTextColor() {
        int pct = getPercentage();
        if (pct >= 100) return "danger";
        if (pct >= 80) return "warning";
        return "success";
    }
}
