package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
	// Controller 타입의 어노테이션을 붙여주면 빈 스캐닝을 통해 자동 bean 등록.
public class MemberController {
	//	login.me
	
/*
	@RequestMapping(value="login.me")
		//RequestMapping 타입의 어노테이션을 붙어줌으로 HandlerMapping 등록
	public void loginMember() {
		
	}
	
	
	public void insertMember() {
		
		
	}
	
	
	public void updateMember() {
		
	}
*/	
	
	/*
	  파라미터(요청시 전달 값)를 받는 방법
	  
	  1. HttpServletRequest를 이용해서 전달받기 (기존 jsp/servlet때의 방식)
	  
	  
	*/
	@RequestMapping("login.me")
	public String loginMember(HttpServletRequest request) {
		String userId = request.getParameter("id");
		String userPwd = request.getParameter("pwd");
		
		System.out.println("id : " + userId);
		System.out.println("pwd : " + userPwd);
		
		return "main";
	}
	
	
}
