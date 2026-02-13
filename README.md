# 노아의 가계부

직장 3년차 "노아"를 위한 지출 관리 & 예산 분석 웹 애플리케이션

> "이번 달 돈이 어디로 갔지?" — 매달 반복되는 이 질문에 답하기 위해 만들었습니다.

## 기술 스택

| 영역 | 기술 | 버전 |
|------|------|------|
| Language | Java | 17 |
| Backend | Spring Boot, Spring Data JPA | 4.0.2 |
| Frontend | Thymeleaf, Bootstrap, Chart.js | 5.3.3 / 4.4.7 |
| Database | H2 (File 모드) | - |
| Build | Gradle | 9.3 |
| Testing | JUnit 5, Mockito, AssertJ | - |
| Infra | Docker, AWS EC2, Nginx | - |
| CI/CD | GitHub Actions | - |
| SSL | Let's Encrypt (Certbot) | - |

## 실행 방법

### 사전 요구사항
- Java 17 이상

### 로컬 실행

```bash
./gradlew bootRun
```

브라우저에서 http://localhost:8080 접속

### H2 콘솔
- http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/budgetdb`
- Username: `sa` / Password: (없음)

### 빌드 및 테스트

```bash
./gradlew build     # 전체 빌드 (테스트 포함)
./gradlew test      # 단위 테스트만 실행
./gradlew bootJar   # 실행 가능한 JAR 생성
```

## 주요 기능

### 대시보드 (`/`)
- 이번 달 요약 카드 4개: 총지출, 총예산, 잔여예산, 거래건수
- 카테고리별 지출 비율 도넛 차트 (Chart.js)
- 예산 80% 이상 소진 카테고리 알림
- 최근 지출 5건 미리보기

### 지출 관리 (`/expenses`)
- 지출 기록 CRUD (추가/조회/수정/삭제)
- 날짜 범위 + 카테고리 필터링
- 페이지네이션 (10건/페이지)
- 서버 사이드 유효성 검증 (Jakarta Validation)

### 예산 관리 (`/budgets`)
- 카테고리별 월 예산 설정 (인라인 편집)
- 예산 대비 지출 진행바 (초록/노랑/빨강 3단계)
- 예산 vs 실제 가로 막대 비교 차트
- 지난달 예산 복사 기능

### 소비 패턴 분석 (`/patterns`)
- 월별 총지출 추이 라인 차트 (최근 6개월)
- 카테고리별 월별 누적 막대 차트
- 전월 대비 카테고리별 증감률 테이블

## 프로젝트 구조

```
src/main/java/com/noaats/eunchae/
├── BudgetApplication.java              # Spring Boot 메인 클래스
├── common/                             # 공통 인프라
│   ├── config/DataInitializer.java     #   샘플 데이터 초기화 (71건 지출 + 32건 예산)
│   ├── domain/BaseEntity.java          #   공통 엔티티 (id, createdAt, updatedAt)
│   └── exception/                      #   글로벌 예외 처리
│       ├── GlobalExceptionHandler.java
│       └── ResourceNotFoundException.java
├── expense/                            # 지출 모듈
│   ├── controller/ExpenseController.java
│   ├── domain/Expense.java
│   ├── domain/ExpenseCategory.java     #   8개 카테고리 Enum
│   ├── dto/ExpenseCreateRequest.java
│   ├── dto/ExpenseUpdateRequest.java
│   ├── repository/ExpenseRepository.java
│   └── service/ExpenseService.java
├── budget/                             # 예산 모듈
│   ├── controller/BudgetController.java
│   ├── domain/Budget.java
│   ├── dto/BudgetSaveRequest.java
│   ├── repository/BudgetRepository.java
│   └── service/BudgetService.java
├── dashboard/                          # 대시보드 모듈
│   ├── controller/DashboardController.java
│   └── dto/DashboardSummary.java
└── pattern/                            # 소비 패턴 분석 모듈
    ├── api/ChartApiController.java     #   REST API (Chart.js 데이터)
    ├── controller/PatternController.java
    └── dto/
        ├── BudgetVsActual.java
        └── CategoryChangeDto.java
```

## API 명세

### 페이지 (Thymeleaf SSR)

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/` | 대시보드 (월 네비게이션) |
| GET | `/expenses` | 지출 목록 (필터, 페이지네이션) |
| GET | `/expenses/new` | 지출 추가 폼 |
| POST | `/expenses` | 지출 생성 |
| GET | `/expenses/{id}/edit` | 지출 수정 폼 |
| POST | `/expenses/{id}` | 지출 수정 |
| POST | `/expenses/{id}/delete` | 지출 삭제 |
| GET | `/budgets` | 예산 관리 (월 네비게이션) |
| POST | `/budgets` | 예산 일괄 저장 |
| POST | `/budgets/copy-previous` | 지난달 예산 복사 |
| GET | `/patterns` | 소비 패턴 분석 |

### REST API (Chart.js JSON)

| Method | URL | 설명 | 응답 |
|--------|-----|------|------|
| GET | `/api/expenses/category-summary` | 카테고리별 지출 비율 | `{labels, values}` |
| GET | `/api/expenses/monthly-summary` | 월별 총지출 추이 (6개월) | `{labels, values}` |
| GET | `/api/expenses/category-trend` | 카테고리별 월별 추이 | `{labels, datasets}` |
| GET | `/api/expenses/budget-vs-actual` | 예산 vs 실제 비교 | `{labels, datasets}` |

