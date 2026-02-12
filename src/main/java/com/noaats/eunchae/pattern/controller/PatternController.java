package com.noaats.eunchae.pattern.controller;

import com.noaats.eunchae.expense.domain.ExpenseCategory;
import com.noaats.eunchae.pattern.dto.CategoryChangeDto;
import com.noaats.eunchae.expense.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class PatternController {

    private final ExpenseRepository expenseRepository;

    @GetMapping("/patterns")
    public String patterns(Model model) {
        String currentMonth = YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String prevMonth = YearMonth.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // 전월 카테고리별 지출
        Map<ExpenseCategory, BigDecimal> prevMap = buildCategoryMap(
                expenseRepository.sumByCategory(prevMonth));

        // 이번달 카테고리별 지출
        Map<ExpenseCategory, BigDecimal> currentMap = buildCategoryMap(
                expenseRepository.sumByCategory(currentMonth));

        // 카테고리별 변화 목록
        List<CategoryChangeDto> changes = new ArrayList<>();
        BigDecimal prevTotal = BigDecimal.ZERO;
        BigDecimal currentTotal = BigDecimal.ZERO;

        for (ExpenseCategory cat : ExpenseCategory.values()) {
            BigDecimal prev = prevMap.getOrDefault(cat, BigDecimal.ZERO);
            BigDecimal curr = currentMap.getOrDefault(cat, BigDecimal.ZERO);
            changes.add(new CategoryChangeDto(cat, prev, curr));
            prevTotal = prevTotal.add(prev);
            currentTotal = currentTotal.add(curr);
        }

        // 합계 행
        CategoryChangeDto totalChange = new CategoryChangeDto(null, prevTotal, currentTotal);

        // 월 표시
        YearMonth prevYm = YearMonth.parse(prevMonth);
        YearMonth currentYm = YearMonth.parse(currentMonth);

        model.addAttribute("changes", changes);
        model.addAttribute("totalChange", totalChange);
        model.addAttribute("prevDisplay", prevYm.getMonthValue() + "월");
        model.addAttribute("currentDisplay", currentYm.getMonthValue() + "월");

        // layout 관련
        model.addAttribute("pageTitle", "소비 패턴");
        model.addAttribute("currentPage", "patterns");
        model.addAttribute("contentTemplate", "patterns");

        return "layout/layout";
    }

    private Map<ExpenseCategory, BigDecimal> buildCategoryMap(List<Object[]> data) {
        Map<ExpenseCategory, BigDecimal> map = new HashMap<>();
        for (Object[] row : data) {
            map.put((ExpenseCategory) row[0], (BigDecimal) row[1]);
        }
        return map;
    }
}
