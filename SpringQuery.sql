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