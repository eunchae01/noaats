# 노아의 가계부

직장 3년차 "노아"를 위한 지출 관리 & 예산 분석 웹 애플리케이션

## 기술 스택

| 영역 | 기술 |
|------|------|
| Backend | Java 17, Spring Boot 4.0.2, Spring Data JPA |
| Frontend | Thymeleaf, Bootstrap 5, Chart.js 4 |
| Database | H2 (File 모드) |
| Build | Gradle 9.3 |

## 실행 방법

### 사전 요구사항
- Java 17 이상

### 로컬 실행

```bash
cd budget
./gradlew bootRun
```

브라우저에서 http://localhost:8080 접속

### H2 콘솔
- http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/budgetdb`
- Username: `sa` / Password: (없음)

## 주요 기능

- **대시보드**: 이번 달 지출 요약, 카테고리별 비율 차트, 예산 알림
- **지출 관리**: 지출 기록 추가/수정/삭제, 카테고리별 필터링
- **예산 관리**: 카테고리별 월 예산 설정, 예산 대비 지출 진행바
- **소비 패턴**: 월별 지출 추이, 카테고리별 트렌드 분석

<!-- TODO: 스크린샷 추가 -->
<!-- TODO: 프로젝트 구조 상세 -->
<!-- TODO: API 명세 -->

## 문서

- [기획서 및 개발 문서](docs/PLAN.md)
