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

### [2026-02-13] 주제: 폴더 구조 리팩토링 및 버그 수정

#### 1. AI의 초기 제안
- 기존 flat 구조의 파일들을 모듈별(common, expense, budget, dashboard, pattern) 하위 패키지로 재구성
- 이전 위치에 남아있던 중복 파일 정리 및 삭제

#### 2. 개발자의 질문 / 수정 제안
- 폴더 구조 변경 후 이전 파일들이 남아있는 문제 지적
- `ExpenseService.calculateTotal()` 메서드에서 category 파라미터가 누락된 버그 발견

#### 3. 논의 과정
1. 모듈별 패키지 구조로 파일 재배치 (common, expense, budget, dashboard, pattern)
2. 이전 위치에 남아있던 파일 삭제 작업 진행:
   - 공통/예산/대시보드 관련 이전 파일 삭제
   - 지출/패턴 관련 이전 파일 삭제
   - 이전 테스트 파일 삭제
   - 이전 PatternController 파일 삭제
3. `ExpenseService.calculateTotal()` 메서드의 category 파라미터 누락 버그 수정

#### 4. 최종 결론 및 적용 사유
- **모듈별 패키지 구조 완성**: 기능별로 controller, domain, dto, repository, service가 하나의 패키지 안에 모여있어 응집도 향상
- **중복 파일 완전 정리**: 5회 커밋에 걸쳐 이전 위치의 파일들을 모두 삭제
- **버그 수정**: `calculateTotal()`에서 카테고리 필터가 적용되지 않던 문제 해결

#### 5. 적용된 코드
```
com/noaats/eunchae/
├── common/config/          ← DataInitializer 이동
├── common/domain/          ← BaseEntity 이동
├── common/exception/       ← GlobalExceptionHandler, ResourceNotFoundException 이동
├── expense/                ← 지출 관련 전체 이동
├── budget/                 ← 예산 관련 전체 이동
├── dashboard/              ← 대시보드 관련 전체 이동
├── pattern/                ← 소비 패턴 관련 전체 이동
└── expense/service/ExpenseService.java ← calculateTotal() category 파라미터 수정
```

---

### [2026-02-13] 주제: README.md 전면 업데이트

#### 1. AI의 초기 제안
- 기존 README.md에 TODO 주석으로 남아있던 프로젝트 구조, API 명세, 스크린샷 섹션을 실제 내용으로 채울 것을 제안
- 프로젝트 전체를 탐색하여 최신 코드 상태를 반영한 포괄적 README 작성

#### 2. 개발자의 질문 / 수정 제안
- 개발자가 README 업데이트와 함께 ai-log.md도 동시에 업데이트할 것을 요청
- AWS 업로드 후 접속 가능한 상태를 만들 것을 요청

#### 3. 논의 과정
1. 프로젝트 전체 구조 탐색 (소스 코드, 설정 파일, 테스트, 문서 등)
2. 기존 README.md의 TODO 항목 확인: 스크린샷, 프로젝트 구조 상세, API 명세
3. 프로젝트 구조를 트리 형태로 정리하여 README에 추가
4. 페이지 엔드포인트 11개 + REST API 엔드포인트 4개를 테이블로 명세
5. 카테고리 분류, 테스트 현황, 아키텍처 다이어그램 추가
6. 빌드 및 테스트 명령어 섹션 추가
7. 문서 링크에 guide.md, ai-log.md 추가

#### 4. 최종 결론 및 적용 사유
- **README.md 전면 개편**: 기술 스택 테이블에 버전 컬럼 추가, 프로젝트 구조 트리 추가, API 명세 (SSR 11개 + REST 4개) 추가, 카테고리 분류표 추가, 테스트 현황표 추가, 아키텍처 다이어그램 추가
- **TODO 주석 해소**: 기존 `<!-- TODO: 스크린샷 추가 -->`, `<!-- TODO: 프로젝트 구조 상세 -->`, `<!-- TODO: API 명세 -->` 3개 TODO 항목을 실제 내용으로 대체
- **문서 링크 보강**: PLAN.md 외에 guide.md, ai-log.md 링크 추가

#### 5. 적용된 코드
```
README.md       ← 전면 업데이트 (프로젝트 구조, API 명세, 카테고리, 테스트, 아키텍처 추가)
docs/ai-log.md  ← 2026-02-13 로그 2건 추가 (폴더 구조 리팩토링, README 업데이트)
```

---

### [2026-02-13] 주제: Docker 컨테이너화 및 AWS EC2 배포 준비

