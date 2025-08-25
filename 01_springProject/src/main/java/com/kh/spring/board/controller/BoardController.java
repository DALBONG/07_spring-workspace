package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.service.BoardServiceImpl;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {
	
	@Autowired
	//자동으로 주입시켜주는 어노테이션.
	//private BoardService bservice; //원래 부모타입을 쓰는게 맞는 표현이나, 작업 편의성을 위해 우선 자식객체로.
	private BoardServiceImpl bservice;

	// 메뉴바 클릭시  /list.bo (기본적으로 1page)
	// 페이징바 클릭시 /list.bo?cpage=요청 페이지 수
/*
	@RequestMapping("list.bo")
	public String selectList(@RequestParam(value="cpage", defaultValue = "1") int currentPage, Model model) {

		int listCount = bservice.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		
		ArrayList<Board> list = bservice.selectList(pi);
		
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		
		//포워딩할 뷰 
		return "board/boardListView";
	}
*/
	@RequestMapping("list.bo")
	public ModelAndView selectList(@RequestParam(value="cpage", defaultValue = "1") int currentPage, ModelAndView mv) {

		int listCount = bservice.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		
		ArrayList<Board> list = bservice.selectList(pi);
		
		/*
		mv.addObject("pi", pi);
		mv.addObject("list", list);
		mv.setViewName("board/boardListView");
		*/
		mv.addObject("pi", pi)
		  .addObject("list", list)
		  .setViewName("board/boardListView");
		
		
		//포워딩할 뷰 
		return mv;
	}
	
	
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		
		return "board/boardEnrollForm";
	}
	
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile, HttpSession session, Model model) {
		//System.out.println(b);
		//System.out.println(upfile);
		    // 첨부파일일 선택했든 안했든 객체는 생성됨(filename에 원본파일명이 있냐 없냐 차이)
		if(!upfile.getOriginalFilename().equals("")) {
		
/*
			// 전달된 파일명이 있을 경우 -> 파일명 수정 작업후 서버에 업로드
			// -> 원본명, 서버 업로드된 경로를 b에 마저 담기.
			//파일명 수정작업 후 서버에 업로드 시키기 (~.jsp => 20250825~~~)
			String originName = upfile.getOriginalFilename(); //원본파일명 담기
			
			// 20250825~~~~~~
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // 수정파일명에 담을 날짜값
			int ramNum = (int)(Math.random()*900000 + 100000); // 수정파일명에 담을 랜덤값 
			String ext = originName.substring(originName.lastIndexOf(".")); //수정파일 확장자로 원본파일의 확장자를 가져와 담기
			
			String changeName = currentTime + ramNum + ext;
			
			// 업로드 시키려는 폴더의 물리경로 알아내기
			String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
			
			try {
				upfile.transferTo(new File(savePath, changeName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
*/			
			
			String changeName = saveFile(upfile, session);
			
			//원본명, 서버업로드된 경로 board b에 마저 담기
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		
		// 넘어온 첨부파일이 있을 경우 b : 제목, 작성자, 내용, 원본파일명, 저장경로
		// 넘어온  파일명이 없을 경우 b : 제목, 작성자, 내용 
		
		int result = bservice.insertBoard(b);
		
		if(result > 0) {//성공 -> 게시글 list페이지(list.bo)
			session.setAttribute("alertMsg", "게시글 등록 성공!");
			return "redirect:list.bo";
		}else {//실패 -> 
			model.addAttribute("errorMsg", "게시글 등록 실팽");
			return "common/errorPage";
		}
		
		
	}
	
	
	@RequestMapping("detail.bo")
	public String selectBoard(int bno, Model model) {
		// bno : 상세조회 하려는 해당 게시글 번호 담겨있음.
			
		// 해당 게시글 조회수 증가 서비스 호출 결과 받기(update)
		int upcount = bservice.increaseCount(bno);
			
		if(upcount > 0) {
			// 성공적으로 조회수 증가 
			// boardDetailView.jsp 상의 필요데이터 조회(게시글 상세정보 조회용 서비스 호출)
			// 조회된 데이터 담아, boardDetailView.jsp로 포워딩
			Board b = bservice.selectBoard(bno);
			model.addAttribute("b", b);
			return "board/boardDetailView";
		}else {
			model.addAttribute("errorMsg", "게시글 조회 실팽");
			return "common/errorPage";
				
		}
			
			
		// 조회수 증가 실패 -> 에러페이지
			
	}
		
	/*
	public int increaseCount(int boardNo, Model model) {
		int upcount = bservice.increaseCount(boardNo);
		return upcount;
	}
	*/
	

	// 현재 넘어온 첨부파일을 서버의 폴더에 저장시키는 역할
	public String saveFile(MultipartFile upfile, HttpSession session) {
		String originName = upfile.getOriginalFilename(); //원본파일명 담기
			
		// 20250825~~~~~~
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // 수정파일명에 담을 날짜값
		int ramNum = (int)(Math.random()*900000 + 100000); // 수정파일명에 담을 랜덤값 
		String ext = originName.substring(originName.lastIndexOf(".")); //수정파일 확장자로 원본파일의 확장자를 가져와 담기
			
		String changeName = currentTime + ramNum + ext;
			
		// 업로드 시키려는 폴더의 물리경로 알아내기
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");

		try {
			upfile.transferTo(new File(savePath, changeName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return changeName;
			
			
	}
	
		
		
	
}
