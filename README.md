# 소켓 프로그래밍을 사용한 영화 예매 시스템(다우기술 인턴 프로젝트)
팀원: 2명
기간: 2019/01/2 ~ 2019/1/28

## 사용 기술
- 클라이언트(C언어)
- 서버(JAVA)

## 요구사항
- 사용자
    1. 인증(로그인, 회원탈퇴)
    2. 영화 예매(상영중인 영화 목록, 상영일정 선택, 좌석 선택, 결제, 티켓 출력)
    3. 주문(티켓) 조회
    4. 주문(티켓) 취소
- 관리자
    1. 영화 등록
    2. 상영 일정 등록
    3. 공지사항


## 구현 방법
### Traffic 관리
- Thread Pool 
- Database Connection Pool
- 동시접속자 수
- 추가적인 병렬 처리 방법(ForkJoinPool)

### 동기화
- Readers-writer(shared-exclusive) lock

### 파일시스템
- 티켓 생성 -> 클라이언트에게 전송 -> 티켓 삭제

### 기본적인 Secure Coding
- Header Validation(body 길이, version)
- Body Validation(숫자, 문자 길이, 날짜, 시간, 데이터 개수, 좌석번호)
- 가격 계산 시 BigInteger 사용
- SQL Injection
