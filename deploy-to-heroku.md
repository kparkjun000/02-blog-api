# Heroku 배포 가이드

## 🚀 Heroku 배포 단계별 가이드

### 1. Heroku CLI 설치 및 로그인

```bash
# Heroku CLI 설치 (이미 설치됨)
heroku --version

# Heroku 로그인
heroku login
# 브라우저가 열리면 로그인 진행

# 로그인 확인
heroku auth:whoami
```

### 2. Heroku 앱 생성

```bash
# 새로운 Heroku 앱 생성
heroku create blog-api-demo-2025

# 또는 기존 앱 사용
heroku git:remote -a your-app-name
```

### 3. PostgreSQL 애드온 추가

```bash
# PostgreSQL 애드온 추가 (무료 플랜)
heroku addons:create heroku-postgresql:hobby-dev

# 데이터베이스 URL 확인
heroku config:get DATABASE_URL
```

### 4. 환경 변수 설정

```bash
# Java 옵션 설정
heroku config:set JAVA_OPTS="-Xmx512m -Xms512m"

# Spring 프로파일 설정
heroku config:set SPRING_PROFILES_ACTIVE=heroku
```

### 5. 배포 실행

```bash
# Git 푸시로 배포
git push heroku master

# 또는 main 브랜치 사용시
git push heroku main
```

### 6. 애플리케이션 확인

```bash
# 애플리케이션 열기
heroku open

# 로그 확인
heroku logs --tail

# 헬스체크
curl https://your-app-name.herokuapp.com/api/health
```

## 📋 배포 체크리스트

### ✅ 준비 완료된 파일들

- ✅ `Procfile` - Heroku 실행 설정
- ✅ `app.json` - 앱 설정 및 애드온
- ✅ `application-heroku.yml` - Heroku 전용 설정
- ✅ `pom.xml` - Maven 설정 (executable jar)
- ✅ Git 저장소 초기화 완료

### 🔧 설정 확인사항

1. **Java 빌드팩**: 자동으로 감지됨
2. **PostgreSQL**: hobby-dev 플랜으로 자동 설정
3. **포트**: $PORT 환경변수 사용
4. **데이터베이스**: DATABASE_URL 자동 연결

## 🎯 배포 후 테스트

### API 엔드포인트 테스트

```bash
# 헬스체크
curl https://your-app-name.herokuapp.com/api/health

# 카테고리 생성
curl -X POST https://your-app-name.herokuapp.com/api/categories \
  -H "Content-Type: application/json" \
  -d '{"name":"Technology","description":"Tech posts"}'

# 게시글 생성
curl -X POST https://your-app-name.herokuapp.com/api/posts \
  -H "Content-Type: application/json" \
  -d '{"title":"Hello Heroku","content":"Deployed successfully!","author":"Developer","status":"PUBLISHED"}'
```

### Swagger UI 접속

```
https://your-app-name.herokuapp.com/swagger-ui.html
```

## 🛠️ 문제 해결

### 일반적인 문제들

1. **빌드 실패**: `heroku logs --tail`로 로그 확인
2. **데이터베이스 연결 실패**: PostgreSQL 애드온 확인
3. **포트 바인딩 실패**: Procfile 확인
4. **메모리 부족**: JAVA_OPTS 조정

### 유용한 명령어들

```bash
# 앱 상태 확인
heroku ps

# 환경 변수 확인
heroku config

# 데이터베이스 접속
heroku pg:psql

# 앱 재시작
heroku restart

# 앱 삭제
heroku apps:destroy your-app-name
```

## 🎉 성공 확인

배포가 성공하면 다음을 확인할 수 있습니다:

1. ✅ `https://your-app-name.herokuapp.com/api/health` → 200 OK
2. ✅ `https://your-app-name.herokuapp.com/swagger-ui.html` → Swagger UI
3. ✅ 카테고리 생성/조회 → 201 Created, 200 OK
4. ✅ 게시글 생성/조회 → 201 Created, 200 OK
5. ✅ PostgreSQL 데이터베이스 정상 연결

## 📝 참고사항

- **무료 플랜**: 30분 비활성화 시 슬립 모드
- **데이터베이스**: hobby-dev 플랜 (10,000 행 제한)
- **빌드 시간**: 약 2-3분 소요
- **첫 배포**: 더 오래 걸릴 수 있음
