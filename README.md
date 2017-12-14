# bookshop-mvc-php
## my-theme repository에서 만든 theme, PHP, MVC pattern을 사용하여 온라인 서점 홈페이지 prototype을 만들어 본다.

###요구사항
#### 관리자
1. 로그인 페이지에서 관리자 아이디/비밀번호로 입력할 경우 관리자로 로그인된다. (완료) <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-네비게이션 메뉴가 유저와 다르게 보인다.

2. 유저를 추가, 변경, 삭제를 할 수 있다. (미완료)

3. 상품을 추가, 변경, 삭제를 할 수 있다. (진행 중)

4. 댓글을 삭제를 할 수 있다. (미완료)

5. 질문을 추가, 변경, 삭제를 할 수 있다. (미완료)


#### 유저
1. 회원가입을 할 수 있다. (완료)

2. 로그인할 수 있다. (완료)

3. 로그아웃할 수 있다. (완료)

4. 자신의 정보를 변경, 삭제할 수 있다. (완료)

5. 상품을 볼 수 있다. (미완료)<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-NEW, BEST, ALL, PROGRAMMING, DATABASE, NETWORK, OS, ETC

6. 상품을 장바구니에 담을 수 있다. (미완료)

7. 상품을 구매할 수 있다. (미완료)

8. 질문을 할 수 있다. (미완료)

### 로직
모든 url은 /public/index.php로 들어온다. public directory의 .htaccess 파일에 작성된 규칙에 따라 url을 rewrite된다. 다시 작성된 url은 index.php에서 초기화된 /app/core/App.php에 정의된 class App의 instance 안에서 parse되고 적절한 controller의 method를 실행시킨다.


### 예상 URI (/Controller/Method)
#### 관리자
* / or /home/index - 홈 화면을 보여준다.

* /admin_member/list_users_form - 유저 목록을 보여준다.

* /admin_member/change_user_form - 유저 개인정보 변경화면을 보여준다.

* /admin_book/list_books_form - 책 목록을 보여준다.

* /admin_book/add_book_form - 책 추가 화면을 보여준다.

* /admin_book/change_book_form - 책 정보 변경 화면을 보여준다. 

* /admin_review/list_reviews_form - 리뷰화면을 보여준다. 세부사항은 모달을 통해 보여준다.

* /admin_question/list_questions_form -질문화면을 보여준다.

* /admin_question/show_question_form - 질문 디테일 화면을 보여준다.

#### 유저
* / or /home/index - 홈 화면을 보여준다.

* /member/signup - 회원가입 화면을 보여준다.

* /member/login - 로그인 화면을 보여준다. 로그인이 완료되면 기존의 보고 있던 화면을 보여준다.

* /member/auth_form - 본인 인증 화면을 보여준다. 비밀번호를 통해 인증한다.

* /member/auth - 개인정보화면을 보여준다.

* /member/fotgot_pwd - 비밀번호를 잊어버렸을 때 화면을 보여준다. 아이디와 이메일을 확인한다.

* /book/display_new - 네비게이션의 NEW를 클릭했을 때  화면을 보여준다.

* /book/display_best - 네비게이션의 BEST를 클릭했을 때 화면을 보여준다.

* /book/display_cate - 네비게이션의 category의 ALL, PROGRAMMING, DATABASE, NETWORK, OS, ETC 화면을 보여준다.

* /book/show_book - 상품의 디테일 화면을 보여준다.

* /order/order_books - 주문을 위해 배송지 등의 정보를 입력하는 화면을 보여준다. 카드 정보 입력화면을 모달을 통해 처리한다.

* /home/show_cart - 장바구니를 보여준다.

* /question/list_questions - 질문 화면을 보여준다.

* /question/edit_question - 질문 변경화면을 보여준다.

* /handler/error - 에러페이지를 보여준다.


