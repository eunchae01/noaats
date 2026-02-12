# BudgetVsActualTest

## 테스트 대상
`com.noaats.eunchae.dto.BudgetVsActual` - 예산 대비 실제 지출 계산 DTO

## 테스트 환경
- JUnit 5 (순수 단위 테스트, Mock 불필요)

## 테스트 케이스

| # | 메서드명 | 설명 | 검증 항목 |
|---|---------|------|----------|
| 1 | `givenZeroBudget_whenGetPercentage_thenReturnZero` | 예산 0원 | 0% 반환 (0 나누기 방지) |
| 2 | `givenOverBudget_whenGetProgressColor_thenReturnOver` | 100% 초과 | "over" 반환 |
| 3 | `givenCautionLevel_whenGetProgressColor_thenReturnCaution` | 80~99% | "caution" 반환 |
| 4 | `givenSafeLevel_whenGetTextColor_thenReturnSuccess` | 80% 미만 | "success" 반환 |
| 5 | `givenBudgetAndActual_whenGetRemaining_thenReturnDifference` | 잔여 계산 | 예산 - 실제 |

## 커버리지 범위
- `getPercentage()`: 0 나누기 엣지 케이스
- `getProgressColor()`: safe/caution/over 3단계 분기
- `getTextColor()`: success/warning/danger 3단계 분기
- `getRemaining()`: 차액 계산
