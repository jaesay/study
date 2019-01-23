use bajo;

-- 사용자
insert into members (member_name, password, name, role) values ('admin', '1234', '김관리자', 'ADMIN');
insert into members (member_name, password, name, role) values ('manager1', '1234', '김매니저', 'MANAGER');
insert into members (member_name, password, name, role) values ('manager2', '1234', '이매니저', 'MANAGER');
insert into members (member_name, password, name) values ('user1', '1234', '김사용자');
insert into members (member_name, password, name) values ('user2', '1234', '이사용자');
insert into members (member_name, password, name) values ('user3', '1234', '박사용자');
insert into members (member_name, password, name) values ('user4', '1234', '최사용자');



-- 상영관
insert into theaters (seat_total_number) values (100);
insert into theaters (seat_total_number) values (100);
insert into theaters (seat_total_number) values (100);



-- 영화
insert into movies (title, director, content, actor, rating, rating_count, member_id) values ('말모이', '엄유나', '까막눈 판수, 우리말에 눈뜨다! vs 조선어학회 대표 정환, ‘우리’의 소중함에 눈뜨다!

1940년대 우리말이 점점 사라져가고 있는 경성.
 극장에서 해고된 후 아들 학비 때문에 가방을 훔치다 실패한 판수.
 하필 면접 보러 간 조선어학회 대표가 가방 주인 정환이다.
 사전 만드는데 전과자에다 까막눈이라니!
 그러나 판수를 반기는 회원들에 밀려 정환은 읽고 쓰기를 떼는 조건으로 그를 받아들인다.
 돈도 아닌 말을 대체 왜 모으나 싶었던 판수는 난생처음 글을 읽으며 우리말의 소중함에 눈뜨고,
 정환 또한 전국의 말을 모으는 ‘말모이’에 힘을 보태는 판수를 통해 ‘우리’의 소중함에 눈뜬다.
 얼마 남지 않은 시간, 바짝 조여오는 일제의 감시를 피해 ‘말모이’를 끝내야 하는데…
 
 우리말이 금지된 시대, 말과 마음이 모여 사전이 되다', '유해진/윤계상', 9.29, 1518, 2);
insert into movies (title, director, content, actor, rating, rating_count, member_id) values ('아쿠아맨', '제임스 완', '땅의 아들이자 바다의 왕, 심해의 수호자인 슈퍼히어로 아쿠아맨의 탄생을 그린 액션 블록버스터', '제이슨 모모아/앰버 허드', 8.78, 2376, 3);

insert into movies (title, director, content, actor, rating, rating_count, member_id) values ('보헤미안랩소디', '브라이언 싱어', '“나는 스타가 되지 않을 것이다, 전설이 될 것이다”

공항에서 수하물 노동자로 일하며 음악의 꿈을 키우던 이민자 출신의 아웃사이더 ‘파록버사라’ 
 보컬을 구하던 로컬 밴드에 들어가게 되면서 ‘프레디 머큐리’라는 이름으로 밴드 ‘퀸’을 이끌게 된다. 
  
 시대를 앞서가는 독창적인 음악과 화려한 퍼포먼스로 관중들을 사로잡으며 성장하던 ‘퀸’은 
 라디오와 방송에서 외면을 받을 것이라는 음반사의 반대에도 불구하고 
 무려 6분 동안 이어지는 실험적인 곡 ‘보헤미안 랩소디’로 대성공을 거두며 월드스타 반열에 오른다. 
  
 그러나 독보적인 존재감을 뿜어내던 ‘프레디 머큐리’는 솔로 데뷔라는 유혹에 흔들리게 되고 
 결국 오랜 시간 함께 해왔던 멤버들과 결별을 선언하게 되는데… 
  
 세상에서 소외된 아웃사이더에서 전설의 록밴드 ‘퀸’이 되기까지,
 우리가 몰랐던 그들의 진짜 이야기가 시작된다!', '라미 말렉/루시 보인턴', 9.46, 3639, 2);
  
 insert into movies (title, director, content, actor, rating, rating_count, member_id) values ('범블비', '트래비스 나이트', '진짜 이야기는 지금부터다! 
아무도 몰랐던 이야기가 시작된다! 

지속된 디셉티콘과의 전쟁에서 위기에 몰린 옵티머스 프라임은 중요한 임무를 가진 오토봇을 지구로 보낸다. 
 지구에 도착한 오토봇은 인간들에게 쫓기게 되고, 낡은 비틀로 변신해 폐차장에 은둔하던 중, 
 찰리라는 소녀에 의해 발견된다. 
  
 비틀을 수리하던 찰리는 자신의 낡은 자동차가 거대한 로봇으로 변신하는 놀라운 광경을 목격하고, 
 모든 기억이 사라진 그에게 ‘범블비’라는 이름을 지어주며 서로에게 특별한 존재가 되어간다. 
  
 하지만, 범블비의 정체를 파헤치려는 인간들과 그가 가진 비밀을 쫓는 디셉티콘의 추격과 압박은 점점 더 심해지는데…', '헤일리 스테인펠드/존 시나', 8.75, 1409, 3);

insert into movies (title, director, content, actor, rating, rating_count, member_id) values ('내안에 그놈', '강효진', '나 너니? 너 나니??
제대로 바뀐 아재와 고딩, 웃음 대환장 파티!

엘리트 아재 판수(박성웅)를 우연히 옥상에서 떨어진 고등학생 동현(진영)이 덮치면서 제대로 바뀐다. 
 게다가 판수는 동현의 몸으로 첫사랑 미선(라미란)과 존재도 몰랐던 딸 현정(이수민)을 만나게 되는데… 
 대유잼의 향연, 넌 이미 웃고 있다!', '진영/박성웅', 8.97, 587, 3);

insert into movies (title, director, content, actor, rating, rating_count, member_id) values ('타이타닉', '제임스 카메론', '“내 인생의 가장 큰 행운은 도박에서 딴 티켓으로 당신을 만난 거야”
단 하나의 운명, 단 한 번의 사랑,
영원으로 기억될 세기의 러브 스토리

우연한 기회로 티켓을 구해 타이타닉호에 올라탄 자유로운 영혼을 가진 화가 잭(레오나르도 디카프리오)은 
 막강한 재력의 약혼자와 함께 1등실에 승선한 로즈(케이트 윈슬렛)에게 한 눈에 반한다. 
 진실한 사랑을 꿈꾸던 로즈 또한 생애 처음 황홀한 감정에 휩싸이고, 둘은 운명 같은 사랑에 빠지는데…', '레오나르도 디카프리오/케이트 윈슬렛', 9.86, 272, 2);
 
 
 
 

-- 좌석
DELIMITER $$
CREATE PROCEDURE prepare_data1()
BEGIN
  DECLARE i INT DEFAULT 1;

  WHILE i <= 100 DO
    INSERT INTO seats (theater_id, seat_id) VALUES (1, i);
    SET i = i + 1;
  END WHILE;
END$$

CREATE PROCEDURE prepare_data2()
BEGIN
  DECLARE i INT DEFAULT 1;

  WHILE i <= 100 DO
    INSERT INTO seats (theater_id, seat_id) VALUES (2, i);
    SET i = i + 1;
  END WHILE;
END$$

CREATE PROCEDURE prepare_data3()
BEGIN
  DECLARE i INT DEFAULT 1;

  WHILE i <= 100 DO
    INSERT INTO seats (theater_id, seat_id) VALUES (3, i);
    SET i = i + 1;
  END WHILE;
END$$
DELIMITER ;

CALL prepare_data1();
CALL prepare_data2();
CALL prepare_data3();



-- 상영 일정
insert into schedules (schedule_date, movie_id, theater_id) values ('1997-05-20 10:30:00', 6, 1);

insert into schedules (schedule_date, movie_id, theater_id) values ('2019-01-14 10:30:00', 1, 1);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-01-14 13:45:00', 2, 1);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-01-14 16:30:00', 3, 1);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-01-14 19:25:00', 4, 1);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-01-14 22:30:00', 5, 1);

insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 10:30:00', 1, 1);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 13:45:00', 2, 1);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 16:30:00', 3, 1);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 19:25:00', 4, 1);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 22:30:00', 5, 1);

insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 10:30:00', 5, 2);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 13:45:00', 4, 2);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 16:30:00', 3, 2);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 19:25:00', 2, 2);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 22:30:00', 1, 2);

insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 10:30:00', 3, 3);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 13:45:00', 4, 3);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 16:30:00', 5, 3);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 19:25:00', 1, 3);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-01 22:30:00', 2, 3);


insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 10:30:00', 1, 1);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 13:45:00', 2, 1);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 16:30:00', 3, 1);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 19:25:00', 4, 1);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 22:30:00', 5, 1);

insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 10:30:00', 5, 2);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 13:45:00', 4, 2);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 16:30:00', 3, 2);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 19:25:00', 2, 2);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 22:30:00', 1, 2);

insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 10:30:00', 3, 3);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 13:45:00', 4, 3);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 16:30:00', 5, 3);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 19:25:00', 1, 3);
insert into schedules (schedule_date, movie_id, theater_id) values ('2019-02-02 22:30:00', 2, 3);