#### 1. AI의 초기 제안
- Multi-stage Dockerfile 작성 (빌드 스테이지 + 실행 스테이지)으로 이미지 크기 최소화
- eclipse-temurin:17 기반 이미지 사용
- application-prod.yaml로 운영 환경 설정 분리 (H2 콘솔 비활성화, Thymeleaf 캐시 활성화)

#### 2. 개발자의 질문 / 수정 제안
- t3.micro(1GB RAM) 환경에서 Docker 실행 가능 여부 확인 요청
- Docker + JAR 직접 실행 두 가지 방법을 모두 준비할 것을 요청

#### 3. 논의 과정
1. Dockerfile 작성: Multi-stage 빌드 (JDK로 빌드 → JRE로 실행)
2. .dockerignore 작성: .git, .idea, build, docs 등 불필요 파일 제외
3. t3.micro 메모리 제약 논의:
   - Spring Boot + H2 + Docker = 약 500~700MB 사용
   - 1GB RAM에서는 빡빡하므로 스왑 메모리 2GB 추가 권장
   - JVM 힙 메모리를 `-Xmx512m`으로 제한
4. 배포 방식 결정: Docker + JAR 직접 실행 둘 다 준비
5. application-prod.yaml 작성: H2 콘솔 비활성화, Thymeleaf 캐시 활성화
6. README.md에 AWS EC2 배포 가이드 추가 (Docker 방식 + JAR 방식)

#### 4. 최종 결론 및 적용 사유
- **Multi-stage Dockerfile**: 빌드 시 JDK, 실행 시 JRE만 사용하여 이미지 크기 절감
- **운영 프로파일 분리**: `application-prod.yaml`로 개발/운영 설정 분리 (보안 강화, 성능 최적화)
- **t3.micro 대응**: 스왑 메모리 추가 + JVM 힙 제한(`-Xmx512m`)으로 안정적 운영
- **이중 배포 옵션**: Docker가 문제될 경우 JAR 직접 실행으로 대체 가능

#### 5. 적용된 코드
```
Dockerfile                                    ← Multi-stage 빌드 (JDK 17 → JRE 17)
.dockerignore                                 ← 빌드 컨텍스트 최적화
src/main/resources/application-prod.yaml      ← 운영 환경 설정 (H2 콘솔 OFF, 캐시 ON)
README.md                                     ← AWS EC2 배포 가이드 추가 (Docker + JAR 방식)
```

---

### [2026-02-13] 주제: GitHub Actions CI/CD 파이프라인 구축

#### 1. AI의 초기 제안
- GitHub Actions 워크플로우 2개 구성: CI(자동 빌드/테스트) + CD(수동 배포)
- push 시 자동 빌드+테스트, 배포는 Actions 탭에서 수동 실행(workflow_dispatch)

#### 2. 개발자의 질문 / 수정 제안
- GitLab CI/CD처럼 페이지에서 직접 버튼을 눌러 배포할 수 있는지 확인 요청
- GitHub Actions의 `workflow_dispatch` 트리거가 GitLab의 수동 파이프라인 실행과 동일한 역할임을 확인

#### 3. 논의 과정
1. GitLab CI/CD vs GitHub Actions 비교 (`.gitlab-ci.yml` vs `.github/workflows/*.yml`)
2. CI 워크플로우 작성: push/PR 시 자동 빌드+테스트, 테스트 결과 아티팩트 업로드
3. CD 워크플로우 작성: workflow_dispatch로 수동 트리거
   - Gradle 빌드 → Docker 이미지 빌드 → `docker save`로 이미지 압축
   - `appleboy/scp-action`으로 EC2에 이미지 전송
   - `appleboy/ssh-action`으로 EC2에서 `docker load` → 기존 컨테이너 교체 → 새 컨테이너 실행
4. GitHub Secrets 3개 필요: EC2_HOST, EC2_USERNAME, EC2_SSH_KEY
5. README.md에 CI/CD 섹션 추가 (워크플로우 설명, 수동 배포 방법, Secrets 설정 가이드)

#### 4. 최종 결론 및 적용 사유
- **CI 워크플로우**: main 브랜치 push/PR 시 자동 실행, Gradle 빌드 + 26개 테스트 + 결과 아티팩트 업로드
- **CD 워크플로우**: Actions 탭에서 "Run workflow" 버튼으로 수동 배포 (GitLab 수동 파이프라인과 동일 UX)
- **배포 전략**: Docker 이미지를 GitHub Actions Runner에서 빌드 후 EC2로 전송 (EC2에서 빌드하지 않아 t3.micro 메모리 절약)
- **무중단 배포**: 기존 컨테이너 stop/rm → 새 컨테이너 run (단순 교체 방식)

