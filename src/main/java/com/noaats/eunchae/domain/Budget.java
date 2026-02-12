package com.noaats.eunchae.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "budget",
       uniqueConstraints = @UniqueConstraint(columnNames = {"category", "year_month"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Budget extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ExpenseCategory category;

    @Column(nullable = false, precision = 12, scale = 0)
    private BigDecimal amount;

    @Column(name = "year_month", nullable = false, length = 7)
    private String yearMonth; // "2026-02" 형식

    @Builder
    public Budget(ExpenseCategory category, BigDecimal amount, String yearMonth) {
        this.category = category;
        this.amount = amount;
        this.yearMonth = yearMonth;
    }

    public void updateAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
