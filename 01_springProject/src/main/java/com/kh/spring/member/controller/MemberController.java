package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.service.MemberServiceImpl;
import com.kh.spring.member.model.vo.Member;

@Controller
// Controller 타입의 어노테이션을 붙여주면 빈 스캐닝을 통해 자동 bean 등록.
public class MemberController {

	// private MemberService mService = new MemberServiceImpl();
	@Autowired // DI특징(Dependency Injection)
	private MemberServiceImpl mService;

	/*
	 * @RequestMapping(value="login.me") //RequestMapping 타입의 어노테이션을 붙어줌으로
	 * HandlerMapping 등록 public void loginMember() {
	 * }

	 * public void insertMember() {
	 * }

	 * public void updateMember() {
	 * }
	 */

	/*
	 * 파라미터(요청시 전달 값)를 받는 방법
	 * 
	 * 1. HttpServletRequest를 이용해서 전달받기 (기존 jsp/servlet때의 방식) 해당 메소드의 매개변수로
	 * HttpServletRequest를 작성해두면, Spring 컨테이너(Spring)가 해당 메소드 호출(실행)시 자동으로 해당 객체를
	 * 생성해서 인자로 주입
	 */
	/*
	 * @RequestMapping("login.me") public String loginMember(HttpServletRequest
	 * request) { String userId = request.getParameter("id"); String userPwd =
	 * request.getParameter("pwd");
	 * 
	 * System.out.println("id : " + userId); System.out.println("pwd : " + userPwd);
	 * 
	 * return "main"; }
	 */
	// ========================================================================================

	/*
	 * 2. @RequestParam 어노테이션 이용 request,getParameter("키") : 밸류의 역할을 대신해주는 어노테이션
	 */ /*
		 * @RequestMapping("/login.me") public String
		 * loginMember(@RequestParam(value="id", defaultValue = "aaa") String userId,
		 * 
		 * @RequestParam(value="pwd") String userPwd) {
		 * 
		 * System.out.println("id : " + userId); System.out.println("pwd : " + userPwd);
		 * 
		 * return "main"; }
		 */
	// ========================================================================================

	/*
	 * 3. @RequestParam 어노테이션을 생략하는 방법 단, 매개변수 명과 .jsp의 name값을 동일하게 세팅해야 값이 자동으로
	 * 주입됨.
	 */ /*
		 * @RequestMapping("login.me") public String loginMember(String id, String pwd)
		 * {
		 * 
		 * System.out.println("id : " + id); System.out.println("pwd : " + pwd);
		 * 
		 * return "main"; }
		 */

	// ========================================================================================

	/*
	 * 4. 커맨드 객체 방식 해당 메소드의 매개변수로 요청시 전달값을 담고자하는 vo클래스 타입을 셋팅 후, 요청시 전달값의 키값(jsp의
	 * name)을 vo클래스의 담고자하는 필드명으로 작성
	 * 
	 * -> 스프링이 해당 객체를 기본생성자로 생성후, setter메소드를 찾아 요청시 전달값을 해당 필드에 담아주는 내부적인 원리.
	 * 
	 * !! 반드시 jsp의 name값과 필드명을 동일하게!
	 */

	/*
	 * 요청처리 후, 응답페이지로 포워딩 또는 url재요청하는 응답데이터 담는 법
	 * 
	 * 1. 스프링에서 제공하는 Model객체를 사용하는 방법. 포워딩할 뷰로 전달하고자 하는 데이터를 맵 형식(key - value)으로 담을
	 * 수 있는 영역 model객체 : requestScope 단, setAttribute가 아닌 addAttribute 메소드 이용.
	 */
	@RequestMapping("login.me")
	public String loginMember(Member m, Model model, HttpSession session) {

		Member loginUser = mService.loginMember(m);

		if (loginUser == null) {// 로그인 실패, errorPage포워딩
			model.addAttribute("errorMsg", "로그인실팽");
			return "common/errorPage";
		} else {// 로그인 성공, 메인페이지
			session.setAttribute("loginUser", loginUser);
			return "redirect:/"; // url 재요청 구문
		}

	}

}