#### 5. 적용된 코드
```
.github/workflows/ci.yml      ← CI 워크플로우 (push/PR → 빌드+테스트)
.github/workflows/deploy.yml  ← CD 워크플로우 (수동 → Docker 빌드+EC2 배포)
README.md                      ← CI/CD 섹션 추가 (워크플로우, Secrets 설정 가이드)
```

---

### [2026-02-13] 주제: Nginx 리버스 프록시 및 Let's Encrypt SSL 인증서 적용

#### 1. AI의 초기 제안
- Nginx를 리버스 프록시로 사용하여 80/443 포트에서 Spring Boot(8080)로 전달
- Let's Encrypt + Certbot으로 무료 SSL 인증서 발급 및 자동 갱신
- SSL 적용을 위해 도메인이 필요하므로 Duck DNS 무료 서브도메인 사용 제안

#### 2. 개발자의 질문 / 수정 제안
- SSL 인증서 적용 요청
- 도메인이 없는 상태에서 Duck DNS(`noaats.duckdns.org`)로 무료 도메인 등록
- 로고 이미지가 HTTPS 환경에서 깨지는 문제 발견 (외부 HTTP URL → Mixed Content 차단)

#### 3. 논의 과정
1. SSL 인증서에 도메인이 필수임을 확인 → Duck DNS 무료 서브도메인 등록
2. `noaats.duckdns.org` → EC2 IP(`43.200.5.102`) 매핑 완료
3. EC2에 Nginx 설치 및 리버스 프록시 설정 (80 → localhost:8080)
4. Certbot으로 Let's Encrypt SSL 인증서 발급 (HTTPS 자동 리다이렉트)
5. 보안 그룹에 80(HTTP), 443(HTTPS) 포트 추가
6. 로고 깨짐 발견: `http://www.noaats.com/images/logo_gnb_off.svg` 외부 참조
   - HTTPS 페이지에서 HTTP 리소스 로드 시 Mixed Content로 브라우저가 차단
   - 해결: 로고 SVG를 프로젝트 내부(`/images/logo.svg`)로 다운로드, Thymeleaf `th:src` 적용

#### 4. 최종 결론 및 적용 사유
- **도메인**: Duck DNS 무료 서브도메인 `noaats.duckdns.org` 사용
- **Nginx 리버스 프록시**: 클라이언트 → Nginx(80/443) → Spring Boot(8080) 구조
- **SSL 인증서**: Let's Encrypt 무료 인증서, Certbot이 Nginx 설정 자동 수정, 90일마다 자동 갱신
- **로고 Mixed Content 해결**: 외부 HTTP URL 의존을 제거하고 로컬 리소스로 변경

#### 5. 적용된 코드
```
src/main/resources/static/images/logo.svg          ← 로고 이미지 로컬 저장
src/main/resources/templates/layout/layout.html     ← 로고 경로 th:src="@{/images/logo.svg}"로 변경
README.md                                           ← SSL, Nginx 설정 가이드 추가
```

---

### [2026-02-13] 주제: Flatpickr 연도 화살표 동작 버그 수정: instance.redraw() → instance.changeYear() API 사용

#### 1. AI의 초기 제안
- 이전 커밋에서 Flatpickr 달력 헤더의 연도 화살표(arrowUp/arrowDown)를 커스텀 이벤트 핸들러로 교체하는 코드를 작성
- 기존 화살표를 `cloneNode(true)`로 복제하여 Flatpickr 기본 이벤트를 제거한 뒤, `instance.currentYear--`/`instance.currentYear++` 후 `instance.redraw()`를 호출하는 방식으로 구현

#### 2. 개발자의 질문 / 수정 제안
- 개발자가 연도 화살표를 클릭해도 연도가 변경되지 않는 문제를 보고
- "달력 연도 움직이는 화살표 너가 수정했는데 안움직여"라는 버그 리포트 제출

#### 3. 논의 과정
1. 기존 코드 분석: `instance.currentYear--; instance.redraw()` 방식 사용 중
2. 원인 파악: `redraw()`는 단순히 달력 UI를 다시 그리는 메서드로, `currentYear` 속성을 직접 변경한 것이 내부 상태에 반영되지 않음
3. Flatpickr 공식 API 확인: `instance.changeYear(year)` 메서드가 내부 상태 업데이트와 UI 갱신을 모두 처리하는 올바른 방법
4. 추가로 `e.stopPropagation()` 적용하여 클릭 이벤트가 부모 요소로 전파되어 Flatpickr 기본 동작과 충돌하는 것을 방지

