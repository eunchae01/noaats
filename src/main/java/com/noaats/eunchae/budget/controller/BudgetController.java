package com.noaats.eunchae.budget.controller;

import com.noaats.eunchae.expense.domain.ExpenseCategory;
import com.noaats.eunchae.budget.dto.BudgetSaveRequest;
import com.noaats.eunchae.pattern.dto.BudgetVsActual;
import com.noaats.eunchae.budget.service.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping
    public String budgets(@RequestParam(required = false) String yearMonth, Model model) {
        log.debug("GET /budgets - yearMonth: {}", yearMonth);

        if (yearMonth == null) {
            yearMonth = YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }

        YearMonth ym = YearMonth.parse(yearMonth);
        String prevMonth = ym.minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String nextMonth = ym.plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM"));

        List<BudgetVsActual> budgetList = budgetService.getBudgetVsActual(yearMonth);

        // 합계 계산
        BigDecimal totalBudget = budgetList.stream()
                .map(BudgetVsActual::getBudgetAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalActual = budgetList.stream()
                .map(BudgetVsActual::getActualAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("yearMonth", yearMonth);
        model.addAttribute("displayMonth", ym.getYear() + "년 " + ym.getMonthValue() + "월");
        model.addAttribute("prevMonth", prevMonth);
        model.addAttribute("nextMonth", nextMonth);
        model.addAttribute("budgetList", budgetList);
        model.addAttribute("totalBudget", totalBudget);
        model.addAttribute("totalActual", totalActual);
        model.addAttribute("totalRemaining", totalBudget.subtract(totalActual));
        model.addAttribute("categories", ExpenseCategory.values());

        // layout 관련
        model.addAttribute("pageTitle", "예산 관리");
        model.addAttribute("currentPage", "budgets");
        model.addAttribute("contentTemplate", "budgets");

        return "layout/layout";
    }

    @PostMapping
    public String saveBudgets(@ModelAttribute BudgetSaveRequest request,
                              RedirectAttributes redirectAttributes) {
        log.info("POST /budgets - 예산 저장 요청, yearMonth: {}", request.getYearMonth());
        budgetService.saveBudgets(request.getYearMonth(), request.getAmounts());
        redirectAttributes.addFlashAttribute("message", "예산이 저장되었습니다.");
        return "redirect:/budgets?yearMonth=" + request.getYearMonth();
    }

    @PostMapping("/copy-previous")
    public String copyPrevious(@RequestParam String yearMonth,
                               RedirectAttributes redirectAttributes) {
        log.info("POST /budgets/copy-previous - yearMonth: {}", yearMonth);
        try {
            budgetService.copyFromPreviousMonth(yearMonth);
            redirectAttributes.addFlashAttribute("message", "이전 달 예산이 복사되었습니다.");
        } catch (IllegalArgumentException e) {
            log.warn("이전 달 예산 복사 실패 - yearMonth: {}, 원인: {}", yearMonth, e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/budgets?yearMonth=" + yearMonth;
    }
}
