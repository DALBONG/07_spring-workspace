package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.service.MemberServiceImpl;
import com.kh.spring.member.model.vo.Member;

@Controller
// Controller 타입의 어노테이션을 붙여주면 빈 스캐닝을 통해 자동 bean 등록.
public class MemberController {

	// private MemberService mService = new MemberServiceImpl();
	@Autowired // DI특징(Dependency Injection)
	private MemberServiceImpl mService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

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
	 */      /*
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

	}    */
	
	
	/*
	  2. 스프링에서 제공하는 ModelAndView 객체를 이용.
	    - Model : 데이터를 맵형식(k - v) 세트로 담을 수 있는 공간.
	    - View  : 응답뷰에 대한 정보를 담을 수 있는 공간
	*/
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv) {
		/* 암호화 작업 전 과정
		Member loginUser = mService.loginMember(m);

		if (loginUser == null) {// 로그인 실패, errorPage포워딩
			mv.addObject("errorMsg", "로그인실패");
			mv.setViewName("common/errorPage");
		} else {// 로그인 성공, 메인페이지
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
		}
		return mv;
		*/
		
		// 암호화 작업 후 해야하는 과정
		// Member m userId 필드  : 사용자가 입력한 ID
		// 			userPwd 필드 : 사용자가 입력한 비번(평문)
		Member loginUser = mService.loginMember(m);
			// id만을 가지고 조회한 회원 객체가 있음. 이것으로 바로 로그인X 
			// loginUser userPwd 필드 : db에 기록된 비번(암호문)이 있음.
		
		if(loginUser != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) {
			//로그인 성공
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
			
		}else {
			//로그인 실패
			mv.addObject("errorMsg", "로그인실패");
			mv.setViewName("common/errorPage");
		}
		return mv;
		
	}
	
	
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		//세션 만료시킨 후, 메인화면으로
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		return "member/memberEnrollForm";
		
	}
	
	
	@RequestMapping("insert.me")
	// jsp의 name값을 vo의 필드명과 동일하게!! 
	public String insertMember(Member m, Model model, HttpSession session) {
		/*
		  문제 1. 한글 깨짐 => 스프링에서 제공하는 인코딩 필터 등록.(web.xml에 기술)
		  문제 2. 나이를 입력하지 않았을 경우, " " 빈문자열 넘어옴, int형을 필드에 담을 수 없어 400 에러 발생
		  		 -> vo의 int형을 String으로 바꾸면 해결은 되나,
		  		 	get set 다 바꾸는 번거로움 있음. 해결하기 위한것이 (Lombok롬복 lib를 pom.xml에 기술)
		  문제 3. DB에 비밀번호같은 민감정보는 그대로(평문) 보이면 안됌, 암호화처리 되어 보여야 함.
		   	   평문 -- (암호화 : SHA-256, SHA-512, Bcrypt) --> 암호문
		   	   		  ㄴ SHA-256, 512 : 단방향 암호화 방식(다시 되돌리는 복호화 불가능)
		   	   		  ㄴ Bcrpyt방식(salting 기법(랜덤값)) : 앞뒤로 랜덤값을 더하여 암호화 
		   	     
		   	     =>Bcrypt방식의 암호화를 통해 암호문으로 변경(스프링 시큐리티 모듈에서 제공)
		   	       라이브러리 추가 pom.xml에 spring-security.xml 파일을 pre-loading할 수 있게 등록
		*/
		//System.out.println(m);
		//System.out.println(m.getUserPwd());
		
		//암호화 작업(암호문을 만들어내는 과정)
		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd());
		
		//System.out.println(encPwd);
		m.setUserPwd(encPwd); // Member객체에 userPwd에 평문이 아닌 암호문으로 변경
		int result = mService.insertMember(m);
		
		if(result > 0) {//성공 -> 메인페이지 url재요청
			session.setAttribute("alertMsg", "가입 성공!");
			return "redirect:/";
		}else {
			model.addAttribute("errorMsg", "회원가입실팽");
			return "common/errorPage";
		}
		
		
	}

	@RequestMapping("myPage.me")
	public String myPage() {
		
		return "member/myPage";
		
	}
	
	
	@RequestMapping("update.me")
	public String updateMember(Member m, Model model, HttpSession session) {
		
		int result = mService.updateMember(m);
		
		if(result > 0) { //성공
			//db로부터 수정된 회원정보 다시 조회해서, 세션 갈아끼워야 함. 
			// session의 loginUser라는 키값으로 덮어씌워야 함.
			Member updateMem = mService.loginMember(m);
			session.setAttribute("loginUser", updateMem);
				// 한 줄로 줄이기 
				//session.setAttribute("loginUser", mService.loginMember(m))
			// alert 띄워줄 문구 
			session.setAttribute("alertMsg", "변경 성공!");
			
			//myPage로 재 요청
			return "redirect:myPage.me";
		}else {//실패
			model.addAttribute("errorMSG", "정보 변경 실팽!");
			return "common/errorPage";
			
		}
		
	}
	
	@RequestMapping("delete.me")
	public String deleteMember(String userId, String userPwd, Model model, HttpSession session) {
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null && bcryptPasswordEncoder.matches(userPwd, loginUser.getUserPwd())) {
			model.addAttribute("errorMsg", "비밀번호 미일치!");
			return "common/errorPage";
			
		}
		
		
		int result = mService.deleteMember(userId);
		
		if(result > 0) {//성공
			session.invalidate();
			return "redirect:/";
		}else {//삭제
			model.addAttribute("errorMsg", "탈퇴실팽!");
			return "common/errorPage";
		}
		
		/*
		 if(loginUser != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) {
			//로그인 성공
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
		}else {
			//로그인 실패
			mv.addObject("errorMsg", "로그인실패");
			mv.setViewName("common/errorPage");
		} 
		 */
		
	}
	
	

}
