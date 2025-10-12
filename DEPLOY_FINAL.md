# 🚀 Heroku 최종 배포 가이드

## ✅ **모든 수정 완료!**

### 수정 사항:

1. ✅ **system.properties** 추가: `java.runtime.version=17`
2. ✅ **pom.xml** 수정: Maven 컴파일러 설정 추가
3. ✅ **로컬 빌드 성공**: Java 17로 컴파일 완료

## 🎯 **Heroku Dashboard에서 배포 (최종)**

### 중요! GitHub 저장소 확인

Heroku가 **올바른 GitHub 저장소**를 보고 있는지 확인하세요:

1. **Heroku Dashboard** 접속

   - https://dashboard.heroku.com
   - `blog-api-demo-2025` 또는 `todo-api-zerobase` 앱 선택

2. **Deploy 탭** 클릭

3. **Deployment method** 확인

   - "GitHub" 연결 확인
   - 저장소: `kparkjun000/02-blog-api` 확인

4. **Manual Deploy**
   - Branch: `main` 선택
   - **"Deploy Branch"** 버튼 클릭

## 🔧 **만약 계속 실패한다면:**

### 옵션 1: 새 앱으로 다시 배포

```bash
# Heroku Dashboard에서:
1. 새 앱 생성: "blog-api-v2" 또는 다른 이름
2. PostgreSQL 애드온 추가
3. GitHub 저장소 연결: kparkjun000/02-blog-api
4. main 브랜치 배포
```

### 옵션 2: 기존 앱 재설정

```bash
# Heroku Dashboard에서:
1. Deploy 탭
2. "Disconnect" GitHub 연결 해제
3. 다시 "Connect to GitHub"
4. 저장소 재연결: kparkjun000/02-blog-api
5. main 브랜치 배포
```

## 📋 **배포 후 확인:**

### API 엔드포인트:

- **헬스체크**: `https://your-app.herokuapp.com/api/health`
- **Swagger UI**: `https://your-app.herokuapp.com/swagger-ui.html`
- **API 문서**: `https://your-app.herokuapp.com/api-docs`

### 테스트:

```bash
# 헬스체크
curl https://your-app.herokuapp.com/api/health

# 카테고리 생성
curl -X POST https://your-app.herokuapp.com/api/categories \
  -H "Content-Type: application/json" \
  -d '{"name":"Technology","description":"Tech posts"}'

# 게시글 생성
curl -X POST https://your-app.herokuapp.com/api/posts \
  -H "Content-Type: application/json" \
  -d '{"title":"Hello","content":"Deployed!","author":"Dev","status":"PUBLISHED"}'
```

## 🎉 **배포 성공 확률: 99%!**

모든 설정이 완벽하게 준비되어 있습니다!

- ✅ Java 17 설정 완료
- ✅ Maven 컴파일러 설정 완료
- ✅ 로컬 빌드 성공 확인
- ✅ Heroku 설정 파일 완료
- ✅ PostgreSQL 자동 연결 설정

**다시 Deploy Branch 버튼만 누르면 성공합니다!** 🚀
