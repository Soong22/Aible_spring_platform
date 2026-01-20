# 🚨 포즈 에스티메이션 모델 기반 범죄 예측 모니터링 시스템

<div align="center">

![YOLO Pose](https://img.shields.io/badge/YOLO-Pose-red)
![LSTM](https://img.shields.io/badge/LSTM-Model-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![FastAPI](https://img.shields.io/badge/FastAPI-Python-yellow)
![License](https://img.shields.io/badge/License-Education-lightgrey)

**YOLO Pose + LSTM 모델을 적용하여 CCTV 영상에서 이상 행동을 실시간으로 감지하고,<br/>웹 기반 관리자 페이지에서 시각화·관리하는 AI 기반 모니터링 시스템**

[목차](#목차) • [개요](#프로젝트-개요) • [기술스택](#기술-스택) • [주요기능](#핵심-기능) • [아키텍처](#시스템-아키텍처) • [설치](#설치-및-실행)

</div>

---

## 📌 목차
1. [프로젝트 개요](#프로젝트-개요)
2. [핵심 기능](#핵심-기능)
3. [시스템 아키텍처](#시스템-아키텍처)
4. [기술 스택](#기술-스택)
5. [주요 구현 내용](#주요-구현-내용)
6. [설치 및 실행](#설치-및-실행)
7. [팀 역할](#팀-역할)
8. [주요 성과](#주요-성과)

---

## 📖 프로젝트 개요

| 항목 | 내용 |
|------|------|
| **프로젝트명** | 포즈 에스티메이션 모델 기반 범죄 예측 모니터링 |
| **프로젝트 기간** | 2024.12.30 ~ 2025.02.13 (약 6주) |
| **참여 인원** | 8명 |
| **소속** | KT AIVLE School (24조) |
| **프로젝트 유형** | 빅 프로젝트 (실무 중심 교육) |
| **수상** | **KT AIVLE School Big Project Practical 상** 🏆 |

### 목표
- ✅ **포즈 에스티메이션 기반 이상 행동 실시간 탐지**
- ✅ **CCTV 실시간 스트리밍 및 AI 추론 결과 시각화**
- ✅ **범죄 이력 관리 및 위치 기반 정보 제공**
- ✅ **웹 기반 관리자 대시보드 구축**

---

## 🌟 핵심 기능

### 1️⃣ 이상 행동 탐지 (AI)
- **YOLO Pose**: 실시간 포즈 추출 (17개 주요 관절 인식)
- **LSTM 모델**: 시계열 데이터 기반 이상 행동 분류
- **FastAPI 서버**: 멀티스레딩 기반 실시간 추론 처리
- **응답 시간**: ~50ms (GPU 기준)

### 2️⃣ 실시간 영상 스트리밍
- **MJPEG 스트리밍**: CCTV/웹캠 영상 실시간 전송
- **응답 엔드포인트**: `/predict` - 실시간 영상 분석
- **프레임 레이트**: 30 FPS
- **멀티 소스**: 여러 CCTV 동시 처리 지원

### 3️⃣ 관리자 웹 페이지
- **이상 행동 탐지 페이지**: 실시간 MJPEG 스트리밍 및 탐지 결과 표시
- **CCTV 지도**: Google Maps 기반 광주광역시 CCTV 위치 시각화
- **경찰서 위치 지도**: 광주광역시 경찰서 위치 정보 제공
- **범죄 이력 관리**: 탐지된 이상 행동 기록 저장 및 조회
- **커뮤니티**: 공지사항 게시판, 댓글 기능
- **인터랙션**: 이미지 다운로드, 전체 화면 토글, 마커 클릭 기능

### 4️⃣ 위치 기반 시각화
- **Google Maps API** + **SVG 기반 인터랙티브 지도**
- **마커 클릭 시** 해당 CCTV의 MJPEG 스트리밍 영상 표시
- **실시간 알림**: 이상 행동 탐지 시 특정 경찰서에 알림 전송
- **위치 정보**: 지도 위의 마커에서 상세 정보 확인

---

## 🏗 시스템 아키텍처

```
┌─────────────────┐
│  CCTV / Webcam  │
└────────┬────────┘
         │ (Video Stream)
         ↓
┌─────────────────────────────┐
│    FastAPI 서버             │
│  (YOLO Pose + LSTM)        │
│  (멀티스레딩 처리)          │
└────────┬────────────────────┘
         │ (Detection Results)
         ↓
┌─────────────────────────────┐
│   Spring Boot API           │
│  (관리자 페이지 서버)       │
│  (DB 연동)                  │
└────────┬────────────────────┘
         │ (REST API)
         ↓
┌─────────────────────────────┐
│  관리자 웹 페이지           │
│  (Thymeleaf + JavaScript)  │
│  (Google Maps 시각화)       │
└─────────────────────────────┘
```

---

## 🛠 기술 스택

### Backend
| 기술 | 설명 | 사용 예 |
|------|------|--------|
| **Python 3.9+** | AI 모델 추론 서버 | OpenCV, NumPy 기반 이미지 처리 |
| **FastAPI** | 비동기 API 프레임워크 | 실시간 영상 스트리밍, AI 추론 |
| **OpenCV** | 컴퓨터 비전 라이브러리 | 이미지 인코딩, 전처리 |
| **Spring Boot 3.x** | Java 웹 애플리케이션 프레임워크 | REST API, 데이터 관리 |
| **Spring Security** | 인증/인가 | 로그인, 세션 관리 |
| **Spring Data JPA** | ORM | 데이터베이스 연동 |
| **Thymeleaf** | 템플릿 엔진 | 동적 HTML 생성 |

### Frontend
| 기술 | 설명 |
|------|------|
| **Thymeleaf** | 서버 템플릿 엔진 |
| **HTML5 / CSS3** | 마크업, 스타일링 |
| **JavaScript (Vanilla)** | 인터랙션, Ajax |
| **jQuery** | DOM 조작 (선택사항) |
| **SVG** | 벡터 그래픽 |
| **Google Maps API** | 인터랙티브 지도 |

### AI / ML
| 기술 | 설명 |
|------|------|
| **YOLO (YOLOv8 Pose)** | 실시간 포즈 추정 모델 |
| **LSTM** | 시계열 이상 행동 분류 모델 |
| **NumPy** | 수치 계산 라이브러리 |
| **PyTorch / TensorFlow** | 모델 추론 엔진 |

### DevOps / Infrastructure
| 기술 | 설명 |
|------|------|
| **Docker** | 컨테이너화 |
| **Docker Compose** | 멀티 컨테이너 관리 |
| **Azure** | 클라우드 배포 |
| **Git** | 버전 관리 |
| **GitHub** | 협업 플랫폼 |

### Database
| 기술 | 설명 |
|------|------|
| **MySQL / MariaDB** | 관계형 데이터베이스 |
| **JPA** | ORM 매핑 |

---

## 📂 주요 구현 내용

### Backend - Python + FastAPI

#### 📌 실시간 영상 처리 및 AI 추론
```python
# FastAPI에서 YOLO Pose + LSTM 기반 실시간 추론
@app.get("/predict")
async def predict():
    # 1. CCTV/웹캠에서 프레임 수신
    # 2. YOLO Pose로 포즈 추출
    # 3. LSTM으로 이상 행동 분류
    # 4. 결과 이미지를 MJPEG 형식으로 스트리밍
    # 5. 탐지 결과를 Spring Boot로 전송
```

**주요 구현:**
- **ThreadPoolExecutor + Queue**: 멀티스레딩 기반 동시 처리
- **MJPEG 스트리밍**: `image/jpeg` boundary 방식으로 실시간 영상 전송
- **OpenCV**: 이미지 인코딩 및 바운딩 박스 그리기
- **requests**: Spring Boot API와 HTTP 통신

#### 📌 이미지 처리 및 데이터 송수신
- NumPy 기반 이미지 배열 처리
- 포즈 추출 결과를 JSON 형식으로 변환
- Spring Boot로 탐지 결과 이미지 전송

### Backend - Spring Boot

#### 📌 Main 구조
```
src/main/java/com/aivle/platform/
├── PlatformApplication.java (Spring Boot 메인)
├── config/
│   ├── SecurityConfig.java (Spring Security 설정)
│   ├── WebConfig.java (CORS, 인터셉터)
│   ├── WebSocketConfig.java (실시간 알림)
│   └── CustomSessionExpiredStrategy.java (세션 만료)
├── controller/
│   ├── HomeController.java (홈 페이지)
│   ├── MemberController.java (회원 관리)
│   ├── BoardController.java (게시판)
│   ├── CommentController.java (댓글)
│   ├── NotificationController.java (알림)
│   ├── ApiController.java (FastAPI 연동)
│   └── ErrorController.java (에러 처리)
├── service/
│   ├── MemberService.java
│   ├── BoardService.java
│   ├── CommentService.java
│   ├── NotificationService.java
│   └── ImageService.java
├── domain/ (Entity)
│   ├── Member.java
│   ├── Board.java
│   ├── Comment.java
│   ├── Notification.java
│   ├── Image.java
│   └── PoliceUnit.java
├── dto/
│   ├── request/ (요청 DTO)
│   └── response/ (응답 DTO)
├── repository/ (Data Access)
│   ├── MemberRepository.java
│   ├── BoardRepository.java
│   ├── ImageRepository.java
│   ├── NotificationRepository.java
│   └── PoliceUnitRepository.java
└── exception/ (예외 처리)
```

#### 📌 핵심 기능별 구현

**1. 인증/인가 (Spring Security)**
```java
// SecurityConfig.java
- 로그인/로그아웃 기능
- 세션 기반 인증
- 커스텀 로그인 페이지
- CSRF 보호
```

**2. 이미지 관리 (MultipartFile)**
```java
// ImageService.java
- FastAPI에서 전송받은 탐지 이미지 저장
- 파일 시스템에 저장 (Java NIO Files)
- 이미지 조회 및 다운로드
- CopyOnWriteArrayList로 스레드 안전성 보장
```

**3. 알림 시스템 (WebSocket + Notification)**
```java
// NotificationController.java
- 이상 행동 탐지 시 실시간 알림
- 경찰서별 알림 전송
- 알림 이력 저장
```

**4. 게시판 (Board + Comment)**
```java
// BoardController.java, CommentController.java
- 공지사항 작성/조회/수정/삭제
- 댓글 기능
- 페이지네이션
- 검색 기능
```

**5. 지도 API (Google Maps)**
```java
// HomeController.java (CCTV Map & Police Map)
@GetMapping("/cctv/google_map")
- CCTV 위치 데이터 JSON 반환
- 경찰서 위치 데이터 JSON 반환
- 마커 클릭 시 MJPEG 스트리밍 URL 전달
```

### Frontend - Thymeleaf + JavaScript

#### 📌 주요 HTML 페이지

**1. 홈 페이지 (`index.html`)**
- 프로젝트 소개
- 네비게이션 메뉴

**2. CCTV 대시보드 (`cctv/cctv_dashboard.html`)**
- MJPEG 스트리밍 영상 표시
- 이상 행동 탐지 결과 실시간 업데이트
- 이미지 다운로드 버튼
- 전체 화면 토글

**3. Google Maps 기반 위치 시각화**
- **CCTV 지도** (`cctv/google_map.html`)
  - 광주광역시 CCTV 위치 마커
  - 마커 클릭 시 MJPEG 스트리밍
  
- **경찰서 지도** (`region/police_map.html`)
  - 광주광역시 경찰서 위치 마커
  - 경찰서 상세 정보

**4. 게시판 (`board/boards.html`)**
- 공지사항 목록 조회
- 게시물 작성/수정/삭제
- 댓글 기능

**5. 회원 관리 (`member/login.html`)**
- 로그인/회원가입
- 프로필 조회

#### 📌 JavaScript 주요 구현

**1. 실시간 영상 스트리밍**
```javascript
// cctv_dashboard.js
// MJPEG 스트리밍 URL 설정
// <img> 태그에 `/predict` 엔드포인트 연결
// 자동 새로고침으로 최신 프레임 표시
```

**2. Ajax 기반 주기적 데이터 갱신**
```javascript
// 일정 시간마다 탐지 결과 데이터 조회
// DOM 업데이트
// 실시간 알림 표시
```

**3. Google Maps 인터랙션**
```javascript
// google_map.js
- 마커 클릭 시 드롭다운 업데이트
- MJPEG 스트리밍 영상 표시
- 마커 정보 팝업
```

**4. 이미지 다운로드**
```javascript
// 탐지 결과 이미지 다운로드
// URL에서 이미지 생성 및 다운로드
```

---

## 설치 및 실행

### 사전 요구사항
```bash
# Python 3.9+
# Java 17+
# Docker (선택사항)
# MySQL 8.0+
```

### Backend 설정

#### 1️⃣ Python + FastAPI 서버 실행
```bash
cd ai_server  # 또는 해당 경로

# 가상 환경 생성
python -m venv venv
source venv/bin/activate  # Linux/Mac
# 또는
venv\Scripts\activate  # Windows

# 필수 라이브러리 설치
pip install fastapi uvicorn opencv-python numpy torch yolov8

# FastAPI 서버 실행
python main.py
# 또는
uvicorn main:app --reload --port 8000
```

#### 2️⃣ Spring Boot 서버 실행
```bash
cd Aible_spring_platform

# Maven 또는 Gradle 빌드
gradle build
# 또는
mvn clean install

# 애플리케이션 실행
gradle bootRun
# 또는
mvn spring-boot:run

# 기본 포트: 8080
```

#### 3️⃣ 데이터베이스 설정
```bash
# MySQL 접속
mysql -u root -p

# 데이터베이스 생성
CREATE DATABASE aivle_platform;
USE aivle_platform;

# application.properties에서 DB 설정
spring.datasource.url=jdbc:mysql://localhost:3306/aivle_platform
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

### Docker를 이용한 배포 (선택사항)
```bash
# Docker Compose 실행
docker-compose up -d

# 로그 확인
docker-compose logs -f
```

### 접속 방법
- **관리자 페이지**: `http://localhost:8080`
- **FastAPI 문서**: `http://localhost:8000/docs`
- **CCTV 지도**: `http://localhost:8080/cctv/google_map`

---

## 👥 팀 역할

### 📌 **JoSooAh** - Backend/Frontend 개발 담당 ⭐

#### 🔧 백엔드 개발 (Python FastAPI + Java Spring Boot)

**1. FastAPI 서버에서 실시간 영상 수신 및 처리**
- **역할**: `/predict` 엔드포인트에서 웹캠/CCTV 영상을 받아서 AI 모델로 처리한 결과를 MJPEG 형식으로 실시간 스트리밍
- **기술**: FastAPI + Uvicorn으로 비동기 서버 구성
- **로직**:
  1. 웹캠에서 프레임 수신
  2. YOLO Pose로 포즈 추출, LSTM으로 이상 행동 분류
  3. OpenCV로 감지 결과를 이미지에 바운딩 박스로 그려서 표시
  4. 결과 이미지를 MJPEG 형식으로 인코딩하여 스트리밍

**2. 고성능 멀티스레딩 처리로 동시에 여러 프레임 처리**
- **역할**: 프레임을 빠르게 처리하기 위해 여러 스레드에서 동시 처리
- **기술**: Python ThreadPoolExecutor + Queue
- **로직**:
  1. 들어오는 프레임들을 Queue에 저장
  2. ThreadPoolExecutor가 여러 스레드에서 동시에 AI 모델 추론 실행
  3. 성능: 약 50ms의 응답 시간 달성

**3. Spring Boot에서 탐지 결과 이미지 저장 및 관리**
- **역할**: FastAPI에서 보내온 탐지 결과 이미지를 서버의 파일 시스템에 안전하게 저장
- **기술**: MultipartFile (Spring), Java NIO Files, CopyOnWriteArrayList
- **로직**:
  1. FastAPI에서 POST 요청으로 이미지 파일 수신
  2. MultipartFile로 받은 파일을 검증
  3. Java NIO Files로 파일 시스템에 저장 (빠르고 효율적)
  4. CopyOnWriteArrayList로 저장된 이미지 목록을 스레드 안전하게 관리 (멀티스레드 환경에서도 안전)
  5. 필요시 이미지를 다운로드할 수 있도록 제공

---

#### 🎨 프론트엔드 개발 (Thymeleaf + JavaScript)

**1. 공통 레이아웃 구성 (모든 페이지의 기본 틀)**
- **역할**: 헤더, 네비게이션, 푸터 등 모든 페이지에서 반복되는 부분을 한 번만 작성하고 재사용
- **기술**: Thymeleaf 템플릿 엔진의 `th:replace` 사용
- **로직**:
  1. `fragments/header.html`, `fragments/footer.html` 등 공통 파일 작성
  2. 각 페이지에서 `<div th:replace="~{fragments/header :: header}"></div>` 형태로 포함
  3. 전체 디자인 일관성 유지, 수정 시 한 곳만 수정하면 모든 페이지에 반영

**2. CCTV 대시보드 페이지 - 실시간 영상 스트리밍**
- **페이지**: `cctv/cctv_dashboard.html`
- **역할**: FastAPI에서 보내는 실시간 MJPEG 스트리밍 영상을 웹 브라우저에 표시
- **기술**: HTML `<img>` 태그, JavaScript
- **로직**:
  ```html
  <!-- FastAPI의 /predict 엔드포인트 URL을 img src에 설정 -->
  <img src="/api/predict" />
  <!-- MJPEG는 자동으로 계속 새로운 프레임을 갱신 -->
  ```
- **추가 기능**:
  - ⬇️ 이미지 다운로드: 감지 결과 이미지를 로컬에 저장
  - 🖥️ 전체 화면 토글: CCTV 영상을 전체 화면으로 확대 표시

**3. CCTV 위치 지도 - Google Maps 기반 인터랙티브 지도**
- **페이지**: `cctv/google_map.html`
- **역할**: 광주광역시의 모든 CCTV 위치를 지도에 표시, 마커 클릭 시 해당 CCTV 실시간 영상 표시
- **기술**: Google Maps API, SVG, JavaScript
- **로직**:
  1. Spring Boot에서 CCTV 좌표 데이터를 JSON으로 제공
  2. JavaScript로 Google Maps에 마커 생성 (각 CCTV마다 하나의 마커)
  3. 사용자가 마커 클릭
  4. `onClick` 이벤트에서 해당 CCTV ID를 추출
  5. 드롭다운 메뉴를 자동으로 그 CCTV로 변경
  6. MJPEG 스트리밍 영상을 자동으로 전환

**4. Ajax 기반 실시간 데이터 갱신**
- **역할**: 페이지 새로고침 없이 최신 탐지 결과를 자동으로 가져와서 화면 업데이트
- **기술**: JavaScript Ajax, jQuery 또는 Fetch API
- **로직**:
  ```javascript
  // 3초마다 서버에서 최신 탐지 결과 가져오기
  setInterval(function() {
    // GET 요청으로 최신 데이터 조회
    fetch('/api/detection-results')
      .then(response => response.json())
      .then(data => {
        // 화면의 결과 영역 업데이트
        updateDetectionUI(data);
      });
  }, 3000);
  ```
- **사용되는 페이지**:
  - CCTV 대시보드: 감지 결과 실시간 표시
  - 게시판: 새 게시물 알림
  - 알림 영역: 새 알림 표시

---

#### 📋 구현한 주요 페이지

| 페이지 | 설명 | 주요 기능 |
|--------|------|---------|
| **index.html** | 홈 페이지 | 프로젝트 소개, 네비게이션 |
| **cctv/cctv_dashboard.html** | CCTV 영상 뷰어 | 실시간 MJPEG 스트리밍, 이미지 다운로드 |
| **cctv/google_map.html** | CCTV 위치 지도 | 지도에서 CCTV 마커 클릭 → 실시간 영상 전환 |
| **region/police_map.html** | 경찰서 위치 지도 | 광주시 경찰서 위치 정보 표시 |
| **board/boards.html** | 공지사항 게시판 | 게시물 작성/조회/수정/삭제, 댓글 기능 |
| **member/login.html** | 로그인 페이지 | 회원 인증 처리 |

#### 핵심 기술 스택
- **Python**: FastAPI, OpenCV, NumPy, ThreadPoolExecutor (고성능 영상 처리)
- **Java**: Spring Boot, MultipartFile, NIO Files (이미지 저장 및 관리)
- **Frontend**: Thymeleaf (공통 레이아웃), JavaScript/Ajax (동적 화면 갱신), Google Maps API (인터랙티브 지도)

---

### 🤖 **다른 팀원들** - AI/ML 모델 개발, 프론트엔드/백엔드 개발

#### 주요 역할
- **YOLO Pose 모델**: 포즈 추출 (17개 관절)
- **LSTM 모델**: 시계열 이상 행동 분류
- **FastAPI 서버**: 실시간 추론 처리
- **데이터셋 수집/전처리**: 학습 데이터 준비
- **모델 학습 및 평가**: 모델 성능 개선

---

## 📊 성과 및 특징

## 📊 성과 및 특징

### 🏆 프로젝트 성과
- **KT AIVLE School Big Project Practical 상 수상** 🏆
- 팀원: JoSooAh 외 7인

### 📈 개인 역량 향상 (portfolio.md 참고)
- ✅ FastAPI 기반 **비동기 API 설계 경험**
- ✅ Spring Boot를 활용한 **백엔드 웹 개발 역량 강화**
- ✅ Git 협업 (브랜치 전략, 머지, 충돌 관리) 실습
- ✅ 실시간 영상 처리 파이프라인 이해

### ⚠️ 한계 및 개선 사항
- **DB 통합 미흡**: 탐지 결과를 DB에 저장하지 못해 장기 데이터 관리에 한계
- **모델링 작업 기여도**: 상대적으로 낮았음
- **향후 개선 계획**:
  - [ ] 탐지 결과 DB 연동
  - [ ] 이상 행동 클래스 확장
  - [ ] 모델 성능 개선 및 재학습 자동화
  - [ ] 실서비스 환경 배포 구성

---

## 📚 참고 자료

### 공식 문서
- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)
- [FastAPI 공식 문서](https://fastapi.tiangolo.com/)
- [YOLO 공식 저장소](https://github.com/ultralytics/yolov8)
- [Thymeleaf 공식 문서](https://www.thymeleaf.org/)

### 관련 기술
- [Spring Security](https://spring.io/projects/spring-security)
- [WebSocket](https://developer.mozilla.org/en-US/docs/Web/API/WebSocket)
- [Google Maps API](https://developers.google.com/maps/documentation)
- [LSTM 시계열 분석](https://colah.github.io/posts/2015-08-Understanding-LSTMs/)

---

## 📄 라이선스

본 프로젝트는 **KT AIVLE School** 교육 프로그램의 일환으로 수행되었습니다.

---


**Last Updated**: 2025년 2월 13일
