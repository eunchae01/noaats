# AI 활용 기록 로그

이 파일은 프로젝트 진행 중 AI를 활용한 내역을 시간순으로 기록합니다.
AI는 `/.claude/commands/CLAUDE.md`의 지침에 따라 이 파일에 자동으로 로그를 추가합니다.

---

### [2026-02-12] 주제: 프로젝트 개발 순서 및 디자인 방향 논의

#### 1. AI의 초기 제안
- 기획서(PLAN.md) 작성 완료 후 바로 Phase 1 코드 구현(application.yaml, 엔티티, Repository 등)으로 진행하는 순서를 제안
- 구현 순서: 기반 세팅 → 지출 CRUD → 대시보드 → 소비 패턴 → 마무리

#### 2. 개발자의 질문 / 수정 제안
- 실무 프로젝트 진행 순서(기획 → 디자인 → 개발)를 따르는 것이 적절하다고 판단, AI가 제안한 순서 조정 요청
- UI/UX가 평가 가산점 항목에 포함되어 있으므로 디자인 단계를 별도로 진행할 것을 제안
- 디자인 접근법으로 와이어프레임을 먼저 작성하여 레이아웃을 확정한 뒤 개발에 착수하는 방식 선택
- 색상 테마는 노아에이티에스 홈페이지의 브랜드 컬러(블루 계열)를 참고하여, 금융 서비스에도 적합한 네이비/블루 계열로 방향 설정

#### 3. 논의 과정
1. AI가 기획서 → 바로 코드 구현 순서를 제안
2. 개발자가 실무 프로세스(기획 → 디자인 → 개발)를 따르자고 수정
3. 디자인 접근법 3가지 제안: ① 코드로 바로 구현 ② 와이어프레임 먼저 ③ 참고 디자인 찾기
4. 개발자가 "와이어프레임 먼저" 선택
5. 색상 테마 3가지 제안: ① 네이비/블루 ② 그린/민트 ③ 기본 Bootstrap
6. 개발자가 "네이비/블루 계열" 선택

#### 4. 최종 결론 및 적용 사유
- **개발 순서 변경**: 기획 → 디자인(와이어프레임) → 개발
  - 실무 프로세스를 따르는 것이 개발자 입장에서 더 좋다고 판단
- **Phase 0.7 추가**: 와이어프레임 단계를 구현 계획에 삽입
  - 정적 HTML로 4개 페이지 목업 작성 → 브라우저 확인 → Thymeleaf로 전환
- **디자인 테마**: 네이비/블루 계열
  - Primary: #1B2A4A, Accent: #4A90D9, Background: #F5F7FA
  - 노아에이티에스 홈페이지 브랜드 컬러를 참고하여 톤 통일
  - 금융 서비스에 적합한 신뢰감 있는 색상 (토스, 카카오뱅크 등 참고)

#### 5. 적용된 코드
```markdown
<!-- 계획 파일(gleaming-fluttering-swan.md)에 추가된 내용 -->
### Phase 0.7: 디자인 (와이어프레임)
- 색상 테마: 네이비/블루 계열
  - Primary: 네이비 (#1B2A4A)
  - Accent: 블루 (#4A90D9)
  - Background: 연한 회색 (#F5F7FA)
- 작업: 4개 페이지 정적 HTML 와이어프레임 작성
- 브라우저 확인 후 Thymeleaf 템플릿으로 전환
```

---

### [2026-02-12] 주제: Phase 0.7 와이어프레임 디자인 구현

#### 1. AI의 초기 제안
- Bootstrap 5 기반 커스텀 CSS(style.css)를 먼저 작성하여 디자인 시스템 정의
- 4개 페이지 + 지출 폼 = 총 5개 정적 HTML 와이어프레임 작성
- 각 페이지에 CDN 링크와 navbar/footer를 포함한 완전한 HTML로 작성

#### 2. 개발자의 질문 / 수정 제안
- 와이어프레임 확인 후, Thymeleaf 전환 시 각 페이지마다 중복되는 CDN 링크·navbar·footer 등의 공통 코드를 `th:fragment`/`th:replace`로 레이아웃 템플릿에 통합할 것을 요청
- 레이아웃 구조: `layout/layout.html`에 공통 요소를 집중시키고, 개별 페이지는 본문 content만 전달하는 방식

