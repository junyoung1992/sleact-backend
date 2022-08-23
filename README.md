# Sleact - Backend

Inflearn) Slack 클론 코딩[실시간 채팅 with React] 강의의 Backend 코드를 Spring boot로 재구현하였다.<br /> 
https://github.com/ZeroCho/sleact

## Fix

1. 엔티티 수정
    - 외래키를 복합키로 사용하지 않고 대리키를 추가하였다. 
    - 엔티티는 camelCase를 테이블은 snake_case를 사용하였다.
2. API 명세는 swagger와 restdocs를 통해 제공한다.

## Git Flow

실습 프로젝트이므로 git flow는 단순하게 가져간다.

- master
    - master에는 aws ec2에 배포 가능한 상태의 코드만 merge 한다.
    - master로 push/merge 가 발생하면 actions를 통해 배포가 진행된다. 
- develop
    - develop에는 feature에서 개발된 코드를 merge 한다.
    - master로 merge하기 전에 충분히 테스트한다.
- feature/...
    - 기능 개발은 feature 브랜치를 만들어 개발한다.
    - 개발이 완료되면 테스트 후 develop으로 merge 한다.

## Architecture

### 패키지 구조 예시

```
.
|-- common
|   |-- constants
|   `-- enums
|-- configuration
|-- core
|   |-- channel
|   |   |-- controller
|   |   |-- dto
|   |   |   |-- response
|   |   |   `-- request
|   |   |-- mapper
|   |   |-- repositoryservice
|   |   `-- service
|   |-- user
|   `-- workspace
`-- persistence
    |-- entity
    `-- repository
```
