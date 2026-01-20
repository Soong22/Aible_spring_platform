# 포즈 에스티메이션 모델 기반 범죄 예측 모니터링

YOLO Pose + LSTM 모델을 적용하여 **CCTV(웹캠) 영상에서 이상 행동을 실시간으로 감지**하고,  
탐지 결과를 **웹 기반 관리자 페이지**에서 시각화·관리하는 AI 기반 모니터링 시스템입니다.

---

## 📌 프로젝트 개요
- **프로젝트 기간**: 2024.12.30 ~ 2025.02.13
- **참여 인원**: 8명
- **프로젝트 유형**: 교육
- **소속**: KT AIVLE School (24조)
- **목표**
  - 포즈 에스티메이션 기반 이상 행동 탐지
  - 실시간 CCTV 스트리밍 및 탐지 결과 시각화
  - 범죄 이력 관리 및 위치 기반 정보 제공

---

## 🧠 핵심 기능
### 1. 이상 행동 탐지 (AI)
- YOLO Pose 기반 **포즈 추출**
- LSTM 모델을 통한 **이상 행동 분류**
- FastAPI 서버에서 **실시간 추론 처리**

### 2. 실시간 영상 스트리밍
- MJPEG 스트리밍 방식으로 CCTV(웹캠) 영상 제공
- `/predict` 엔드포인트를 통한 실시간 영상 분석

### 3. 관리자 웹 페이지
- 이상 행동 탐지 페이지
- 범죄 이력 관리 페이지
- 이미지 다운로드, 전체 화면 토글 등 인터랙션 제공

### 4. 위치 기반 시각화
- 광주광역시 **CCTV 위치 지도**
- 광주광역시 **경찰서 위치 지도**
- Google Maps API + SVG 기반 인터랙티브 지도
- 마커 클릭 시 MJPEG 스트리밍 영상 표시

---

## 🏗 시스템 아키텍처
[CCTV / Webcam]
↓
FastAPI 서버
(YOLO Pose + LSTM)
↓
탐지 결과 이미지
↓
Spring Boot API
↓
관리자 웹 페이지
(Thymeleaf + JS)

---

## 🛠 사용 기술 스택
### Backend
- **Python**
  - OpenCV, NumPy
  - ThreadPoolExecutor, Queue
  - requests
- **FastAPI**
  - 실시간 영상 스트리밍
  - AI 모델 추론 API
- **Spring Boot**
  - 관리자 페이지
  - 알림 전송 및 데이터 관리 API
- **Spring MVC**

### Frontend
- **Thymeleaf**
- **JavaScript**
- **jQuery / Ajax**
- **SVG**
- **Google Maps API**

### DevOps / Infra
- **Docker**
- **Azure**
- **Git**
- **IntelliJ / VSCode**

---

## 📂 주요 구현 내용
### 백엔드
- FastAPI + Uvicorn 기반 실시간 영상 수신
- OpenCV, NumPy로 이미지 전처리
- ThreadPoolExecutor + Queue 기반 멀티스레딩 처리
- 탐지 결과 이미지를 Spring Boot 서버로 전송
- CopyOnWriteArrayList로 데이터 안전 관리
- MultipartFile + Java NIO Files로 이미지 저장

### 프론트엔드
- Thymeleaf 공통 레이아웃 구성
- Ajax 기반 주기적 데이터 갱신
- MJPEG 스트리밍 영상 표시
- 지도 클릭 시 드롭다운 연동 및 스트리밍 표시

---

## 📈 프로젝트를 통해 성장한 점
- FastAPI 기반 **비동기 API 설계 경험**
- Spring Boot를 활용한 **백엔드 웹 개발 역량 강화**
- Git 협업 (브랜치 전략, 머지, 충돌 관리) 실습
- 실시간 영상 처리 파이프라인 이해

---

## ⚠️ 한계 및 개선점
- 탐지 결과를 DB에 저장하지 못해 장기 데이터 관리에 한계
- 모델링 작업 기여도가 상대적으로 낮았음
- 향후 데이터 확보 및 모델 성능 개선 필요

---

## 🏆 성과
- **KT AIVLE School Big Project Practical 상 수상**
- 팀원: 조수아 외 7인

---

## 📌 TODO
- [ ] 탐지 결과 DB 연동
- [ ] 이상 행동 클래스 확장
- [ ] 모델 성능 개선 및 재학습 자동화
- [ ] 실서비스 환경 배포 구성

---

## 📄 License
본 프로젝트는 교육 목적으로 수행되었습니다.
