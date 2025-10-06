# Blog API

블로그 게시글 관리 및 카테고리 RESTful API

## 기술 스택

- Java 17 (LTS)
- Spring Boot 3.2.x
- Maven 3.9.x
- PostgreSQL 16+
- Spring Data JPA + Hibernate 6.x
- Swagger UI (OpenAPI 3)

## 주요 기능

### 카테고리 관리
- 카테고리 생성, 조회, 수정, 삭제
- 카테고리 이름으로 검색
- 카테고리별 게시글 수 조회

### 게시글 관리
- 게시글 생성, 조회, 수정, 삭제
- 게시글 상태 관리 (DRAFT, PUBLISHED, ARCHIVED)
- 카테고리별 게시글 조회
- 작성자별 게시글 조회
- 제목/내용으로 게시글 검색
- 페이지네이션 지원
- 기간별 게시글 조회

## API 엔드포인트

### 헬스체크
- `GET /api/health` - 애플리케이션 상태 확인
- `GET /api/` - API 기본 정보

### 카테고리 API
- `GET /api/categories` - 모든 카테고리 조회
- `GET /api/categories/{id}` - ID로 카테고리 조회
- `GET /api/categories/name/{name}` - 이름으로 카테고리 조회
- `POST /api/categories` - 카테고리 생성
- `PUT /api/categories/{id}` - 카테고리 수정
- `DELETE /api/categories/{id}` - 카테고리 삭제
- `GET /api/categories/search?name={name}` - 카테고리 검색

### 게시글 API
- `GET /api/posts` - 모든 게시글 조회
- `GET /api/posts/paged` - 페이지네이션으로 게시글 조회
- `GET /api/posts/{id}` - ID로 게시글 조회
- `GET /api/posts/published` - 발행된 게시글 조회
- `GET /api/posts/published/paged` - 페이지네이션으로 발행된 게시글 조회
- `GET /api/posts/status/{status}` - 상태별 게시글 조회
- `GET /api/posts/category/{categoryId}` - 카테고리별 게시글 조회
- `GET /api/posts/author/{author}` - 작성자별 게시글 조회
- `GET /api/posts/search/title?title={title}` - 제목으로 게시글 검색
- `POST /api/posts` - 게시글 생성
- `PUT /api/posts/{id}` - 게시글 수정
- `PATCH /api/posts/{id}/status` - 게시글 상태 변경
- `DELETE /api/posts/{id}` - 게시글 삭제
- `GET /api/posts/date-range?startDate={startDate}&endDate={endDate}` - 기간별 게시글 조회

## 로컬 실행 방법

### 1. PostgreSQL 데이터베이스 설정
```sql
CREATE DATABASE blogapi;
```

### 2. 환경 변수 설정
```bash
export USER=your_db_username
```

### 3. 애플리케이션 실행
```bash
mvn spring-boot:run
```

### 4. API 문서 확인
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

## Heroku 배포

### 1. Heroku CLI 설치 및 로그인
```bash
heroku login
```

### 2. Heroku 앱 생성
```bash
heroku create your-app-name
```

### 3. PostgreSQL 애드온 추가
```bash
heroku addons:create heroku-postgresql:hobby-dev
```

### 4. 배포
```bash
git add .
git commit -m "Initial commit"
git push heroku main
```

### 5. 애플리케이션 확인
```bash
heroku open
```

## 데이터베이스 스키마

### Categories 테이블
- id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
- name (VARCHAR(50), NOT NULL, UNIQUE)
- description (VARCHAR(200))
- created_at (TIMESTAMP, NOT NULL)
- updated_at (TIMESTAMP)

### Posts 테이블
- id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
- title (VARCHAR(200), NOT NULL)
- content (TEXT, NOT NULL)
- summary (VARCHAR(500))
- author (VARCHAR(100), NOT NULL)
- status (VARCHAR(20), NOT NULL, DEFAULT 'DRAFT')
- created_at (TIMESTAMP, NOT NULL)
- updated_at (TIMESTAMP)
- category_id (BIGINT, FOREIGN KEY)

## 예제 요청

### 카테고리 생성
```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "기술",
    "description": "프로그래밍과 기술 관련 게시글"
  }'
```

### 게시글 생성
```bash
curl -X POST http://localhost:8080/api/posts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Spring Boot 시작하기",
    "content": "Spring Boot를 사용한 웹 애플리케이션 개발에 대해 알아보겠습니다.",
    "summary": "Spring Boot 기초 가이드",
    "author": "개발자",
    "status": "PUBLISHED"
  }'
```

## 라이선스

MIT License
