# CategoryChangeDtoTest

## 테스트 대상
`com.noaats.eunchae.dto.CategoryChangeDto` - 전월 대비 카테고리별 변화 DTO

## 테스트 환경
- JUnit 5 (순수 단위 테스트, Mock 불필요)

## 테스트 케이스

| # | 메서드명 | 설명 | 검증 항목 |
|---|---------|------|----------|
| 1 | `givenIncrease_whenCheckStatus_thenIsIncreaseTrue` | 지출 증가 | isIncrease=true, change 양수, changeRate 50% |
| 2 | `givenDecrease_whenCheckStatus_thenIsDecreaseTrue` | 지출 감소 | isDecrease=true, change 음수, changeRate -40% |
| 3 | `givenSmallChange_whenCheckNeutral_thenIsNeutralTrue` | 미미한 변화 | isNeutral=true (5% 미만) |
| 4 | `givenZeroPrev_whenCalculateRate_thenReturn100OrZero` | 이전 0원 | 현재 있으면 100%, 없으면 0% |

## 커버리지 범위
- `isIncrease()` / `isDecrease()` / `isNeutral()`: 증가/감소/중립 판별
- `changeRate`: 정상 계산 + 이전 금액 0일 때 엣지 케이스
- `change`: 변화량 계산 (양수/음수)
