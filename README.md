# bookshop-mvc-php
## my-theme repository에서 만든 theme, PHP, MVC pattern을 사용하여 온라인 서점 홈페이지 prototype을 만들어 본다.

### 개발환경
PHP 7.0.22 <br />
Apache 2.4.18 <br />
Ubuntu 16.04 <br />
Sublime Text3

### 요구사항
#### 관리자
1. 로그인 페이지에서 관리자 아이디/비밀번호로 입력할 경우 관리자로 로그인된다. (완료) <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-네비게이션 메뉴가 유저와 다르게 보인다.

2. 상품을 추가, 변경, 삭제를 할 수 있다. (완료)

#### 유저
1. 회원가입을 할 수 있다. (완료)

2. 로그인할 수 있다. (완료)

3. 로그아웃할 수 있다. (완료)

4. 자신의 정보를 변경, 삭제할 수 있다. (완료)

5. 상품을 볼 수 있다. (완료)<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-NEW, BEST, ALL, PROGRAMMING, DATABASE, NETWORK, OS, ETC

6. 상품을 구매할 수 있다. (완료)

7. 상품을 장바구니에 담을 수 있다. (완료)

8. 상품을 장바구니에서 구매할 수 있다. (완료)


<br /><br /><br />
### 로직
모든 url은 /public/index.php로 들어온다. public directory의 .htaccess 파일에 작성된 규칙에 따라 url을 rewrite된다. 다시 작성된 url은 index.php에서 초기화된 /app/core/App.php에 정의된 class App의 instance 안에서 parse되고 적절한 controller의 method를 실행시킨다.


<br /><br /><br />
### URI (/Controller/Method)
#### 관리자
* / or /home/index - 홈 화면을 보여준다.

* /book/list_books - 책 목록을 보여준다.

* /book/show_book - 책 상세 페이지를 보여준다.

* /admin_book/add_book_form - 책 추가 화면을 보여준다.

* /admin_book/add_book - 책 정보 변경 화면을 보여준다. 

* /admin_book/edit_book - 리뷰화면을 보여준다. 세부사항은 모달을 통해 보여준다.

* /admin_book/remove_book -질문화면을 보여준다.

* /admin_question/show_question_form - 질문 디테일 화면을 보여준다.

#### 유저
* / or /home/index - 홈 화면을 보여준다.

* /member/signup_form - 회원가입 화면을 보여준다.

* /member/signup - 회원가입을 처리한다.

* /member/login_form - 로그인 화면을 보여준다. 로그인이 완료되면 기존의 보고 있던 화면을 보여준다.

* /member/login - 로그인을 처리한다.

* /member/logout - 로그아웃을 처리한다.

* /member/auth_form - 본인 인증 화면을 보여준다. 비밀번호를 통해 인증한다.

* /member/auth - 변경 화면이나 삭제 처리를 한다.

* /member/change_member - 회원 정보를 변경한다.

* /book/list_books - 책 목록을 보여준다.

* /book/show_book - 책 상세 페이지를 보여준다.

* /cart/add_to_cart - 장바구니에 추가한다.

* /cart/show_cart - 장바구니를 보여준다.

* /cart/change_qty - 장바구니 수량 변경을 처리한다.

* /order/order_book_form - 바로 구매하기 주문 화면을 보여준다.

* /order/order_book - 바로 구매하기 주문을 처리한다.

* /order/order_cart_form - 장바구니 구매하기 주문 화면을 보여준다.

* /order/order_cart - 장바구니 구매하기 주문을 처리한다.

* /order/order_compl - 구매 완료 화면을 보여준다.

* /handler/error - 에러페이지를 보여준다.