#### 3. 논의 과정
1. 커스텀 CSS 작성 (네이비/블루 테마 변수, 컴포넌트 스타일)
2. 대시보드 와이어프레임 작성 (요약카드, 도넛차트, 예산알림, 최근지출)
3. 지출관리 목록 + 지출 추가/수정 폼 와이어프레임 작성
4. 예산관리 와이어프레임 작성 (예산 입력 테이블, 진행바, 가로막대 비교차트)
5. 소비패턴 와이어프레임 작성 (월별 라인차트, 누적막대차트, 전월대비 테이블)
6. 개발자가 Thymeleaf 전환 시 레이아웃 템플릿 활용 지시

#### 4. 최종 결론 및 적용 사유
- **와이어프레임 6개 파일 완성**:
  - `css/style.css` - 디자인 시스템 (CSS 변수, 컴포넌트 스타일 전체)
  - `wireframe-dashboard.html` - 대시보드 (Chart.js 도넛차트 포함)
  - `wireframe-expenses.html` - 지출 목록 (필터, 테이블, 페이지네이션)
  - `wireframe-expense-form.html` - 지출 추가/수정 폼
  - `wireframe-budgets.html` - 예산 관리 (입력 테이블, 가로막대 차트)
  - `wireframe-patterns.html` - 소비 패턴 (라인차트, 누적막대차트, 비교 테이블)
- **Thymeleaf 전환 계획**: Phase 1에서 `layout/layout.html` 공통 레이아웃 도입, 페이지별 중복 제거

#### 5. 적용된 코드
```
src/main/resources/static/
  css/style.css                    ← 네이비/블루 디자인 시스템
  wireframe-dashboard.html         ← 대시보드 와이어프레임
  wireframe-expenses.html          ← 지출 목록
  wireframe-expense-form.html      ← 지출 폼
  wireframe-budgets.html           ← 예산 관리
  wireframe-patterns.html          ← 소비 패턴
```

---

### [2026-02-12] 주제: Phase 1 기반 세팅 - 샘플 데이터 초기화 방식 논의

#### 1. AI의 초기 제안
- `DataInitializer.java`(CommandLineRunner)를 사용하여 Repository를 통해 샘플 데이터를 삽입하는 방식 제안
- 플랜 문서에도 `config/DataInitializer.java`로 설계되어 있었음

#### 2. 개발자의 질문 / 수정 제안
- `data.sql` 방식(Spring Boot 자동 실행 SQL)을 이전에 사용해본 경험이 있어 해당 방식을 언급
- 두 방식의 장단점 비교를 요청

#### 3. 논의 과정
1. AI가 두 가지 방식 비교:
   - `data.sql`: SQL INSERT문 직접 작성, 엔티티 변경 시 SQL도 동시 수정 필요
   - `DataInitializer`: 자바 코드 기반, 엔티티 변경 시 컴파일 오류로 즉시 감지, Enum 타입 안전
2. JPA 프로젝트에서는 DataInitializer가 관리 효율성이 더 높다고 결론
3. `expenseRepository.count() > 0` 조건으로 중복 삽입 방지 로직 포함

#### 4. 최종 결론 및 적용 사유
- **`DataInitializer.java` 채택**: 타입 안전성, 엔티티 동기화, 코드 가독성 측면에서 우위
- 4개월분(2025.11~2026.02) 샘플 데이터: 지출 71건, 예산 32건
- 앱 기동 시 자동 삽입, 데이터 존재 시 초기화 건너뜀

#### 5. 적용된 코드
```
config/DataInitializer.java  ← CommandLineRunner 기반 샘플 데이터 로더
application.yaml             ← H2 File DB, JPA, H2 Console, Thymeleaf 설정
domain/BaseEntity.java       ← 공통 엔티티 (id, createdAt, updatedAt)
domain/ExpenseCategory.java  ← 8개 카테고리 Enum
domain/Expense.java          ← 지출 엔티티
domain/Budget.java           ← 예산 엔티티
repository/ExpenseRepository.java ← JPQL 집계 쿼리 포함
repository/BudgetRepository.java  ← 월별/카테고리 조회
templates/layout/layout.html ← Thymeleaf 공통 레이아웃 (th:replace)
```

