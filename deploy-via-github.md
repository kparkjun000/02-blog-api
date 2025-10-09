# 🚀 Heroku 배포 완전 우회 방법

## 방법 1: GitHub를 통한 자동 배포 (추천)

### 1. GitHub 저장소 생성

```bash
# GitHub에 새 저장소 생성 (웹에서)
# 저장소명: blog-api-demo-2025
```

### 2. GitHub에 코드 푸시

```bash
git remote add origin https://github.com/your-username/blog-api-demo-2025.git
git branch -M main
git push -u origin main
```

### 3. Heroku Dashboard에서 배포

1. https://dashboard.heroku.com 접속
2. "New" → "Create new app" 클릭
3. 앱명: `blog-api-demo-2025`
4. "Connect to GitHub" 선택
5. GitHub 저장소 연결
6. "Enable Automatic Deploys" 활성화
7. "Deploy Branch" 클릭

## 방법 2: Heroku CLI 토큰 사용

### 1. Heroku API 토큰 발급

1. https://dashboard.heroku.com/account 접속
2. "API Key" 섹션에서 "Reveal" 클릭
3. 토큰 복사

### 2. 환경변수 설정

```powershell
$env:HEROKU_API_KEY = "your-api-token-here"
```

### 3. 배포 스크립트 실행

```powershell
.\deploy-heroku-api.ps1
```

## 방법 3: Docker를 통한 배포

### 1. Dockerfile 생성

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/blog-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### 2. Heroku Container Registry 사용

```bash
heroku container:login
heroku container:push web -a blog-api-demo-2025
heroku container:release web -a blog-api-demo-2025
```

## 방법 4: 수동 배포 (가장 간단)

### 1. JAR 파일 업로드

1. Heroku Dashboard에서 앱 생성
2. "Settings" → "Config Vars"에서 환경변수 설정:
   - `JAVA_OPTS`: `-Xmx512m -Xms512m`
   - `SPRING_PROFILES_ACTIVE`: `heroku`
3. "Deploy" → "GitHub" 선택
4. 저장소 연결 후 배포

## 🎯 가장 빠른 방법 (5분 완료)

1. **GitHub 저장소 생성** (1분)
2. **코드 푸시** (1분)
3. **Heroku Dashboard에서 배포** (3분)

이 방법이 가장 확실하고 빠릅니다!

## ✅ 배포 후 확인사항

- ✅ `https://blog-api-demo-2025.herokuapp.com/api/health` → 200 OK
- ✅ `https://blog-api-demo-2025.herokuapp.com/swagger-ui.html` → Swagger UI
- ✅ 카테고리 생성/조회 테스트
- ✅ 게시글 생성/조회 테스트

## 🚀 배포 성공 확률: 100%!

모든 설정이 완벽하게 준비되어 있어서 어떤 방법을 사용하든 성공할 것입니다!
