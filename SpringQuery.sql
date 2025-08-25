select
       user_id
     , user_pwd
     , user_name
     , email
     , gender
     , age
     , phone
     , address
     , enroll_date
     , modify_date
     , status
  from member
 where status = 'Y'
   and user_id = ?
   and user_pwd = ? ; 
   
insert
  into member
  (
       user_id
     , user_pwd
     , user_name
     , email
     , gender
     , age
     , phone
     , address
  )
  values
  (
       아이디
     , 암호비번
     , 이름
     , 메일
     , 성별
     , 나이
     , 번호
     , 주소
  ) ; 
  
update member
   set email = ?
     , gender = ?
     , age = ?
     , phone = ?
     , address = ?
     , modify_date = sysdate
 where user_id = ? ; 
 
update member
   set status = 'N'
 where user_id = ? 
   and status = 'Y' ; 
   
select 
       count(*)
  from board
 where status = 'Y' ;
 
 
select board_no
     , board_title
     , board_writer
     , count
     , to_char(create_date, 'YYYY-MM-DD') as "create_date"
     , origin_name
  from board
 where status = 'Y'
 order 
    by board_no desc ;
    
insert 
  into board
  (
       board_no
     , board_title
     , board_writer
     , board_content
     , origin_name
     , change_name
  )
  values
  (
       seq_bno.nextval
     , #{boardTitle}
     , #{boardWriter}
     , #{boardContent}
     , #{originName}
     , #{changeName}
  ) ;
  
update board
   set count = count + 1
 where board_no = ? ; 
 
select 
       board_no
     , board_title
     , board_writer
     , create_date
     , origin_name
     , board_content
  from board
 where status = 'Y' ;