package com.kh.spring.board.model.service;

import java.util.ArrayList;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardService {

	// 1. 게시판 리스트 페이지.(페이징 처리)
	int selectListCount();
	ArrayList<Board> selectList(PageInfo pi);
	
	// 2. 게시글 작성
	int insertBoard(Board b);
	
	// 3. 게시글 상세조회
	int increaseCount(int boardNo);
	Board selectBoard(int boardNo);
	
	// 4. 게시글 삭제
	int deleteBoard(int boardNo);
	
	// 5. 게시글 수정
	int updateBoard(Board b);
	
	// 6. 댓글 리스트 조회(ajax)
	ArrayList<Reply> selectReplyList(int boardNo); //게시글 번호를 넘겨줘야 해당 게시글의 댓글 조회할 수 있음.
	
	// 7. 댓글 작성
	int insertReply(Reply r);

	
}
