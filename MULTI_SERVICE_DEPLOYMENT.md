# 🚀 Heroku 멀티서비스 배포 가이드

## 📋 **프로젝트 구조**

```
02-blog-api/
├── common/                      # 공통 모듈
│   ├── src/
│   │   └── main/
│   │       └── java/com/example/common/
│   │           ├── entity/BaseEntity.java
│   │           ├── dto/ApiResponse.java
│   │           └── controller/BaseHealthController.java
│   └── pom.xml
├── blog-service/                # 블로그 서비스
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/blogapi/
│   │       └── resources/
│   │           ├── application.yml
│   │           ├── application-heroku.yml
│   │           └── application-h2.yml
│   ├── pom.xml
│   ├── Procfile
│   ├── system.properties
│   └── app.json
├── user-service/                # 사용자 서비스
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/userapi/
│   │       └── resources/
│   │           ├── application.yml
│   │           ├── application-heroku.yml
│   │           └── application-h2.yml
│   ├── pom.xml
│   ├── Procfile
│   ├── system.properties
│   └── app.json
└── pom-parent.xml              # 부모 POM
```

## 🎯 **각 서비스의 역할**

### 1. **Common Module**
- 공통 엔티티 (BaseEntity)
- 공통 DTO (ApiResponse)
- 공통 컨트롤러 (BaseHealthController)

### 2. **Blog Service**
- 포트: 8080 (로컬), $PORT (Heroku)
- 데이터베이스: blogapi
- API 엔드포인트:
  - `/api/health` - 헬스체크
  - `/api/categories` - 카테고리 관리
  - `/api/posts` - 게시글 관리
  - `/swagger-ui.html` - API 문서

### 3. **User Service**
- 포트: 8081 (로컬), $PORT (Heroku)
- 데이터베이스: userapi
- API 엔드포인트:
  - `/api/health` - 헬스체크
  - `/api/users` - 사용자 관리
  - `/swagger-ui.html` - API 문서

## 🔧 **로컬 빌드 및 실행**

### 1. **Common 모듈 빌드**
```bash
cd common
mvn clean install
cd ..
```

### 2. **Blog Service 실행**
```bash
cd blog-service
mvn spring-boot:run -Dspring-boot.run.profiles=h2
# 또는
java -jar target/blog-service.jar --spring.profiles.active=h2
```

### 3. **User Service 실행**
```bash
cd user-service
mvn spring-boot:run -Dspring-boot.run.profiles=h2
# 또는
java -jar target/user-service.jar --spring.profiles.active=h2
```

## 🌐 **Heroku 배포 방법**

### **방법 1: Git Subtree를 사용한 배포 (권장)**

#### 1단계: Heroku 앱 생성

```bash
# Blog Service 앱 생성
heroku create blog-api-zerobase

# User Service 앱 생성
heroku create user-api-zerobase
```

#### 2단계: PostgreSQL 애드온 추가

```bash
# Blog Service
heroku addons:create heroku-postgresql:essential-0 --app blog-api-zerobase

# User Service
heroku addons:create heroku-postgresql:essential-0 --app user-api-zerobase
```

#### 3단계: Git Remote 추가

```bash
# Blog Service
git remote add heroku-blog https://git.heroku.com/blog-api-zerobase.git

# User Service
git remote add heroku-user https://git.heroku.com/user-api-zerobase.git
```

#### 4단계: 서비스별 배포

**Blog Service 배포:**
```bash
# Common 모듈 먼저 빌드
cd common && mvn clean install && cd ..

# Blog Service 배포
git subtree push --prefix blog-service heroku-blog main
```

**User Service 배포:**
```bash
# Common 모듈 먼저 빌드 (이미 했다면 스킵)
cd common && mvn clean install && cd ..

# User Service 배포
git subtree push --prefix user-service heroku-user main
```

#### 5단계: 배포 확인

```bash
# Blog Service 상태 확인
heroku info --app blog-api-zerobase
curl https://blog-api-zerobase-xxxxx.herokuapp.com/api/health

# User Service 상태 확인
heroku info --app user-api-zerobase
curl https://user-api-zerobase-xxxxx.herokuapp.com/api/health
```

### **방법 2: Heroku Dashboard를 통한 배포**

#### 1단계: GitHub 저장소 푸시
```bash
git add .
git commit -m "Multi-service architecture"
git push origin main
```

#### 2단계: Blog Service 배포
1. Heroku Dashboard 접속
2. "New" → "Create new app"
3. 앱명: `blog-api-zerobase`
4. "Deploy" 탭 → "GitHub" 연결
5. 저장소 연결
6. "Deploy Branch" 클릭
7. **중요**: Heroku Settings에서 빌드팩 설정:
   - Add buildpack: `heroku/java`
   - Config Vars에서 `PROJECT_PATH=blog-service` 추가

