# 🚗 CFS (Car Forensic Scouter)
 차량 내부 데이터를 수집하고, 획득한 데이터를 확인할 수 있는 웹사이트<br />
 작게는 법적 증거 자료를 획득하는 전문적 활동, 넓게는 일반인의 접근성 높은 차량 관리 지원을 목표로 개발 진행

💡 **[header]** 로그인, 회원가입, 아이디/비밀번호 찾기

💡 **[매인 대시보드]** 운전 과정에서 획득한 데이터를 수치로 보여주고, 이동 경로를 지도상에 표현

💡 **[동영상 보관함]** 획득한 동영상 데이터 다운로드

💡 **[그래프 대시보드]** 운전 과정에서 획득한 데이터를 그래프로 표현

💡 **[서비스 설정]** 비밀번호, 차종, 라즈베리 파이 일련번호 수정

💡 **python-OBD** 라이브러리를 사용하여 데이터를 획득하고, Amazon RDS와 S3에 데이터 저장

<br />

# 👨‍👩‍👧‍👦 팀 구성
## Embedded

### **조성빈**

- 차량 데이터 수집 및 전송 기능
    - 차량 ODB-2 데이터 수집 기능 구현
    - GPS 정보 처리 기능 구현
    - 카메라 녹화 기능 구현
    - 획득 데이터 전송 기능 구현

## Frontend

### **임찬수**

- UI/UX 설계
    - 로그인
    - 회원가입
    - 아이디/비밀번호 찾기
    - 그래프 대시보드
- 로그인, 회원가입, 아이디/비밀번호 찾기 화면 구현
- 그래프 대시보드 화면 구현
    - 지정 시간대 ODB-2 데이터 그래프 변환 기능

### **조성빈**

- UI/UX 설계
    - 전체 배너 인터페이스
    - 메인 대시보드
    - 영상 보관함
    - 서비스 설정
- 매인 대시보드 화면 구현
    - 지정 시간대 ODB-2 데이터 조회 기능
    - 지정 시간대 GPS 기반 이동 경로 조회 기능
- 영상 보관함 화면 구현
- 서비스 설정 화면 구현
    - 비밀번호, 차종, 라즈베리 파이 일련번호 수정 기능

## Backend

### **윤예진**

- 데이터베이스 설계
- 도메인 설계
- ODB 로그 데이터 처리 기능
- 동영상 데이터 처리 기능
- 동영상 다운로드, xslx 파일 변환 기능
- Amazon S3 운용

### **정욱재**

- 데이터베이스 설계
- 도메인 설계
- 회원 정보 처리 기능
- 스프링 시큐리티 관련 로그인 기능
- Amazon RDS 운용
- 의존성 관리

<br />

# 🔧 개발환경
## Embedded
Raspberry Pi, Python

## Front
JavaScript, CSS, React, Figma, Naver Map

## Back
Spring Boot, Java, MySQL, JUnit, Postman

## Environment
Git, Github, VSCode, Intellij IDE

## Communication & Tool
Git, Github, Notion, Discord, Kakao Talk

## Cloud Service
Amazon RDS, Amazon S3, Amazon EC2, Amazon Code Deploy

## Distribution
GitHub Actions [진행 예정]

<br />

# ⚙️ System Architecture
![스크린샷 2024-06-12 221023](https://github.com/Car-Forensics-Scouter/CapstoneDesign-2024-1/assets/110958351/570c8dc2-c4f0-4511-ad9b-34830ec93c87)

<br />

# 🖥️ 화면 구성 및 기능
## 로그인
![스크린샷 2024-06-13 000325](https://github.com/Car-Forensics-Scouter/CapstoneDesign-2024-1/assets/110958351/ad13cc26-f81d-442c-8588-e3d921751bb7)

<br />

## 회원 가입
![스크린샷 2024-06-13 000412](https://github.com/Car-Forensics-Scouter/CapstoneDesign-2024-1/assets/110958351/550d5b49-f560-469f-8525-f9c098fb8b84)

<br />

## 아이디, 비밀번호 찾기
![스크린샷 2024-06-13 000345](https://github.com/Car-Forensics-Scouter/CapstoneDesign-2024-1/assets/110958351/68de3bc1-bfd5-4cd8-888e-48b7b8968316)

<br />

## 메인 대시보드
![스크린샷 2024-06-13 000551](https://github.com/Car-Forensics-Scouter/CapstoneDesign-2024-1/assets/110958351/1154d9b8-a583-4531-99a2-c7c0037018b2)

<br />

## 동영상 보관함
![KakaoTalk_20240612_232143546_02](https://github.com/Car-Forensics-Scouter/CapstoneDesign-2024-1/assets/110958351/4b65f3a9-2ab7-4e73-8f89-67caa44b6730)

<br />

## 그래프 대시보드
![스크린샷 2024-06-13 000532](https://github.com/Car-Forensics-Scouter/CapstoneDesign-2024-1/assets/110958351/b021e881-0bd5-40de-93a4-edb7bca680bb)

<br />

## 서비스 설정
![스크린샷 2024-06-13 000513](https://github.com/Car-Forensics-Scouter/CapstoneDesign-2024-1/assets/110958351/c2f4938c-0ef5-4317-bb2b-90980ffaec71)

