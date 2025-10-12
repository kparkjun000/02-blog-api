#!/bin/bash

# Heroku 멀티서비스 배포 스크립트

echo "🚀 Heroku 멀티서비스 배포 시작..."

# 색상 정의
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 1. Common 모듈 빌드
echo -e "${BLUE}📦 Common 모듈 빌드 중...${NC}"
cd common
mvn clean install -DskipTests
cd ..

# 2. Blog Service 배포
echo -e "${BLUE}📝 Blog Service 배포 중...${NC}"
cd blog-service

# Git 서브트리를 사용하여 blog-service만 배포
git subtree push --prefix blog-service heroku-blog main || \
git push heroku-blog `git subtree split --prefix blog-service main`:main --force

echo -e "${GREEN}✅ Blog Service 배포 완료!${NC}"
cd ..

# 3. User Service 배포
echo -e "${BLUE}👤 User Service 배포 중...${NC}"
cd user-service

# Git 서브트리를 사용하여 user-service만 배포
git subtree push --prefix user-service heroku-user main || \
git push heroku-user `git subtree split --prefix user-service main`:main --force

echo -e "${GREEN}✅ User Service 배포 완료!${NC}"
cd ..

echo -e "${GREEN}🎉 모든 서비스 배포 완료!${NC}"

# 4. 배포된 서비스 확인
echo -e "${YELLOW}📊 배포된 서비스 확인 중...${NC}"
echo -e "${BLUE}Blog Service:${NC}"
heroku info --app blog-api-zerobase | grep "Web URL"

echo -e "${BLUE}User Service:${NC}"
heroku info --app user-api-zerobase | grep "Web URL"

echo -e "${GREEN}✨ 배포 프로세스 완료!${NC}"