---

### [2026-02-12] 주제: Phase 2 지출 CRUD 구현 - Validation 의존성 누락 해결

#### 1. AI의 초기 제안
- DTO에 Jakarta Validation 어노테이션(@NotNull, @NotBlank, @Size, @DecimalMin 등)을 적용하여 서버 사이드 유효성 검증 구현
- Service 계층은 `@Transactional(readOnly = true)` 기본 설정, 쓰기 메서드만 `@Transactional` 적용
- Controller에서 `@Valid` + `BindingResult` 패턴으로 검증 오류 처리

#### 2. 개발자의 질문 / 수정 제안
- 이 Phase에서는 별도 수정 요청 없이 진행

#### 3. 논의 과정
1. DTO 2개 작성 (ExpenseCreateRequest, ExpenseUpdateRequest)
2. ExpenseService 작성 (CRUD + 필터 조회 + 합계 계산)
3. ExpenseController 작성 (목록/폼/생성/수정/삭제 6개 엔드포인트)
4. Thymeleaf 뷰 2개 작성 (list.html, form.html) - layout 상속 구조
5. **컴파일 오류 발생**: `jakarta.validation` 패키지를 찾을 수 없음
6. **원인 분석**: `build.gradle`에 `spring-boot-starter-validation` 의존성 누락
7. 의존성 추가 후 컴파일 및 bootRun 정상 확인

#### 4. 최종 결론 및 적용 사유
- **Validation 의존성 추가**: Spring Boot 4에서는 starter-webmvc에 validation이 포함되지 않아 별도 추가 필요
- **Phase 2 산출물**: DTO 2개, Service 1개, Controller 1개, Thymeleaf 뷰 2개
- **레이아웃 상속 패턴**: 각 뷰가 `th:fragment="content"`로 본문만 정의, `layout/layout.html`이 공통 요소 관리

#### 5. 적용된 코드
```
build.gradle                          ← validation 의존성 추가
dto/ExpenseCreateRequest.java         ← 지출 생성 DTO + 유효성 검증
dto/ExpenseUpdateRequest.java         ← 지출 수정 DTO + 유효성 검증
service/ExpenseService.java           ← 지출 CRUD 비즈니스 로직
controller/ExpenseController.java     ← 6개 엔드포인트 (목록/추가폼/생성/수정폼/수정/삭제)
templates/expenses/list.html          ← 지출 목록 (필터, 테이블, 페이지네이션)
templates/expenses/form.html          ← 지출 추가/수정 폼 (유효성 검증 메시지)
```

---

### [2026-02-12] 주제: AI 활용 기록 로그 순서 검수

#### 1. AI의 초기 제안
- 각 Phase 완료 시 ai-log.md에 항목을 추가하는 방식으로 기록
- 새 로그를 파일 하단에 삽입하는 방식으로 작성

#### 2. 개발자의 질문 / 수정 제안
- 로그가 시간순으로 기록되어야 하는데 역순으로 작성된 것을 확인
- 순서를 올바르게 수정할 것을 요청

#### 3. 논의 과정
1. 개발자가 기록 순서가 맞는지 확인 요청
2. 확인 결과 Phase 2(최신)가 맨 위, 개발 순서 논의(최초)가 맨 아래로 역순 배치
3. 시간순 정렬로 전체 재배치 수행

#### 4. 최종 결론 및 적용 사유
- **로그 순서 수정**: 오래된 기록이 위, 최신 기록이 아래 (시간순 정렬)
- 이후 새로운 로그는 파일 하단에 추가하는 방식으로 통일

#### 5. 적용된 코드
```
docs/ai-log.md ← 전체 항목 시간순 재배치
```

---

### [2026-02-12] 주제: Phase 3 대시보드 + 예산 관리 구현

#### 1. AI의 초기 제안
- 대시보드: DashboardSummary DTO로 요약 데이터 전달, Chart.js REST API로 도넛차트 렌더링
- 예산 관리: BudgetVsActual DTO로 진행률/색상 자동 계산, 일괄 저장 + 지난달 복사 기능
- REST API: 4개 엔드포인트 (카테고리요약, 월별총계, 카테고리트렌드, 예산vs실제)

