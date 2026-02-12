# BudgetServiceTest

## 테스트 대상
`com.noaats.eunchae.service.BudgetService` - 대시보드 요약, 예산 관리, 알림 비즈니스 로직

## 테스트 환경
- JUnit 5 + Mockito (MockitoExtension)
- `@Mock`: BudgetRepository, ExpenseRepository
- `@InjectMocks`: BudgetService

## 테스트 케이스

| # | 메서드명 | 설명 | 검증 항목 |
|---|---------|------|----------|
| 1 | `givenMonthlyData_whenGetDashboardSummary_thenReturnCorrectSums` | 데이터 있을 때 요약 | 합계, 잔여, 거래건수 |
| 2 | `givenNoData_whenGetDashboardSummary_thenReturnZeroes` | 데이터 없을 때 요약 | 모든 값 0 |
| 3 | `givenMixedCategories_whenGetBudgetVsActual_thenReturnAllCategories` | 예산 vs 실제 | 전체 카테고리 수, 금액 일치 |
| 4 | `givenHighSpending_whenGetAlerts_thenReturnOver80Percent` | 80% 이상 알림 | 알림 1건, 해당 카테고리 |
| 5 | `givenLowSpending_whenGetAlerts_thenReturnEmpty` | 80% 미만 | 빈 알림 목록 |
| 6 | `givenExistingBudget_whenSaveBudgets_thenUpdateAmount` | 기존 예산 수정 | 금액 업데이트 확인 |
| 7 | `givenNewBudget_whenSaveBudgets_thenCreateNew` | 신규 예산 생성 | save 호출 확인 |
| 8 | `givenNullOrNegativeAmount_whenSaveBudgets_thenSetToZero` | 음수/null 금액 | 0으로 보정 확인 |
| 9 | `givenPreviousMonthExists_whenCopyFromPreviousMonth_thenCopyAll` | 지난달 복사 | save 호출 확인 |
| 10 | `givenNoPreviousMonth_whenCopyFromPreviousMonth_thenThrowException` | 지난달 없음 | IllegalArgumentException |

## 커버리지 범위
- 대시보드: 데이터 유무 분기
- 알림: 80% 임계값 분기
- 예산 저장: 기존/신규/음수 분기
- 지난달 복사: 성공/실패 분기
