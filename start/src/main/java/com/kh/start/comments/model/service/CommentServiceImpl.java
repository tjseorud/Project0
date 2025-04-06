package com.kh.start.comments.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.start.auth.model.vo.CustomUserDetails;
import com.kh.start.auth.service.AuthService;
import com.kh.start.board.model.service.BoardService;
import com.kh.start.comments.model.dao.CommentMapper;
import com.kh.start.comments.model.dto.CommentDTO;
import com.kh.start.comments.model.vo.CommentVO;
import com.kh.start.exception.InvalidUserRequestException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	
	private final CommentMapper commentMapper;
	private final BoardService boardService;
	private final AuthService authService;

	@Override
	public void insertComment(CommentDTO comment) {
		// 게시글이 있는가?
		//log.info("{}",comment);
		boardService.findById(comment.getRefBoardNo());		
		// 요거 요청한 사용자랑 토큰 소유주랑 같아?
		String tokenMemberNo = String.valueOf(((CustomUserDetails)authService.getUserDetails()).getMemberNo());
		//log.info("tokenMemberNo: {}",tokenMemberNo);
		
		if( !tokenMemberNo.equals(comment.getCommentWriter()) ) {
			throw new InvalidUserRequestException("누구세요?");
		}
		
		CommentVO requestData = CommentVO.builder()
											.commentWriter(Long.parseLong(tokenMemberNo))
											.commentContent(comment.getCommentContent())
											.refBoardNo(comment.getRefBoardNo())
											.build();
		//log.info("requestData: {}",requestData);
		commentMapper.insertComment(requestData);
	}

	@Override
	public List<CommentDTO> selectCommentList(Long boardNo) {
		boardService.findById(boardNo);
		return commentMapper.selectCommentList(boardNo);
	}

}
