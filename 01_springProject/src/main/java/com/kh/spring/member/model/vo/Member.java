package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

	/*
	   Lombok(롬복)
	   1. 라이브러리 다운 후 적용(pom.xml)
	   2. 다운로드 된 jar파일 찾아 설치 (작업할 IDE선택하여 설치)
	   3. IDE 재실행
	   4. 주의사항! 필드를 만들 땐 앞의 2글자는 최소한 소문자로!
	*/

@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 전체 매개변수 생성자
@Getter	// getter
@Setter // setter
@ToString // toString
public class Member {

	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	//private int age;
	private String age;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	private String status;
	
	
	
	
	
}