## 카테고리 분류

| 카테고리 | 한글명 | 설명 |
|----------|--------|------|
| FOOD | 식비 | 식사, 간식, 커피, 식료품 |
| TRANSPORT | 교통 | 대중교통, 택시, 주유 |
| ENTERTAINMENT | 여가/오락 | 영화, 게임, 취미, 여행 |
| HOUSING | 주거/통신 | 월세, 관리비, 통신비 |
| SHOPPING | 쇼핑 | 의류, 생필품, 전자기기 |
| HEALTH | 의료/건강 | 병원, 약국, 헬스장 |
| EDUCATION | 교육/자기개발 | 강의, 도서, 자격증 |
| OTHER | 기타 | 경조사, 기부 등 |

## 테스트

26개 단위 테스트 (JUnit 5 + Mockito + AssertJ, BDD 스타일)

| 테스트 클래스 | 테스트 수 | 대상 |
|--------------|----------|------|
| ExpenseServiceTest | 7 | 지출 CRUD, 필터, 예외 처리 |
| BudgetServiceTest | 10 | 대시보드 요약, 예산 비교, 알림, 저장, 복사 |
| BudgetVsActualTest | 5 | 퍼센티지 계산, 색상 판별 (3단계) |
| CategoryChangeDtoTest | 4 | 증감/중립 판별, 변화율 계산 |

## 아키텍처

```
[Browser] → [Thymeleaf Controller] → [Service] → [Repository] → [H2 DB]
                                          ↑
[Chart.js] → [REST API Controller] ──────┘
```

- **Controller**: Thymeleaf 뷰 렌더링 + REST API (차트 데이터)
- **Service**: 비즈니스 로직, 집계 연산, `@Transactional` 관리
- **Repository**: Spring Data JPA + JPQL 커스텀 쿼리
- **단일 사용자**: 인증/인가 없이 심플하게 구성 (포트폴리오 프로젝트)

## CI/CD

GitHub Actions를 사용한 자동화 파이프라인

| 워크플로우 | 트리거 | 동작 |
|-----------|--------|------|
| **CI - Build & Test** | `push`, `pull_request` (main) | Gradle 빌드 + 26개 단위 테스트 실행 |
| **Deploy to EC2** | 수동 (Actions 탭 → Run workflow) | 빌드 → Docker 이미지 생성 → EC2 배포 |

### 수동 배포 방법
1. GitHub → **Actions** 탭 클릭
2. 좌측에서 **"Deploy to EC2"** 선택
3. **"Run workflow"** 버튼 클릭
4. 배포 완료 후 `https://noaats.duckdns.org` 접속

### GitHub Secrets 설정 (배포 시 필요)

| Secret 이름 | 설명 |
|-------------|------|
| `EC2_HOST` | EC2 퍼블릭 IP 또는 도메인 |
| `EC2_USERNAME` | SSH 사용자명 (예: `ec2-user`) |
| `EC2_SSH_KEY` | EC2 SSH 프라이빗 키 (PEM 내용 전체) |

Settings → Secrets and variables → Actions에서 등록

## 배포

### 방법 1: Docker (권장)

```bash
# 이미지 빌드
docker build -t budget-app .

# 컨테이너 실행
docker run -d -p 8080:8080 -v budget-data:/app/data --name budget budget-app
```

### 방법 2: JAR 직접 실행

```bash
# JAR 빌드
./gradlew bootJar

# 실행 (prod 프로파일)
java -Dspring.profiles.active=prod -Xmx512m -jar build/libs/budget-0.0.1-SNAPSHOT.jar
```

### AWS EC2 배포 (t3.micro)

**1. EC2 사전 설정**
```bash
# 스왑 메모리 추가 (t3.micro 1GB RAM 보완)
sudo fallocate -l 2G /swapfile
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile
echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
```

**2-A. Docker로 배포**
```bash
# Docker 설치 (Amazon Linux 2023)
sudo yum install -y docker
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker ec2-user

# 프로젝트 클론 및 빌드
git clone <repository-url>
cd noaats
docker build -t budget-app .
docker run -d -p 8080:8080 -v budget-data:/app/data --name budget budget-app
```

**2-B. JAR로 배포**
```bash
# JDK 17 설치 (Amazon Linux 2023)
sudo yum install -y java-17-amazon-corretto-devel

# JAR 업로드 후 실행
nohup java -Dspring.profiles.active=prod -Xmx512m -jar budget-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
```

**3. 보안 그룹 설정**
- 인바운드 규칙에 TCP 포트 80(HTTP), 443(HTTPS) 허용

**4. Nginx + SSL 설정**
```bash
# Nginx 설치
sudo apt install -y nginx

# 리버스 프록시 설정
sudo tee /etc/nginx/sites-available/budget > /dev/null << 'CONF'
server {
    listen 80;
    server_name noaats.duckdns.org;
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
CONF

sudo ln -s /etc/nginx/sites-available/budget /etc/nginx/sites-enabled/
sudo rm /etc/nginx/sites-enabled/default
sudo nginx -t && sudo systemctl restart nginx

# Let's Encrypt SSL 인증서 발급
sudo apt install -y certbot python3-certbot-nginx
sudo certbot --nginx -d noaats.duckdns.org
```

- 접속: https://noaats.duckdns.org

## 문서

- [기획서 및 개발 문서](docs/PLAN.md)
- [개발 가이드](docs/guide.md)
- [AI 활용 기록](docs/ai-log.md)
