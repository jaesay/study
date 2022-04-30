스프링 부트 2.3.0 부터는 도커파일 없이도 레이어를 사용한 효율적인 형태로 애플리케이션의 도커이미지를 만들 수 있다.

- 도커 이미지 빌드: `gradle bootBuildImage`
- 도커 컨테이너 실행: `docket run --rm -p 8080:8080 ${project.name}:${project.version}`

[gralde plugin docker docs](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/#build-image)  
[maven plugin docker docs](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/maven-plugin/reference/html/#build-image)