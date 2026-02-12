package com.noaats.eunchae.expense.controller;

import com.noaats.eunchae.expense.domain.Expense;
import com.noaats.eunchae.expense.domain.ExpenseCategory;
import com.noaats.eunchae.expense.dto.ExpenseCreateRequest;
import com.noaats.eunchae.expense.dto.ExpenseUpdateRequest;
import com.noaats.eunchae.expense.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Controller
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private static final int PAGE_SIZE = 10;

    // 지출 목록
    @GetMapping
    public String list(@RequestParam(required = false) LocalDate startDate,
                       @RequestParam(required = false) LocalDate endDate,
                       @RequestParam(required = false) ExpenseCategory category,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {

        // 기본값: 이번 달 1일 ~ 말일
        if (startDate == null || endDate == null) {
            YearMonth current = YearMonth.now();
            startDate = current.atDay(1);
            endDate = current.atEndOfMonth();
        }

        PageRequest pageable = PageRequest.of(page, PAGE_SIZE,
                Sort.by("expenseDate").descending().and(Sort.by("id").descending()));

        Page<Expense> expenses = expenseService.getExpenses(startDate, endDate, category, pageable);
        BigDecimal totalAmount = expenseService.calculateTotal(startDate, endDate, category);

        model.addAttribute("expenses", expenses);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("category", category);
        model.addAttribute("categories", ExpenseCategory.values());

        // layout 관련
        model.addAttribute("pageTitle", "지출 관리");
        model.addAttribute("currentPage", "expenses");
        model.addAttribute("contentTemplate", "expenses/list");

        return "layout/layout";
    }

    // 지출 추가 폼
    @GetMapping("/new")
    public String newForm(Model model) {
        ExpenseCreateRequest request = new ExpenseCreateRequest();
        request.setExpenseDate(LocalDate.now());

        model.addAttribute("expense", request);
        model.addAttribute("categories", ExpenseCategory.values());
        model.addAttribute("isEdit", false);

        // layout 관련
        model.addAttribute("pageTitle", "지출 추가");
        model.addAttribute("currentPage", "expenses");
        model.addAttribute("contentTemplate", "expenses/form");

        return "layout/layout";
    }

    // 지출 생성
    @PostMapping
    public String create(@Valid @ModelAttribute("expense") ExpenseCreateRequest request,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", ExpenseCategory.values());
            model.addAttribute("isEdit", false);
            model.addAttribute("pageTitle", "지출 추가");
            model.addAttribute("currentPage", "expenses");
            model.addAttribute("contentTemplate", "expenses/form");
            return "layout/layout";
        }

        expenseService.create(request);
        redirectAttributes.addFlashAttribute("message", "지출이 등록되었습니다.");
        return "redirect:/expenses";
    }

    // 지출 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Expense expense = expenseService.getExpense(id);

        ExpenseUpdateRequest request = new ExpenseUpdateRequest();
        request.setExpenseDate(expense.getExpenseDate());
        request.setCategory(expense.getCategory());
        request.setAmount(expense.getAmount());
        request.setDescription(expense.getDescription());
        request.setMemo(expense.getMemo());

        model.addAttribute("expense", request);
        model.addAttribute("expenseId", id);
        model.addAttribute("categories", ExpenseCategory.values());
        model.addAttribute("isEdit", true);

        // layout 관련
        model.addAttribute("pageTitle", "지출 수정");
        model.addAttribute("currentPage", "expenses");
        model.addAttribute("contentTemplate", "expenses/form");

        return "layout/layout";
    }

    // 지출 수정
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("expense") ExpenseUpdateRequest request,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("expenseId", id);
            model.addAttribute("categories", ExpenseCategory.values());
            model.addAttribute("isEdit", true);
            model.addAttribute("pageTitle", "지출 수정");
            model.addAttribute("currentPage", "expenses");
            model.addAttribute("contentTemplate", "expenses/form");
            return "layout/layout";
        }

        expenseService.update(id, request);
        redirectAttributes.addFlashAttribute("message", "지출이 수정되었습니다.");
        return "redirect:/expenses";
    }

    // 지출 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        expenseService.delete(id);
        redirectAttributes.addFlashAttribute("message", "지출이 삭제되었습니다.");
        return "redirect:/expenses";
    }
}
