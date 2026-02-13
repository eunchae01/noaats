package com.noaats.eunchae.pattern.api;

import com.noaats.eunchae.expense.domain.ExpenseCategory;
import com.noaats.eunchae.pattern.dto.BudgetVsActual;
import com.noaats.eunchae.expense.repository.ExpenseRepository;
import com.noaats.eunchae.budget.service.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ChartApiController {

    private final ExpenseRepository expenseRepository;
    private final BudgetService budgetService;

    // 카테고리별 지출 요약 (도넛 차트용)
    @GetMapping("/category-summary")
    public Map<String, Object> categorySummary(@RequestParam String yearMonth) {
        log.debug("API - 카테고리 요약 조회, yearMonth: {}", yearMonth);
        List<Object[]> data = expenseRepository.sumByCategory(yearMonth);

        List<String> labels = new ArrayList<>();
        List<Long> values = new ArrayList<>();

        for (Object[] row : data) {
            ExpenseCategory cat = (ExpenseCategory) row[0];
            labels.add(cat.getDisplayName());
            values.add(((Number) row[1]).longValue());
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("labels", labels);
        result.put("values", values);
        return result;
    }

    // 월별 총지출 (라인 차트용)
    @GetMapping("/monthly-summary")
    public Map<String, Object> monthlySummary(@RequestParam(defaultValue = "6") int months) {
        log.debug("API - 월별 총지출 조회, months: {}", months);
        LocalDate startDate = YearMonth.now().minusMonths(months - 1).atDay(1);
        List<Object[]> data = expenseRepository.monthlyTotals(startDate);

        List<String> labels = new ArrayList<>();
        List<Long> values = new ArrayList<>();

        for (Object[] row : data) {
            labels.add((String) row[0]);
            values.add(((Number) row[1]).longValue());
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("labels", labels);
        result.put("values", values);
        return result;
    }

    // 카테고리별 월별 지출 (누적 막대 차트용)
    @GetMapping("/category-trend")
    public Map<String, Object> categoryTrend(@RequestParam(defaultValue = "6") int months) {
        log.debug("API - 카테고리 트렌드 조회, months: {}", months);
        LocalDate startDate = YearMonth.now().minusMonths(months - 1).atDay(1);
        List<Object[]> data = expenseRepository.categoryMonthlyTotals(startDate);

        // 월 목록
        Set<String> monthSet = new LinkedHashSet<>();
        // 카테고리 → {월 → 금액}
        Map<String, Map<String, Long>> categoryData = new LinkedHashMap<>();

        for (Object[] row : data) {
            String month = (String) row[0];
            ExpenseCategory cat = (ExpenseCategory) row[1];
            long amount = ((Number) row[2]).longValue();

            monthSet.add(month);
            categoryData.computeIfAbsent(cat.getDisplayName(), k -> new LinkedHashMap<>())
                    .put(month, amount);
        }

        List<String> labels = new ArrayList<>(monthSet);

        // datasets 구성
        List<Map<String, Object>> datasets = new ArrayList<>();
        String[] colors = {"#E8725A", "#5B8DEF", "#9B6DD7", "#45B99C", "#E57BA6", "#F0A05A", "#4DABD5", "#8896AB"};
        int colorIdx = 0;

        for (ExpenseCategory cat : ExpenseCategory.values()) {
            String displayName = cat.getDisplayName();
            Map<String, Long> monthData = categoryData.getOrDefault(displayName, Map.of());

            List<Long> values = new ArrayList<>();
            for (String m : labels) {
                values.add(monthData.getOrDefault(m, 0L));
            }

            Map<String, Object> dataset = new LinkedHashMap<>();
            dataset.put("label", displayName);
            dataset.put("data", values);
            dataset.put("backgroundColor", colors[colorIdx % colors.length]);
            datasets.add(dataset);
            colorIdx++;
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("labels", labels);
        result.put("datasets", datasets);
        return result;
    }

    // 예산 vs 실제 (가로 막대 차트용)
    @GetMapping("/budget-vs-actual")
    public Map<String, Object> budgetVsActual(@RequestParam String yearMonth) {
        log.debug("API - 예산 vs 실제 조회, yearMonth: {}", yearMonth);
        List<BudgetVsActual> data = budgetService.getBudgetVsActual(yearMonth);

        List<String> labels = new ArrayList<>();
        List<Long> budgetValues = new ArrayList<>();
        List<Long> actualValues = new ArrayList<>();

        for (BudgetVsActual bva : data) {
            labels.add(bva.getCategory().getDisplayName());
            budgetValues.add(bva.getBudgetAmount().longValue());
            actualValues.add(bva.getActualAmount().longValue());
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("labels", labels);
        result.put("budgetValues", budgetValues);
        result.put("actualValues", actualValues);
        return result;
    }
}