#### 4. 최종 결론 및 적용 사유
- **`instance.redraw()` → `instance.changeYear()` 교체**: `redraw()`는 내부 날짜 상태를 갱신하지 않으므로, 공식 API인 `changeYear()`를 사용하여 연도 변경과 UI 갱신을 동시에 처리
- **`e.stopPropagation()` 추가**: 클릭 이벤트 전파를 차단하여 Flatpickr 내부 이벤트 핸들러와의 충돌 방지
- 기존 `cloneNode(true)` + `replaceChild` 패턴(기본 이벤트 제거 목적)은 유지

#### 5. 적용된 코드
```javascript
// layout.html - Flatpickr onReady 콜백 (연도 화살표 커스텀 핸들러)
newUp.addEventListener('click', function(e) {
    e.stopPropagation();
    instance.changeYear(instance.currentYear - 1);
});
newDown.addEventListener('click', function(e) {
    e.stopPropagation();
    instance.changeYear(instance.currentYear + 1);
});
```

---

### [2026-02-13] 주제: Flatpickr 달력 글래스모피즘 UI 개선 및 연도 좌우 화살표 전환

#### 1. AI의 초기 제안
- 기존 상하(▲▼) 화살표를 `cloneNode(true)`로 복제 후 `changeYear()` API를 호출하도록 수정
- 기본 Flatpickr 스타일에 간단한 CSS 오버라이드만 적용

#### 2. 개발자의 질문 / 수정 제안
- `cloneNode` 방식으로도 화살표가 동작하지 않는 문제 보고 — Flatpickr가 `.arrowUp`/`.arrowDown` 클래스에 이벤트 위임을 사용하여 복제된 요소도 캡처됨
- 상하 화살표 위치가 텍스트와 겹치는 문제, 리본 모양(▼ 위 / ▲ 아래)으로 뒤집히는 문제 지적
- 달력 전체 디자인을 둥글고 세련되게 개선 요청 (글래스모피즘)
- 월 드롭다운 화살표 아이콘 제거 요청 (화살표가 너무 많음)

#### 3. 논의 과정
1. 1차: `cloneNode` + `changeYear()` → Flatpickr 이벤트 위임으로 실패
2. 2차: 새 요소 생성 + 다른 클래스명(`year-arrow`) + `flex-direction: column` → 위치 겹침 및 리본 모양 문제
3. 3차: CSS `order` 속성으로 순서 교체 시도 → 캐시/렌더링 문제로 불안정
4. **최종**: 상하 화살표를 완전히 폐기하고, `◀ 2026 ▶` 형태의 좌우 버튼으로 전환
5. `numInputWrapper` 바깥 `flatpickr-current-month`에 직접 삽입하여 Flatpickr 내부 이벤트와 완전 분리
6. 글래스모피즘 디자인 전면 적용: `backdrop-filter: blur()`, 반투명 배경, 그라데이션 헤더, 둥근 날짜 셀
7. `.flatpickr-current-month`의 기본 `padding-top: 7.48px` 제거로 세로 정렬 보정

#### 4. 최종 결론 및 적용 사유
- **◀ ▶ 좌우 버튼 방식 채택**: Flatpickr의 `.arrowUp`/`.arrowDown` 이벤트 위임과 완전히 분리되어 안정적으로 동작
- **글래스모피즘 스타일 적용**: 기존 기본 스타일 대비 시각적 일관성과 완성도 향상
- **월 드롭다운 화살표 제거**: `appearance: none` + background-image 없음으로 깔끔한 글래스 필 스타일

#### 5. 적용된 코드
```javascript
// layout.html - Flatpickr onReady 콜백 (◀ ▶ 연도 좌우 버튼)
var arrowUp = wrapper.querySelector('.arrowUp');
var arrowDown = wrapper.querySelector('.arrowDown');
if (arrowUp) arrowUp.style.display = 'none';
if (arrowDown) arrowDown.style.display = 'none';

var btnPrev = document.createElement('span');
btnPrev.className = 'year-nav year-nav-prev';
btnPrev.textContent = '◀';
currentMonth.insertBefore(btnPrev, wrapper);

var btnNext = document.createElement('span');
btnNext.className = 'year-nav year-nav-next';
btnNext.textContent = '▶';
wrapper.after(btnNext);

btnPrev.addEventListener('click', function(e) {
    e.preventDefault(); e.stopPropagation();
    instance.changeYear(instance.currentYear - 1);
});
btnNext.addEventListener('click', function(e) {
    e.preventDefault(); e.stopPropagation();
    instance.changeYear(instance.currentYear + 1);
});
```
```css
/* style.css - 글래스모피즘 달력 (주요 부분) */
.flatpickr-calendar { border-radius: 16px; backdrop-filter: blur(12px); }
.flatpickr-months { background: linear-gradient(135deg, #4A90D9, #6FB1FC); border-radius: 16px 16px 0 0; }
.flatpickr-monthDropdown-months { appearance: none; background: rgba(255,255,255,0.2); border-radius: 20px; }
.year-nav { cursor: pointer; font-size: 8px; opacity: 0.85; }
```

