# Tour Stream 백엔드 시스템

## 기능 목록

- 상품 관리: 상품 등록, 수정, 삭제, 조회 기능을 제공합니다. (`ProductController`)
- 옵션 관리: 상품에 대한 옵션 등록, 수정, 조회 기능을 제공합니다. (`OptionController`)
- 이미지 관리: 상품 이미지 업로드 및 삭제 기능을 제공합니다. (`ProductController`)
- 파트너 관리: 파트너 등록, 수정, 조회 기능을 제공합니다. (`PartnerFacade`)
- 문의 관리: 사용자 문의 등록 기능을 제공합니다. (`ContactController`)
- 환율 정보 관리: 환율 정보 조회 및 업데이트 기능을 제공합니다. (`ExchangeRateServiceImpl`)
- 인증 및 권한 관리: JWT 기반의 인증 및 권한 관리 기능을 제공합니다. (`spring-boot-starter-security`, `jjwt-api`)
- 파일 저장소 연동: 이미지 파일을 외부 저장소에 저장 및 관리합니다. (`StorageService`)
- 이메일 서비스: 비밀번호 찾기 등의 이메일 발송 기능을 제공합니다. (`EmailService`)
- CI/CD: AWS CodeDeploy를 통한 지속적 통합 및 배포 자동화를 지원합니다. (`appspec.yml`, `deploy.sh`)

## 기술 스택

- **Java**: 주요 개발 언어로 Java 17을 사용합니다.
- **Spring Boot**: 애플리케이션의 기본 프레임워크로, 빠른 개발과 쉬운 배포를 지원합니다.
- **Gradle**: 빌드 도구로, 의존성 관리와 프로젝트 빌드를 담당합니다.
- **JUnit**: 단위 테스트를 위한 프레임워크입니다.
- **Docker**: 애플리케이션의 컨테이너화를 지원하여, 어떤 환경에서도 일관된 실행을 보장합니다.
- **AWS (EC2, S3, CodeDeploy)**: 클라우드 인프라 제공 및 CI/CD 파이프라인 구축에 사용됩니다.
- **GitHub Actions**: 소스 코드 관리 및 CI/CD 자동화를 위한 플랫폼입니다.
- **Redis**: 세션 관리 및 캐싱을 위해 사용되는 인메모리 데이터 스토어입니다.
- **MySQL**: 관계형 데이터베이스 관리 시스템(RDBMS)으로, 데이터 저장 및 관리에 사용됩니다.
- **Thymeleaf**: 서버 사이드 Java 템플릿 엔진으로, 이메일 템플릿 생성에 사용됩니다.
- **Swagger/OpenAPI**: REST API 문서화 및 테스트를 위한 도구입니다.
- **Lombok**: 반복적인 코드 작성을 줄이기 위한 Java 라이브러리입니다.
- **jjwt**: JWT(Json Web Token) 생성 및 검증을 위한 Java 라이브러리입니다.
- **Spring Security**: 인증 및 권한 관리를 위한 Spring 기반의 보안 프레임워크입니다.

## 주요 클래스 및 메서드

### 상품 관리

- `ProductController`: 상품 등록(`saveProduct`), 수정(`updateProduct`), 삭제(`deleteProduct`), 조회(`getProducts`) 기능을 제공합니다.
- `ProductService`: 상품 관련 비즈니스 로직을 처리합니다.
- `ProductRepository`: 상품 데이터에 접근하기 위한 JPA 리포지토리입니다.

### 옵션 관리

- `OptionController`: 옵션 등록(`saveOption`), 수정(`updateOption`), 조회(`getOptions`) 기능을 제공합니다.
- `OptionService`: 옵션 관련 비즈니스 로직을 처리합니다.
- `OptionRepository`: 옵션 데이터에 접근하기 위한 JPA 리포지토리입니다.

### 이미지 관리

- `ProductImageService`: 상품 이미지 업로드 및 삭제 기능을 제공합니다.
  - 상품 이미지 업로드: `upload(List<MultipartFile> files)`
  - 상품 이미지 삭제: `deleteProductImages(Product product)`
- `ProductImageRepository`: 상품 이미지 데이터에 접근하기 위한 JPA 리포지토리입니다.

### 파트너 관리

- `PartnerFacade`: 파트너 등록(`createPartner`), 수정(`updatePartner`), 조회(`findPartner`) 기능을 제공합니다.
- `PartnerService`: 파트너 관련 비즈니스 로직을 처리합니다.
- `PartnerRepository`: 파트너 데이터에 접근하기 위한 JPA 리포지토리입니다.

### 문의 관리

- `ContactController`: 사용자 문의 등록(`saveContact`) 기능을 제공합니다.
- `ContactService`: 문의 관련 비즈니스 로직을 처리합니다.
- `ContactRepository`: 문의 데이터에 접근하기 위한 JPA 리포지토리입니다.

### 환율 정보 관리

- `ExchangeRateServiceImpl`: 환율 정보 조회 및 업데이트(`updateExchangeRates`) 기능을 제공합니다.
- `ExchangeRateRepository`: 환율 데이터에 접근하기 위한 JPA 리포지토리입니다.

### 인증 및 권한 관리

- `AuthController`: 로그인(`login`), 로그아웃(`logout`) 기능을 제공합니다.
- `AuthService`: 인증 관련 비즈니스 로직을 처리합니다.
- `TokenService`: 토큰 생성 및 검증 로직을 처리합니다.

### 이메일 서비스

- `EmailService`: 이메일 발송 기능을 제공합니다.

