package com.noaats.eunchae.config;

import com.noaats.eunchae.domain.Budget;
import com.noaats.eunchae.domain.Expense;
import com.noaats.eunchae.domain.ExpenseCategory;
import com.noaats.eunchae.repository.BudgetRepository;
import com.noaats.eunchae.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;

    @Override
    public void run(String... args) {
        if (expenseRepository.count() > 0) {
            log.info("샘플 데이터가 이미 존재합니다. 초기화를 건너뜁니다.");
            return;
        }

        log.info("샘플 데이터 초기화 시작...");
        initExpenses();
        initBudgets();
        log.info("샘플 데이터 초기화 완료! (지출 {}건, 예산 {}건)",
                expenseRepository.count(), budgetRepository.count());
    }

    private void initExpenses() {
        // === 2025년 11월 ===
        expense("2025-11-02", ExpenseCategory.FOOD, 8500, "점심 식사 (김치찌개)", null);
        expense("2025-11-03", ExpenseCategory.TRANSPORT, 55000, "지하철 정기권 충전", "11월분");
        expense("2025-11-05", ExpenseCategory.HOUSING, 450000, "월세", null);
        expense("2025-11-05", ExpenseCategory.HOUSING, 48000, "인터넷 + TV 요금", "KT");
        expense("2025-11-07", ExpenseCategory.FOOD, 12000, "저녁 회식 (개인부담)", "팀 회식");
        expense("2025-11-09", ExpenseCategory.ENTERTAINMENT, 17000, "넷플릭스 월정액", "스탠다드");
        expense("2025-11-10", ExpenseCategory.SHOPPING, 35000, "가을 니트 구매", "온라인몰");
        expense("2025-11-12", ExpenseCategory.FOOD, 6800, "편의점 도시락", null);
        expense("2025-11-14", ExpenseCategory.HEALTH, 45000, "피부과 진료", "정기 검진");
        expense("2025-11-16", ExpenseCategory.EDUCATION, 50000, "온라인 강의 (인프런)", "Spring 강의");
        expense("2025-11-18", ExpenseCategory.FOOD, 9200, "점심 식사 (냉면)", null);
        expense("2025-11-20", ExpenseCategory.ENTERTAINMENT, 15000, "영화 관람", "CGV");
        expense("2025-11-22", ExpenseCategory.SHOPPING, 65000, "운동화 구매", null);
        expense("2025-11-25", ExpenseCategory.FOOD, 32000, "장보기 (마트)", "주말 식재료");
        expense("2025-11-27", ExpenseCategory.OTHER, 30000, "경조사비", "친구 결혼식");
        expense("2025-11-28", ExpenseCategory.TRANSPORT, 8500, "택시비", "야근 후");
        expense("2025-11-29", ExpenseCategory.FOOD, 7500, "카페 (아메리카노+케이크)", null);

        // === 2025년 12월 ===
        expense("2025-12-01", ExpenseCategory.HOUSING, 450000, "월세", null);
        expense("2025-12-01", ExpenseCategory.HOUSING, 62000, "휴대폰 요금", "KT 5G");
        expense("2025-12-03", ExpenseCategory.TRANSPORT, 55000, "지하철 정기권 충전", "12월분");
        expense("2025-12-05", ExpenseCategory.FOOD, 45000, "회식 (삼겹살)", "연말 팀회식");
        expense("2025-12-07", ExpenseCategory.ENTERTAINMENT, 17000, "넷플릭스 월정액", null);
        expense("2025-12-08", ExpenseCategory.SHOPPING, 120000, "크리스마스 선물", "부모님 선물");
        expense("2025-12-10", ExpenseCategory.FOOD, 8200, "점심 식사 (순두부)", null);
        expense("2025-12-12", ExpenseCategory.ENTERTAINMENT, 35000, "콘서트 티켓", "연말 공연");
        expense("2025-12-14", ExpenseCategory.FOOD, 28000, "치킨 배달", "친구 모임");
        expense("2025-12-15", ExpenseCategory.OTHER, 50000, "경조사비", "선배 결혼");
        expense("2025-12-18", ExpenseCategory.EDUCATION, 90000, "기술서적 3권 구매", "자바+스프링");
        expense("2025-12-20", ExpenseCategory.FOOD, 15000, "카페 (크리스마스 음료)", null);
        expense("2025-12-22", ExpenseCategory.TRANSPORT, 25000, "KTX (고향)", "크리스마스 귀성");
        expense("2025-12-25", ExpenseCategory.SHOPPING, 60000, "겨울 외투 구매", "세일");
        expense("2025-12-28", ExpenseCategory.HEALTH, 20000, "약국 (감기약)", null);
        expense("2025-12-30", ExpenseCategory.FOOD, 9800, "편의점 간식", "연말");
        expense("2025-12-31", ExpenseCategory.OTHER, 50000, "연말 파티 비용", null);

        // === 2026년 1월 ===
        expense("2026-01-02", ExpenseCategory.HOUSING, 450000, "월세", null);
        expense("2026-01-02", ExpenseCategory.HOUSING, 48000, "인터넷 + TV 요금", "KT");
        expense("2026-01-03", ExpenseCategory.TRANSPORT, 55000, "지하철 정기권 충전", "1월분");
        expense("2026-01-05", ExpenseCategory.FOOD, 11000, "점심 식사 (칼국수)", null);
        expense("2026-01-07", ExpenseCategory.ENTERTAINMENT, 17000, "넷플릭스 월정액", null);
        expense("2026-01-08", ExpenseCategory.FOOD, 42000, "장보기 (마트)", "신년 식재료");
        expense("2026-01-10", ExpenseCategory.SHOPPING, 45000, "무선 마우스 구매", "로지텍");
        expense("2026-01-12", ExpenseCategory.FOOD, 8500, "점심 식사 (비빔밥)", null);
        expense("2026-01-14", ExpenseCategory.HEALTH, 30000, "치과 검진", "정기 검진");
        expense("2026-01-16", ExpenseCategory.EDUCATION, 33000, "온라인 강의 (인프런)", "JPA 강의");
        expense("2026-01-18", ExpenseCategory.FOOD, 7200, "카페 (아메리카노)", null);
        expense("2026-01-20", ExpenseCategory.ENTERTAINMENT, 14000, "영화 관람", "메가박스");
        expense("2026-01-22", ExpenseCategory.TRANSPORT, 15000, "택시비", "병원 방문");
        expense("2026-01-24", ExpenseCategory.SHOPPING, 50000, "신발 관리용품", null);
        expense("2026-01-25", ExpenseCategory.FOOD, 9500, "점심 식사 (돈까스)", null);
        expense("2026-01-27", ExpenseCategory.OTHER, 72000, "보험료", "월납");
        expense("2026-01-29", ExpenseCategory.FOOD, 6500, "편의점 간식", null);
        expense("2026-01-30", ExpenseCategory.TRANSPORT, 8000, "버스비 (충전)", null);

        // === 2026년 2월 ===
        expense("2026-02-01", ExpenseCategory.HOUSING, 450000, "월세", null);
        expense("2026-02-01", ExpenseCategory.HOUSING, 62000, "휴대폰 요금", "KT 5G");
        expense("2026-02-03", ExpenseCategory.TRANSPORT, 55000, "지하철 정기권 충전", "2월분");
        expense("2026-02-04", ExpenseCategory.FOOD, 9500, "점심 식사 (한식당)", "동료와");
        expense("2026-02-05", ExpenseCategory.FOOD, 35000, "장보기 (마트)", "주말 식재료");
        expense("2026-02-06", ExpenseCategory.ENTERTAINMENT, 17000, "넷플릭스 월정액", "스탠다드");
        expense("2026-02-07", ExpenseCategory.EDUCATION, 33000, "온라인 강의 (인프런)", "Spring Boot 강의");
        expense("2026-02-07", ExpenseCategory.EDUCATION, 27000, "기술서적 구매", "클린코드");
        expense("2026-02-08", ExpenseCategory.HEALTH, 8500, "약국 (감기약)", null);
        expense("2026-02-08", ExpenseCategory.HEALTH, 36500, "병원 진료", "내과");
        expense("2026-02-09", ExpenseCategory.FOOD, 12000, "저녁 외식 (파스타)", null);
        expense("2026-02-09", ExpenseCategory.SHOPPING, 89000, "무선 이어폰 구매", "갤럭시 버즈");
        expense("2026-02-10", ExpenseCategory.FOOD, 4800, "편의점 간식", null);
        expense("2026-02-10", ExpenseCategory.ENTERTAINMENT, 28000, "볼링장", "친구 모임");
        expense("2026-02-11", ExpenseCategory.TRANSPORT, 12000, "택시비", "야근 후");
        expense("2026-02-11", ExpenseCategory.FOOD, 8200, "점심 식사 (국밥)", null);
        expense("2026-02-11", ExpenseCategory.SHOPPING, 81000, "봄 재킷 구매", "온라인몰");
        expense("2026-02-12", ExpenseCategory.FOOD, 9500, "점심 식사 (회사 근처 식당)", "동료와 한식");
        expense("2026-02-12", ExpenseCategory.ENTERTAINMENT, 15000, "웹툰 결제", "카카오페이지");
    }

    private void initBudgets() {
        // 2025년 11월 예산
        budget("2025-11", ExpenseCategory.FOOD, 300000);
        budget("2025-11", ExpenseCategory.TRANSPORT, 100000);
        budget("2025-11", ExpenseCategory.ENTERTAINMENT, 150000);
        budget("2025-11", ExpenseCategory.HOUSING, 520000);
        budget("2025-11", ExpenseCategory.SHOPPING, 100000);
        budget("2025-11", ExpenseCategory.HEALTH, 50000);
        budget("2025-11", ExpenseCategory.EDUCATION, 50000);
        budget("2025-11", ExpenseCategory.OTHER, 50000);

        // 2025년 12월 예산
        budget("2025-12", ExpenseCategory.FOOD, 350000);
        budget("2025-12", ExpenseCategory.TRANSPORT, 120000);
        budget("2025-12", ExpenseCategory.ENTERTAINMENT, 200000);
        budget("2025-12", ExpenseCategory.HOUSING, 520000);
        budget("2025-12", ExpenseCategory.SHOPPING, 150000);
        budget("2025-12", ExpenseCategory.HEALTH, 50000);
        budget("2025-12", ExpenseCategory.EDUCATION, 100000);
        budget("2025-12", ExpenseCategory.OTHER, 100000);

        // 2026년 1월 예산
        budget("2026-01", ExpenseCategory.FOOD, 300000);
        budget("2026-01", ExpenseCategory.TRANSPORT, 100000);
        budget("2026-01", ExpenseCategory.ENTERTAINMENT, 150000);
        budget("2026-01", ExpenseCategory.HOUSING, 520000);
        budget("2026-01", ExpenseCategory.SHOPPING, 100000);
        budget("2026-01", ExpenseCategory.HEALTH, 50000);
        budget("2026-01", ExpenseCategory.EDUCATION, 50000);
        budget("2026-01", ExpenseCategory.OTHER, 80000);

        // 2026년 2월 예산
        budget("2026-02", ExpenseCategory.FOOD, 300000);
        budget("2026-02", ExpenseCategory.TRANSPORT, 150000);
        budget("2026-02", ExpenseCategory.ENTERTAINMENT, 200000);
        budget("2026-02", ExpenseCategory.HOUSING, 520000);
        budget("2026-02", ExpenseCategory.SHOPPING, 200000);
        budget("2026-02", ExpenseCategory.HEALTH, 100000);
        budget("2026-02", ExpenseCategory.EDUCATION, 100000);
        budget("2026-02", ExpenseCategory.OTHER, 450000);
    }

    private void expense(String date, ExpenseCategory category, long amount,
                         String description, String memo) {
        expenseRepository.save(Expense.builder()
                .expenseDate(LocalDate.parse(date))
                .category(category)
                .amount(BigDecimal.valueOf(amount))
                .description(description)
                .memo(memo)
                .build());
    }

    private void budget(String yearMonth, ExpenseCategory category, long amount) {
        budgetRepository.save(Budget.builder()
                .yearMonth(yearMonth)
                .category(category)
                .amount(BigDecimal.valueOf(amount))
                .build());
    }
}
