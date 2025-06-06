# Handand - SNS 플랫폼

[![Java CI with Gradle](https://github.com/wocks1123/community-practice/actions/workflows/main.yml/badge.svg)](https://github.com/wocks1123/community-practice/actions/workflows/main.yml)
[![codecov](https://codecov.io/gh/wocks1123/Handand/graph/badge.svg?token=ISKZQHRVRU)](https://codecov.io/gh/wocks1123/Handand)

## 👋 소개

- 게시물을 등록하고 소통할 수 있는 커뮤니티 플랫폼
- Spring Boot와 헥사고날 아키텍처를 기반으로 구현했으며 깔끔한 도메인 중심 설계와 확장 가능한 멀티모듈 구조를 지향
- 사용자는 게시물을 작성하여 다른 사람들과 생각을 공유하고, 관심 있는 사용자 구독을 통해 지속적인 소통 제공

## 기술 스택

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
	![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
 ![CodeCov](https://img.shields.io/badge/codecov-%23ff0077.svg?style=for-the-badge&logo=codecov&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
	![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

## ✨ 주요 기능

### 📝 게시물 관리

- **게시물 작성** : 제목, 내용과 함께 게시물을 작성할 수 있다.
- **게시물 수정** : 본인이 작성한 게시물의 내용을 수정할 수 있다.
- **게시물 삭제** : 본인이 작성한 게시물을 삭제할 수 있다.
- **게시물 조회** : 등록된 게시물을 조회할 수 있다.
- **게시물 목록 조회** : 조건에 맞는 게시물들을 조회할 수 있다.
- **공개 설정** : 게시물의 공개 범위를 설정할 수 있다.

### 👥 회원 관리

- **회원 등록** : 이름과 프로필 이미지로 회원가입을 할 수 있다.
- **프로필 수정** : 프로필 이미지를 변경할 수 있다.
- **회원 조회** : 등록된 회원의 정보를 조회할 수 있다.

### 🔗 소셜 기능

- **사용자 구독** : 관심 있는 다른 사용자를 구독할 수 있다.(TODO)
- **피드 제공** : 구독한 사용자들의 게시물을 피드로 확인할 수 있다.(TODO)

## 🎯 프로젝트 목표

### 🏗️ 아키텍처 학습

- **헥사고날 아키텍처** : 포트-어댑터 패턴을 통한 의존성 역전과 도메인 격리
- **멀티모듈 구조** : 관심사의 분리와 모듈 간의 명확한 책임 경계 설정
- **도메인 주도 설계** : 비즈니스 로직을 도메인 계층에 집중시킬 수 있도록 설계

### 🧪 테스트 전략

- **테스트 주도 개발** : 단위 테스트와 통합 테스트를 통한 안정적인 코드 작성
- **테스트 커버리지** : jacoco, codecov 를 활용한 코드 커버리지 측정 및 품질 관리

<div align="center">
  <img src="https://codecov.io/gh/wocks1123/Handand/graphs/tree.svg?token=ISKZQHRVRU" alt="codecov"/>
  <br/>
  <em>테스트 커버리지</em>
</div>

### 🔧 개발 프로세스

- **CI** : GitHub Actions를 통한 자동화된 테스트 - jacoco 리포트 생성 및 codecov 업로드
- **코드 품질 관리** : SonarQube를 활용해 코드 품질 향상 - intellij plugin 활용

## 📁 모듈 구조

- **handand-api :** 외부 요청을 처리하는 프레젠테이션 계층
- **handand-domain :** 비즈니스 로직과 도메인 규칙이 집중된 핵심 계층
- infra까지 분리하기는 부담이 되서 보류 : 추후 분리 할지도