#### 2. 개발자의 질문 / 수정 제안
- 이 Phase에서는 별도 수정 요청 없이 진행

#### 3. 논의 과정
1. DTO 3개 작성 (DashboardSummary, BudgetVsActual, BudgetSaveRequest)
2. BudgetService 작성 (대시보드 요약, 예산vs실제, 알림, 저장, 지난달 복사)
3. DashboardController 작성 (월 네비게이션 포함)
4. BudgetController 작성 (목록/저장/지난달복사)
5. ChartApiController 작성 (REST API 4개 엔드포인트)
6. Thymeleaf 뷰 2개 작성 (dashboard.html, budgets.html)
7. 컴파일 및 bootRun 정상 확인

#### 4. 최종 결론 및 적용 사유
- **대시보드**: 요약카드 4개 + 도넛차트(API) + 예산알림(80%이상) + 최근지출 5건
- **예산 관리**: 카테고리별 예산 입력/수정 + 진행바(초록/노랑/빨강) + 가로막대 비교차트(API)
- **REST API**: Chart.js에서 fetch로 JSON 데이터 요청, SSR과 분리하여 차트 렌더링

#### 5. 적용된 코드
```
dto/DashboardSummary.java             ← 대시보드 요약 DTO
dto/BudgetVsActual.java               ← 예산 vs 실제 DTO (진행률/색상 자동 계산)
dto/BudgetSaveRequest.java            ← 예산 일괄 저장 DTO
service/BudgetService.java            ← 예산 비즈니스 로직
controller/DashboardController.java   ← 대시보드 (GET /)
controller/BudgetController.java      ← 예산 관리 (GET/POST /budgets)
controller/api/ChartApiController.java ← REST API 4개 엔드포인트
templates/dashboard.html              ← 대시보드 뷰
templates/budgets.html                ← 예산 관리 뷰
```

---

### [2026-02-12] 주제: Phase 4 소비 패턴 분석 구현

#### 1. AI의 초기 제안
- PatternController에서 전월/이번달 카테고리별 지출을 조회하여 비교 데이터 생성
- CategoryChangeDto로 변화량/변화율/증감 여부를 자동 계산
- Chart.js 차트는 Phase 3에서 구현한 REST API(monthly-summary, category-trend) 재활용
- Thymeleaf 뷰에서 비교 테이블은 서버 사이드 렌더링, 차트는 클라이언트 사이드 fetch

#### 2. 개발자의 질문 / 수정 제안
- 이 Phase에서는 별도 수정 요청 없이 진행

#### 3. 논의 과정
1. CategoryChangeDto 작성 (전월 대비 변화량, 변화율, 증감/중립 판별)
2. PatternController 작성 (GET /patterns, 카테고리별 전월 대비 비교 데이터 계산)
3. patterns.html Thymeleaf 뷰 작성:
   - 월별 총지출 라인 차트 (fetch `/api/expenses/monthly-summary`)
   - 카테고리별 월별 누적 막대 차트 (fetch `/api/expenses/category-trend`)
   - 전월 대비 변화 테이블 (Thymeleaf 서버 사이드 렌더링)
4. 컴파일 정상 확인

#### 4. 최종 결론 및 적용 사유
- **CategoryChangeDto**: `isIncrease()`, `isDecrease()`, `isNeutral()` 메서드로 Thymeleaf에서 조건부 렌더링
- **REST API 재활용**: Phase 3에서 구현한 ChartApiController의 monthly-summary, category-trend 엔드포인트를 그대로 사용
- **비교 테이블**: 카테고리별 전월/이번달 금액, 변화량(+/-), 변화율(%) 표시, 합계 행 포함

#### 5. 적용된 코드
```
dto/CategoryChangeDto.java           ← 전월 대비 변화 DTO (변화량/변화율/증감 판별)
controller/PatternController.java    ← 소비 패턴 (GET /patterns)
templates/patterns.html              ← 소비 패턴 뷰 (라인차트 + 누적막대 + 비교테이블)
```

