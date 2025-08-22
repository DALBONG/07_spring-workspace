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
       ���̵�
     , ��ȣ���
     , �̸�
     , ����
     , ����
     , ����
     , ��ȣ
     , �ּ�
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