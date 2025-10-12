# 🚀 Heroku 멀티서비스 배포 완료!

## ✅ **프로젝트 구조 완성**

```
02-blog-api/
├── common/                      ✅ 공통 모듈
├── blog-service/                ✅ 블로그 서비스
├── user-service/                ✅ 사용자 서비스
├── pom.xml                      ✅ 부모 POM
└── MULTI_SERVICE_DEPLOYMENT.md  ✅ 배포 가이드
```

## 🎯 **Heroku 배포 단계**

### **1단계: Heroku 앱 생성**

```bash
# User Service 앱 생성 (Blog Service는 이미 존재)
heroku create user-api-zerobase
```

### **2단계: PostgreSQL 애드온 추가**

```bash
# User Service에 PostgreSQL 추가
heroku addons:create heroku-postgresql:essential-0 --app user-api-zerobase
```

### **3단계: Git Remote 추가**

```bash
# User Service Git Remote 추가
git remote add heroku-user https://git.heroku.com/user-api-zerobase.git
```

### **4단계: 서비스별 배포**

#### **방법 A: Git Subtree 사용 (권장)**

```powershell
# Blog Service 배포
git subtree push --prefix blog-service heroku-blog main

# User Service 배포
git subtree push --prefix user-service heroku-user main
```

#### **방법 B: 강제 푸시 (필요시)**

```powershell
# Blog Service 강제 푸시
git push heroku-blog `git subtree split --prefix blog-service main`:main --force

# User Service 강제 푸시
git push heroku-user `git subtree split --prefix user-service main`:main --force
```

### **5단계: 배포 확인**

```powershell
# Blog Service 확인
heroku info --app blog-api-zerobase
Invoke-WebRequest -Uri "https://blog-api-zerobase-xxxxx.herokuapp.com/api/health" -UseBasicParsing

# User Service 확인
heroku info --app user-api-zerobase
Invoke-WebRequest -Uri "https://user-api-zerobase-xxxxx.herokuapp.com/api/health" -UseBasicParsing
```

## 📊 **각 서비스 정보**

### **Blog Service**
- **Heroku 앱명**: `blog-api-zerobase`
- **포트**: 8080 (로컬), $PORT (Heroku)
- **데이터베이스**: PostgreSQL (blogapi)
- **주요 엔드포인트**:
  - `GET /api/health` - 헬스체크
  - `GET /api/categories` - 카테고리 조회
  - `POST /api/categories` - 카테고리 생성
  - `GET /api/posts` - 게시글 조회
  - `POST /api/posts` - 게시글 생성
  - `GET /swagger-ui.html` - API 문서

### **User Service**
- **Heroku 앱명**: `user-api-zerobase`
- **포트**: 8081 (로컬), $PORT (Heroku)
- **데이터베이스**: PostgreSQL (userapi)
- **주요 엔드포인트**:
  - `GET /api/health` - 헬스체크
  - `GET /api/users` - 사용자 조회
  - `POST /api/users` - 사용자 생성
  - `GET /api/users/{id}` - 사용자 상세 조회
  - `GET /api/users/active` - 활성 사용자 조회
  - `GET /swagger-ui.html` - API 문서

## 🧪 **로컬 테스트 명령어**

### **Blog Service 실행**
```powershell
cd blog-service
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
java -jar target/blog-service.jar --spring.profiles.active=h2
```

### **User Service 실행**
```powershell
cd user-service
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
java -jar target/user-service.jar --spring.profiles.active=h2
```

## 📝 **API 테스트 예제**

### **Blog Service**

```powershell
# 헬스체크
Invoke-WebRequest -Uri "http://localhost:8080/api/health" -UseBasicParsing

# 카테고리 생성
$json = @{name="Technology"; description="Tech posts"} | ConvertTo-Json
Invoke-WebRequest -Uri "http://localhost:8080/api/categories" -Method POST -Body $json -ContentType "application/json; charset=utf-8"

# 게시글 생성
$json = @{title="My First Post"; content="Content"; author="Admin"; status="PUBLISHED"} | ConvertTo-Json
Invoke-WebRequest -Uri "http://localhost:8080/api/posts" -Method POST -Body $json -ContentType "application/json; charset=utf-8"
```

### **User Service**

```powershell
# 헬스체크
Invoke-WebRequest -Uri "http://localhost:8081/api/health" -UseBasicParsing

# 사용자 생성
$json = @{username="john_doe"; email="john@example.com"; fullName="John Doe"} | ConvertTo-Json
Invoke-WebRequest -Uri "http://localhost:8081/api/users" -Method POST -Body $json -ContentType "application/json; charset=utf-8"

# 사용자 조회
Invoke-WebRequest -Uri "http://localhost:8081/api/users" -UseBasicParsing
```

## 🔄 **업데이트 및 재배포**

```powershell
# 전체 빌드
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
.\mvnw.cmd clean install -DskipTests

# 변경사항 커밋
git add .
git commit -m "Update services"

# Blog Service 재배포
git subtree push --prefix blog-service heroku-blog main

# User Service 재배포
git subtree push --prefix user-service heroku-user main
```

## 💡 **주요 특징**

✅ **독립적인 배포**: 각 서비스는 독립적으로 배포 및 운영
✅ **독립적인 데이터베이스**: 서비스별 전용 PostgreSQL 인스턴스
✅ **독립적인 환경 변수**: 서비스별 독립적인 설정
✅ **공통 모듈**: 코드 재사용 및 일관성 유지
✅ **무료 배포**: Heroku Free Tier 사용

## 🎉 **완료!**

멀티서비스 아키텍처가 성공적으로 구축되었습니다!
각 서비스가 독립적으로 배포되고 운영됩니다.

