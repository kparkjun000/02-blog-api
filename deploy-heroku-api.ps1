# Heroku API를 통한 직접 배포 스크립트
# 이 스크립트는 Heroku API를 직접 호출하여 배포를 시도합니다

Write-Host "🚀 Heroku API를 통한 직접 배포 시작!" -ForegroundColor Green

# 1. Heroku API 토큰 확인 (환경변수에서)
$herokuToken = $env:HEROKU_API_KEY
if (-not $herokuToken) {
    Write-Host "❌ HEROKU_API_KEY 환경변수가 설정되지 않았습니다." -ForegroundColor Red
    Write-Host "Heroku Dashboard > Account Settings > API Key에서 토큰을 복사하고" -ForegroundColor Yellow
    Write-Host "환경변수 HEROKU_API_KEY로 설정해주세요." -ForegroundColor Yellow
    exit 1
}

$appName = "blog-api-demo-2025"
$headers = @{
    "Authorization" = "Bearer $herokuToken"
    "Accept" = "application/vnd.heroku+json; version=3"
    "Content-Type" = "application/json"
}

Write-Host "📱 앱 생성 중: $appName" -ForegroundColor Cyan

# 2. 앱 생성
try {
    $createAppBody = @{
        name = $appName
    } | ConvertTo-Json

    $createResponse = Invoke-RestMethod -Uri "https://api.heroku.com/apps" -Method POST -Headers $headers -Body $createAppBody
    Write-Host "✅ 앱 생성 성공: $($createResponse.web_url)" -ForegroundColor Green
} catch {
    if ($_.Exception.Response.StatusCode -eq 422) {
        Write-Host "⚠️ 앱이 이미 존재하거나 이름이 중복됩니다." -ForegroundColor Yellow
    } else {
        Write-Host "❌ 앱 생성 실패: $($_.Exception.Message)" -ForegroundColor Red
        exit 1
    }
}

# 3. PostgreSQL 애드온 추가
Write-Host "🗄️ PostgreSQL 애드온 추가 중..." -ForegroundColor Cyan
try {
    $addonBody = @{
        plan = "heroku-postgresql:hobby-dev"
    } | ConvertTo-Json

    $addonResponse = Invoke-RestMethod -Uri "https://api.heroku.com/apps/$appName/addons" -Method POST -Headers $headers -Body $addonBody
    Write-Host "✅ PostgreSQL 애드온 추가 성공" -ForegroundColor Green
} catch {
    Write-Host "⚠️ PostgreSQL 애드온 추가 실패: $($_.Exception.Message)" -ForegroundColor Yellow
}

# 4. 환경 변수 설정
Write-Host "🔧 환경 변수 설정 중..." -ForegroundColor Cyan
$configVars = @{
    "JAVA_OPTS" = "-Xmx512m -Xms512m"
    "SPRING_PROFILES_ACTIVE" = "heroku"
}

foreach ($key in $configVars.Keys) {
    try {
        $configBody = @{
            $key = $configVars[$key]
        } | ConvertTo-Json

        Invoke-RestMethod -Uri "https://api.heroku.com/apps/$appName/config-vars" -Method PATCH -Headers $headers -Body $configBody
        Write-Host "✅ 환경 변수 설정: $key = $($configVars[$key])" -ForegroundColor Green
    } catch {
        Write-Host "❌ 환경 변수 설정 실패: $key" -ForegroundColor Red
    }
}

# 5. Git 원격 저장소 추가
Write-Host "🔗 Git 원격 저장소 추가 중..." -ForegroundColor Cyan
try {
    git remote add heroku https://git.heroku.com/$appName.git
    Write-Host "✅ Git 원격 저장소 추가 성공" -ForegroundColor Green
} catch {
    Write-Host "⚠️ Git 원격 저장소 추가 실패: $($_.Exception.Message)" -ForegroundColor Yellow
}

# 6. 배포 실행
Write-Host "🚀 배포 실행 중..." -ForegroundColor Cyan
Write-Host "이제 다음 명령어를 실행하세요:" -ForegroundColor Yellow
Write-Host "git push heroku master" -ForegroundColor White

Write-Host "`n🎉 Heroku 설정 완료!" -ForegroundColor Green
Write-Host "앱 URL: https://$appName.herokuapp.com" -ForegroundColor Cyan
Write-Host "Swagger UI: https://$appName.herokuapp.com/swagger-ui.html" -ForegroundColor Cyan
