package com.noaats.eunchae.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class DashboardSummary {

    private BigDecimal totalExpense;    // 이번 달 총지출
    private BigDecimal totalBudget;     // 총 예산
    private BigDecimal remaining;       // 잔여 예산
    private long transactionCount;      // 거래 건수
}