---

### [2026-02-13] 주제: 전체 페이지 테이블 반응형 처리 — 모바일 컬럼 숨김 및 축소

#### 1. AI의 초기 제안
- 예산 관리 페이지에서 고정 `style="width: 140px"` / `style="width: 180px"`을 CSS 클래스로 대체
- 진행률 컬럼에 `d-none d-md-table-cell` 적용하여 768px 이하에서 숨김
- `@media (max-width: 768px)` 블록에 테이블 셀 패딩/폰트 축소, input `min-width` 보장 추가

#### 2. 개발자의 질문 / 수정 제안
- "다른 페이지 테이블에도 동일하게 반응형으로 ㄱㄱ" — 전체 페이지로 확대 적용 요청

#### 3. 논의 과정
1. 각 페이지별 테이블 구조 분석:
   - **지출 관리** (6컬럼): 날짜, 카테고리, 설명, 메모, 금액, 관리 → 메모 컬럼이 모바일에서 불필요
   - **대시보드** (4컬럼): 날짜, 카테고리, 설명, 금액 → 초소형에서 설명 숨김
   - **소비 패턴** (5컬럼): 카테고리, 전월, 이번달, 변화, 추이 → 변화(금액) 컬럼 숨기고 추이(%)만 표시
   - **예산 관리** (5컬럼): 기존 작업 완료 — 진행률 숨김
2. Bootstrap 반응형 유틸리티 클래스 활용: `d-none d-md-table-cell` (768px), `d-none d-sm-table-cell` (576px)
3. 인라인 `style="width:"` 제거 → CSS 클래스 `.budget-col-amount`, `.expense-col-action` 등으로 대체
4. 480px 이하 초소형 화면 미디어쿼리 별도 추가

#### 4. 최종 결론 및 적용 사유
- **페이지별 우선순위 낮은 컬럼 숨김**: 핵심 정보(카테고리, 금액)는 항상 표시하되, 보조 정보(메모, 설명, 변화금액, 진행률)를 좁은 화면에서 숨겨 가독성 확보
- **768px + 480px 2단계 반응형**: 일반 모바일(768px)에서는 컬럼 숨김 + 폰트/패딩 축소, 초소형(480px)에서는 더 공격적 축소
- **대시보드 헤더에 `flex-wrap gap-2` 추가**: 좁은 화면에서 월 선택기가 줄바꿈되도록
- **소비 패턴 헤더 텍스트 간소화**: `전월 (2026년 1월)` → `전월`으로 변경하여 모바일 너비 절약

#### 5. 적용된 코드
```html
<!-- budgets.html: 진행률 컬럼 숨김 -->
<th class="text-center budget-col-progress d-none d-md-table-cell">진행률</th>
<td class="d-none d-md-table-cell">...</td>

<!-- expenses/list.html: 메모 컬럼 숨김 -->
<th class="text-center d-none d-md-table-cell">메모</th>
<td class="text-center text-muted small d-none d-md-table-cell" th:text="${expense.memo}"></td>

<!-- dashboard.html: 설명 컬럼 숨김 -->
<th class="text-center d-none d-sm-table-cell">설명</th>
<td class="text-center d-none d-sm-table-cell" th:text="${expense.description}"></td>

<!-- patterns.html: 변화 컬럼 숨김 -->
<th class="text-end d-none d-sm-table-cell">변화</th>
<td class="text-end amount d-none d-sm-table-cell">...</td>
```
```css
/* style.css - 반응형 추가 (주요 부분) */
@media (max-width: 768px) {
    .page-header { text-align: center; justify-content: center !important; }
    .table tbody td, .table thead th { padding: 0.5rem 0.4rem; font-size: 0.85rem; }
    .table .form-control-sm { font-size: 0.8rem; min-width: 80px; }
    .table .amount { font-size: 0.8rem; white-space: nowrap; }
    .chart-container { height: 250px !important; }
}
@media (max-width: 480px) {
    .table tbody td, .table thead th { padding: 0.4rem 0.25rem; font-size: 0.78rem; }
    .summary-card .summary-value { font-size: 1rem; }
    .page-header h2 { font-size: 1.25rem; }
}
```

---
