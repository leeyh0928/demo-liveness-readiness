# Overview
Spring Boot 2.3 부터 k8s를 지원하기 위해 추가 된 `Liveness`와 `Readiness` 에 대해 테스트 해보자.

# What is liveness?
* 애플리케이션이 살아있는가?
* 상태가 비정상이고, 복구하지 못한다면 보통 애플리케이션을 재기동
    * LivenessState.CORRECT
    * LivenessState.BROKEN

# What is readiness?
* 요청을 받을 준비가 되었는가?
* 준비가 될 때까지 해당 서버로 요청을 보내지 않고, 다른 서버로 보낸다.
    * ReadinessState.ACCEPTING_TRAFFIC
    * ReadinessState.REFUSING_TRAFFIC
    
# Tip
외부에서 애플리케이션에 상태 정보 조회를 위해 아래 옵션을 활성화
* Liveness : /actuator/health/livenss
* readiness : /actuator/health/readiness
~~~properties
anagement.endpoint.health.probes.enabled=true
~~~
