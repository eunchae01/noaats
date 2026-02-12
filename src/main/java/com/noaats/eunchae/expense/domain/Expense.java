package com.noaats.eunchae.expense.domain;

import com.noaats.eunchae.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expense")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense extends BaseEntity {

    @Column(nullable = false, precision = 12, scale = 0)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ExpenseCategory category;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false)
    private LocalDate expenseDate;

    @Column(length = 200)
    private String memo;

    @Builder
    public Expense(BigDecimal amount, ExpenseCategory category, String description,
                   LocalDate expenseDate, String memo) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.expenseDate = expenseDate;
        this.memo = memo;
    }

    public void update(BigDecimal amount, ExpenseCategory category, String description,
                       LocalDate expenseDate, String memo) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.expenseDate = expenseDate;
        this.memo = memo;
    }
}
