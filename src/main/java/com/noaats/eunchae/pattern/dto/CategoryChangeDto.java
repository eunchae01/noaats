package com.noaats.eunchae.pattern.dto;

import com.noaats.eunchae.expense.domain.ExpenseCategory;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class CategoryChangeDto {

    private final ExpenseCategory category;
    private final BigDecimal prevAmount;
    private final BigDecimal currentAmount;
    private final BigDecimal change;
    private final double changeRate; // 변화율 (%)

    public CategoryChangeDto(ExpenseCategory category, BigDecimal prevAmount, BigDecimal currentAmount) {
        this.category = category;
        this.prevAmount = prevAmount;
        this.currentAmount = currentAmount;
        this.change = currentAmount.subtract(prevAmount);

        if (prevAmount.compareTo(BigDecimal.ZERO) == 0) {
            this.changeRate = currentAmount.compareTo(BigDecimal.ZERO) > 0 ? 100.0 : 0.0;
        } else {
            this.changeRate = change.multiply(BigDecimal.valueOf(100))
                    .divide(prevAmount, 1, RoundingMode.HALF_UP)
                    .doubleValue();
        }
    }

    public boolean isIncrease() {
        return change.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isDecrease() {
        return change.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isNeutral() {
        return Math.abs(changeRate) < 5.0;
    }
}
