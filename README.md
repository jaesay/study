# Toy Project: 스프링 부트를 활용한 쇼핑몰
## 주제
사용자가 상품을 장바구니에 담고 주문할 수 있는 쇼핑몰을 구현한다.

## 사용 기술
- Spring Boot
- Spring Batch
- Spring Data JPA[Hibernate]
- Spring Security
- Thymeleaf
- MySQL
- JQuery
- Bootstrap

## 제작 내용
- Spring Boot를 활용하여 쇼핑몰의 기본 기능인 인증/인가, 제품 목록, 제품 상세, 장바구니, 체크아웃, 주문, 결제를 구현한다.
- Spring Security를 통해 인증/인가를 구현한다.
- Spring Batch를 통해 장바구니 비우기 기능을 개발한다.
- 주소 API는 다음 주소 API를 사용한다.
- 결제연동 서비스를 위해 아임포트 API를 사용한다.
	- 서버 단에서 아임포트 RestClient를 사용해 클라이언트 변조 검증한다
- 회원 가입시 유효성 검사는 client, server 모두에서 진행한다.
	- Client 유효성 검사를 위해 필요에 따라 jQuery Validator를 사용한다.
	- Server side 유효성 검사를 위해서 Hibernate Validator를 사용한다.
