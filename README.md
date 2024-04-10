# CFS(Car Forensic Scouter)
![image](https://github.com/Car-Forensics-Scouter/cfs/assets/121649620/35aad32b-34ce-459e-918c-f930386e5b43)

<br />

# Introdution
 CFS(Car Forensic Scouter)는 차량 내부 데이터를 수집하는 시스템을 구축,
 작게는 법적 증거 자료를 획득하는 전문적 활동에,
 넓게는 일반인의 접근성 높은 차량 관리의 지원을 목표로 하는 프로그램이다.

 본 프로젝트에선 일반 사용자가 이해할 수 있는 속도, 연료 정보 등의 단순한 정보부터
 가속 페달 정보 등 법적 증거로 쓰일 수 있을 만한 데이터를 10 종류 내외로 선정, 수집할 계획이다.

<br />

# Motivation
라즈베리파이, OBD-2, 파이썬 등을 이용한 프레임워크를 제시한 논문과 인포카를 비롯한 서비스들은 기존부터 있어 왔으나, 본 프로젝트에선 국산차를 비롯해 실질적으로 필요한 데이터를 사용하여 접근성 높은 서비스 제공을 목표로 한다.

<br />

# System Architecture
![image](https://github.com/Car-Forensics-Scouter/limchansu/assets/121649620/8fc53de1-6eb5-448d-a872-c2ce48df8546)

Raspberry Pi는 차량의 OBD-II 포트를 통해 차량 내부에 저장된 데이터를 수집하고, 페달 작동과 관련된 영상을 수집한다. 획득한 OBD 데이터는 MQTT 프로토콜을, 영상 데이터는 HTTP
프로토콜을 사용하여 AWS IoT 시스템으로 전송된다. 전송된 OBD 데이터와 영상의 메타데이터는
MySQL 데이터베이스가 호스팅된 Amazon RDS에, 페달 작동 영상은 Amazon S3에 최종적으로
보관된다. 이를 통해 데이터에 대한 신속한 접근 뿐만 아니라 데이터 안정성을 보장할 수 있다.

클라우드에 보관된 데이터들을 웹 서비스로 제공하기 위해 Spring Boot를 사용한다. Spring
Boot를 사용하면 복잡한 서버 설정 과정을 간소화하고, 시스템 로직 설계에 필요한 컴포넌트들을통합하여 개발 초기에 필요한 환경 설정을 쉽게 진행할 수 있다. 그리고 데이터베이스 설계 및 구현 과정에서 간결하고 효율적인 처리를 위해 Spring Data JPA를 사용하여 비즈니스 로직 구현에 더 집중할 수 있도록 한다. Spring Security는 서비스에 대한 안전한 접근을 보장하는 인증 및 권한 부여에 대한 메커니즘을 제공한다.

지속적인 통합과 배포를 위해서 GitHub Actions과 Amazon CodeDeploy를 활용해 개발에 사용된 로직들을 자동 배포할 계획이다. 이로써 사용자는 웹 브라우저를 통해 시스템에 접근할 수 있으며, React와 Material-UI를 활용하여 구축한 사용자 친화적인 인터페이스로 차량 상태를 시각적으로 제공받을 수 있다.
