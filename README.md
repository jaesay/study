# Spring framework를 사용한 회원제 게시판


## 요구사항
1. 사용자 인증
* 자체 인증
* 자동 로그인
2. 게시판 기능
* 글
	* CRUD
	* 인증/인가된 사용자에게만 CUD 허용
	* 검색
	* 투표(좋아요)
	* 페이지네이션
* 댓글
	* 인증/인가된 사용자에게만 CUD 허용
	* 투표(좋아요)
3. 다국어 지원(한글/영어)

## 구현
### 서버
* Spring framework
* MyBatis
* MySQL
* Spring Security
### 클라이언트
* JSP
* jQuery
* Mustache
* CKEditor

### 구현 방법
* Spring Security를 사용하여 인증/인가를 구현한다.
* REST API로 댓글 기능을 구현한다.
* Hibernate Validator로 유효성 검사를 한다.
* LocaleChangeInterceptor를 listener로 직접 구현하여 국제화를 구현한다.
