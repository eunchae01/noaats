package com.noaats.eunchae.dashboard.controller;

import com.noaats.eunchae.expense.domain.Expense;
import com.noaats.eunchae.pattern.dto.BudgetVsActual;
import com.noaats.eunchae.dashboard.dto.DashboardSummary;
import com.noaats.eunchae.expense.repository.ExpenseRepository;
import com.noaats.eunchae.budget.service.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final BudgetService budgetService;
    private final ExpenseRepository expenseRepository;

    @GetMapping("/")
    public String dashboard(@RequestParam(required = false) String yearMonth, Model model) {
        log.debug("GET / - 대시보드 요청, yearMonth: {}", yearMonth);

        if (yearMonth == null) {
            yearMonth = YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }

        // 이전/다음 월 계산
        YearMonth ym = YearMonth.parse(yearMonth);
        String prevMonth = ym.minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String nextMonth = ym.plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // 데이터
        DashboardSummary summary = budgetService.getDashboardSummary(yearMonth);
        List<BudgetVsActual> alerts = budgetService.getAlerts(yearMonth);
        List<Expense> recentExpenses = expenseRepository.findTop5ByOrderByExpenseDateDescIdDesc();

        log.debug("대시보드 데이터 로드 완료 - 알림: {}건, 최근 지출: {}건",
                alerts.size(), recentExpenses.size());

        model.addAttribute("yearMonth", yearMonth);
        model.addAttribute("displayMonth", ym.getYear() + "년 " + ym.getMonthValue() + "월");
        model.addAttribute("prevMonth", prevMonth);
        model.addAttribute("nextMonth", nextMonth);
        model.addAttribute("summary", summary);
        model.addAttribute("alerts", alerts);
        model.addAttribute("recentExpenses", recentExpenses);

        // layout 관련
        model.addAttribute("pageTitle", "대시보드");
        model.addAttribute("currentPage", "dashboard");
        model.addAttribute("contentTemplate", "dashboard");

        return "layout/layout";
    }
}