---

### [2026-02-12] 주제: Phase 5 마무리 - 단위 테스트, 에러 처리, UI 개선

#### 1. AI의 초기 제안
- test.md 규칙에 따라 BDD 스타일 단위 테스트 작성 (JUnit 5 + BDDMockito + AssertJ)
- Service 계층(ExpenseService, BudgetService) + DTO(BudgetVsActual, CategoryChangeDto) 4개 테스트 클래스 작성
- @ControllerAdvice로 글로벌 에러 핸들링, ResourceNotFoundException 커스텀 예외 도입
- 에러 페이지 3종(400/404/500) 작성
- build.gradle의 테스트 의존성을 개별 starter 3개에서 spring-boot-starter-test로 통합

#### 2. 개발자의 질문 / 수정 제안
- 개발자가 test.md 파일 참조를 직접 지시하여 테스트 작성 규칙을 명확히 함

#### 3. 논의 과정
1. build.gradle 테스트 의존성 정리 (3개 → spring-boot-starter-test 1개로 통합)
2. ExpenseServiceTest 작성 (7개 테스트: CRUD + 필터 분기 + 예외)
3. BudgetServiceTest 작성 (10개 테스트: 요약/알림/저장/복사 + 엣지케이스)
4. BudgetVsActualTest 작성 (5개 테스트: 퍼센티지 0나누기, 색상 3단계 분기)
5. CategoryChangeDtoTest 작성 (4개 테스트: 증감/중립 판별, 이전금액 0 처리)
6. 컴파일 오류 수정: `List.of(new Object[]{})` 타입 추론 문제 → `Arrays.<Object[]>asList()` 사용
7. 전체 테스트 통과 확인 (BUILD SUCCESSFUL)
8. ResourceNotFoundException 커스텀 예외 + GlobalExceptionHandler + 에러 페이지 3종 작성
9. ExpenseService의 IllegalArgumentException → ResourceNotFoundException으로 교체
10. patterns.html 빈 상태 메시지 추가
11. test/java/docs/ 하위에 테스트 클래스별 마크다운 문서 4개 작성

#### 4. 최종 결론 및 적용 사유
- **단위 테스트 26개**: Service 2개 + DTO 2개 = 4개 테스트 클래스, 전체 통과
- **test.md 규칙 준수**: BDD 메서드명, MockitoExtension, AssertJ, 독립적 테스트, 테스트 문서 생성
- **에러 처리 체계화**: 커스텀 예외 → @ControllerAdvice → 에러 페이지 3단 구조
- **UI 개선**: patterns.html 빈 상태 처리 추가 (기존 템플릿은 이미 KRW 포맷, table-responsive, 빈 상태 적용)
- **테스트 의존성 통합**: starter-test 하나로 JUnit 5, Mockito, AssertJ 모두 포함

#### 5. 적용된 코드
```
build.gradle                                      ← 테스트 의존성 통합 (starter-test)
service/ExpenseService.java                       ← ResourceNotFoundException 적용
exception/ResourceNotFoundException.java          ← 커스텀 예외 클래스
exception/GlobalExceptionHandler.java             ← 글로벌 에러 핸들러
templates/error/400.html                          ← 400 에러 페이지
templates/error/404.html                          ← 404 에러 페이지
templates/error/500.html                          ← 500 에러 페이지
templates/patterns.html                           ← 빈 상태 메시지 추가
test/.../service/ExpenseServiceTest.java          ← 지출 서비스 테스트 (7건)
test/.../service/BudgetServiceTest.java           ← 예산 서비스 테스트 (10건)
test/.../dto/BudgetVsActualTest.java              ← 예산 대비 실제 DTO 테스트 (5건)
test/.../dto/CategoryChangeDtoTest.java           ← 카테고리 변화 DTO 테스트 (4건)
test/java/docs/.../ExpenseServiceTest.md          ← 테스트 문서
test/java/docs/.../BudgetServiceTest.md           ← 테스트 문서
test/java/docs/.../BudgetVsActualTest.md          ← 테스트 문서
test/java/docs/.../CategoryChangeDtoTest.md       ← 테스트 문서
```

---