### 파일 저장소 연동

- `StorageService`: 외부 저장소에 이미지 파일 저장 및 관리 기능을 제공합니다.

### CI/CD

- `appspec.yml`, `deploy.sh`: AWS CodeDeploy를 통한 배포 자동화 설정 파일입니다.

## 환경 설정 및 실행 방법

### 로컬 환경 설정

1. **Java 설치**: Java 17 버전을 설치합니다.
2. **Gradle 설치**: 프로젝트 빌드 및 의존성 관리를 위해 Gradle을 설치합니다.
3. **환경 변수 설정**: `application.yml` 파일에 필요한 환경 변수를 설정합니다. 예를 들어, 데이터베이스 연결 정보, 외부 API 키 등을 설정합니다.

### 데이터베이스 설정

MySQL 데이터베이스를 사용합니다. 데이터베이스와 사용자를 생성하고, `application.yml`에 해당 정보를 설정합니다.

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/tour_stream?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC
    username: tour_user
    password: your_password
```

### 애플리케이션 실행

1. **프로젝트 클론**: GitHub에서 프로젝트를 클론합니다.
2. **의존성 설치 및 빌드**: 프로젝트 루트 디렉토리에서 다음 명령어를 실행하여 의존성을 설치하고 프로젝트를 빌드합니다.

```shell
./gradlew build
```

3. **애플리케이션 실행**: 빌드가 완료되면, 다음 명령어로 애플리케이션을 실행합니다.

```shell
java -jar build/libs/tour-stream-backend-0.0.1-SNAPSHOT.jar
```

### Docker를 사용한 실행

Docker와 Docker Compose가 설치되어 있어야 합니다.

1. **Docker 이미지 빌드**:

```shell
docker build -t tour-stream-backend:latest .
```

2. **Docker 컨테이너 실행**:

```shell
docker run -d -p 8080:8080 tour-stream-backend:latest
```

이제 애플리케이션이 로컬 환경에서 실행되며, `http://localhost:8080`을 통해 접근할 수 있습니다.

## CI/CD

Tour Stream 백엔드 시스템의 지속적 통합 및 배포(CI/CD) 파이프라인은 GitHub Actions와 AWS CodeDeploy를 사용하여 구성됩니다. 이를 통해 코드 변경 사항이 메인 브랜치에 푸시될 때마다 자동으로 빌드, 테스트 및 배포가 수행됩니다.

### GitHub Actions 설정

`.github/workflows/gradle.yml` 파일에 CI/CD 파이프라인을 위한 GitHub Actions 워크플로우가 정의되어 있습니다. 이 워크플로우는 다음 단계를 포함합니다:

1. **JDK 설정**: Java 17 버전을 설정합니다.
2. **Gradle 캐싱**: 빌드 시간을 단축하기 위해 Gradle 의존성을 캐싱합니다.
3. **빌드 실행**: Gradle을 사용하여 프로젝트를 빌드합니다. 테스트를 제외하고 빌드를 수행합니다.
4. **Docker 이미지 빌드 및 저장**: Docker를 사용하여 애플리케이션의 Docker 이미지를 빌드하고 tar 파일로 저장합니다.
5. **AWS 자격증명 구성**: AWS에 접근하기 위한 자격증명을 구성합니다.
6. **S3에 배포 파일 업로드**: 빌드된 파일을 AWS S3 버킷에 업로드합니다.

### AWS CodeDeploy 설정

`appspec.yml` 파일은 AWS CodeDeploy를 통한 배포를 위한 설정을 포함합니다. 이 파일은 배포될 파일의 위치, 권한 설정, 배포 후 실행할 스크립트(`deploy.sh`) 등을 정의합니다.

### 배포 스크립트

`scripts/deploy.sh` 스크립트는 AWS EC2 인스턴스에서 실행됩니다. 이 스크립트는 다음 작업을 수행합니다:

1. 기존의 Docker 컨테이너와 이미지를 찾아서 삭제합니다.
2. 새로운 Docker 이미지를 로드합니다.
3. 새로운 Docker 컨테이너를 실행합니다.

이 과정을 통해 애플리케이션의 새 버전이 자동으로 배포됩니다.

## 참고 문서

Tour Stream 백엔드 시스템의 개발 및 운영에 관련된 추가적인 정보와 자세한 가이드는 아래 문서를 참고하세요.

- **Spring Boot 공식 문서**: [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- **Gradle 사용법**: [Gradle User Manual](https://docs.gradle.org/current/userguide/userguide.html)
- **JUnit 5 가이드**: [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- **Docker 문서**: [Docker Documentation](https://docs.docker.com/)
- **AWS 공식 문서**: [AWS Documentation](https://docs.aws.amazon.com/)
- **GitHub Actions 문서**: [GitHub Actions Documentation](https://docs.github.com/en/actions)
- **MySQL 공식 문서**: [MySQL Documentation](https://dev.mysql.com/doc/)
- **Redis 공식 문서**: [Redis Documentation](https://redis.io/documentation)
- **Thymeleaf 사용법**: [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- **Swagger/OpenAPI**: [Swagger Documentation](https://swagger.io/docs/)
- **Lombok 프로젝트**: [Project Lombok](https://projectlombok.org/)
- **jjwt GitHub 리포지토리**: [jjwt GitHub](https://github.com/jwtk/jjwt)
- **Spring Security 참조 가이드**: [Spring Security Reference](https://docs.spring.io/spring-security/site/docs/current/reference/html5/)
