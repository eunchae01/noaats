# ExpenseServiceTest

## 테스트 대상
`com.noaats.eunchae.service.ExpenseService` - 지출 CRUD 비즈니스 로직

## 테스트 환경
- JUnit 5 + Mockito (MockitoExtension)
- `@Mock`: ExpenseRepository
- `@InjectMocks`: ExpenseService

## 테스트 케이스

| # | 메서드명 | 설명 | 검증 항목 |
|---|---------|------|----------|
| 1 | `givenCategoryFilter_whenGetExpenses_thenReturnFilteredPage` | 카테고리 필터 있을 때 | 카테고리별 필터링 호출 확인 |
| 2 | `givenNoCategoryFilter_whenGetExpenses_thenReturnAllPage` | 카테고리 필터 없을 때 | 전체 조회 호출 확인 |
| 3 | `givenValidId_whenGetExpense_thenReturnExpense` | 유효한 ID 조회 | 반환값 not null, 설명 일치 |
| 4 | `givenInvalidId_whenGetExpense_thenThrowException` | 없는 ID 조회 | IllegalArgumentException 발생 |
| 5 | `givenValidRequest_whenCreate_thenSaveAndReturnExpense` | 지출 생성 | save 호출 확인 |
| 6 | `givenValidRequest_whenUpdate_thenModifyExpense` | 지출 수정 | 수정된 필드 값 확인 |
| 7 | `givenValidId_whenDelete_thenRemoveExpense` | 지출 삭제 | delete 호출 확인 |

## 커버리지 범위
- 분기: `getExpenses()`의 category null 분기 (2갈래)
- 정상: 생성/수정/삭제 정상 흐름
- 예외: 존재하지 않는 ID 조회 시 예외