#### 3단계: User Service 배포
1. 같은 방법으로 새 앱 생성: `user-api-zerobase`
2. GitHub 저장소 연결
3. "Deploy Branch" 클릭
4. **중요**: Heroku Settings에서:
   - Add buildpack: `heroku/java`
   - Config Vars에서 `PROJECT_PATH=user-service` 추가

### **방법 3: Heroku Monorepo Buildpack 사용**

#### 1단계: Buildpack 설정

```bash
# Blog Service
heroku buildpacks:clear --app blog-api-zerobase
heroku buildpacks:add https://github.com/lstoll/heroku-buildpack-monorepo --app blog-api-zerobase
heroku buildpacks:add heroku/java --app blog-api-zerobase
heroku config:set APP_BASE=blog-service --app blog-api-zerobase

# User Service
heroku buildpacks:clear --app user-api-zerobase
heroku buildpacks:add https://github.com/lstoll/heroku-buildpack-monorepo --app user-api-zerobase
heroku buildpacks:add heroku/java --app user-api-zerobase
heroku config:set APP_BASE=user-service --app user-api-zerobase
```

#### 2단계: 배포

```bash
git push heroku-blog main
git push heroku-user main
```

## 📊 **배포 후 확인**

### Health Check

```bash
# Blog Service
curl https://blog-api-zerobase-xxxxx.herokuapp.com/api/health

# User Service
curl https://user-api-zerobase-xxxxx.herokuapp.com/api/health
```

### API 테스트

**Blog Service:**
```bash
# 카테고리 조회
curl https://blog-api-zerobase-xxxxx.herokuapp.com/api/categories

# 게시글 조회
curl https://blog-api-zerobase-xxxxx.herokuapp.com/api/posts
```

**User Service:**
```bash
# 사용자 조회
curl https://user-api-zerobase-xxxxx.herokuapp.com/api/users
```

### Swagger UI 접속

- **Blog Service**: `https://blog-api-zerobase-xxxxx.herokuapp.com/swagger-ui.html`
- **User Service**: `https://user-api-zerobase-xxxxx.herokuapp.com/swagger-ui.html`

## 🔍 **로그 확인**

```bash
# Blog Service 로그
heroku logs --tail --app blog-api-zerobase

# User Service 로그
heroku logs --tail --app user-api-zerobase
```

## 🔄 **업데이트 및 재배포**

```bash
# 코드 변경 후
git add .
git commit -m "Update services"

# Blog Service 재배포
git subtree push --prefix blog-service heroku-blog main

# User Service 재배포
git subtree push --prefix user-service heroku-user main
```

## 💡 **주요 특징**

### ✅ **독립적인 배포**
- 각 서비스는 독립적인 Heroku 앱으로 배포됨
- 한 서비스의 변경이 다른 서비스에 영향을 주지 않음

### ✅ **독립적인 데이터베이스**
- Blog Service: 자체 PostgreSQL 인스턴스
- User Service: 자체 PostgreSQL 인스턴스

### ✅ **독립적인 환경 변수**
- 각 서비스는 자체 환경 변수를 가짐
- Heroku Dashboard에서 개별 설정 가능

### ✅ **독립적인 스케일링**
- 각 서비스의 dyno를 독립적으로 스케일링 가능
```bash
# Blog Service 스케일링
heroku ps:scale web=2 --app blog-api-zerobase

# User Service 스케일링
heroku ps:scale web=1 --app user-api-zerobase
```

## 🛠️ **트러블슈팅**

### 문제: 빌드 실패

**해결책:**
1. Common 모듈이 먼저 빌드되었는지 확인
2. `pom.xml` 의존성 확인
3. Java 버전 확인 (system.properties)

### 문제: DATABASE_URL 인식 안됨

**해결책:**
```bash
# PostgreSQL 애드온 확인
heroku addons --app blog-api-zerobase

# DATABASE_URL 확인
heroku config:get DATABASE_URL --app blog-api-zerobase
```

### 문제: Git Subtree 푸시 실패

**해결책:**
```bash
# 강제 푸시
git push heroku-blog `git subtree split --prefix blog-service main`:main --force
```

## 🎉 **완료!**

이제 멀티서비스 아키텍처로 각 서비스가 독립적으로 배포되고 운영됩니다!

### 📚 **다음 단계**
- API Gateway 추가
- 서비스 간 통신 구현
- 로드 밸런싱 설정
- 모니터링 및 로깅 통합

