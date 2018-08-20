# Spring framework를 사용한 OKKY Copycat


## 요구사항
1. 사용자 인증
* 자체 인증
* 소셜 인증
2. 게시판 기능
* 글
	* CRUD
	* 인증/인가된 사용자에게만 CUD 허용
	* 태그 부여
	* 검색
	* 투표(좋아요)
* 태그
	* 태그별 글 목록
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
* JQuery
* Mustache
* CKEditor

### 구현 방법
* Spring Security를 사용하여 인증/인가를 구현한다.
* REST API로 댓글 기능을 구현한다.
* Hibernate Validator로 유효성 검사를 한다.
