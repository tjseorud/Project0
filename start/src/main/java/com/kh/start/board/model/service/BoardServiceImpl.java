package com.kh.start.board.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.start.auth.model.vo.CustomUserDetails;
import com.kh.start.auth.service.AuthService;
import com.kh.start.board.model.dao.BoardMapper;
import com.kh.start.board.model.dto.BoardDTO;
import com.kh.start.board.model.vo.BoardVO;
import com.kh.start.exception.NotFindException;
import com.kh.start.file.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardMapper;
	private final AuthService authService;
	private final FileService fileService;

	@Override
	public void save(BoardDTO board, MultipartFile file) {
		// board에는 개시글 정보
		// file 파일이 존재할 경우 파일의 정보
		CustomUserDetails user = authService.getUserDetails();		
		Long memberNo = user.getMemberNo();
		board.setBoardWriter(String.valueOf(memberNo));
		
		// 첨부파일 관련된 값
		BoardVO requestData = null;
		if(file != null && !file.isEmpty()) {	/*Not NULL And 비어있지 않으면*/
			//파일 올리기 -> 모두 파일서비스로
			String filePath = fileService.store(file);
			requestData = BoardVO
							.builder()
							.boardTitle(board.getBoardTitle())
							.boardContent(board.getBoardContent())
							.boardWriter(String.valueOf(memberNo))
							.boardFileUrl(filePath)
							.build();
		}else {
			requestData = BoardVO
							.builder()
							.boardTitle(board.getBoardTitle())
							.boardContent(board.getBoardContent())
							.boardWriter(String.valueOf(memberNo))
							.build();
		}
		//log.info("save: {}",requestData.getBoardTitle());
		boardMapper.save(requestData);
	}

	@Override
	public List<BoardDTO> findAll(int pageNo) {
		int size = 5;
		RowBounds rowBounds = new RowBounds(pageNo * size, size);
		return boardMapper.findAll(rowBounds);
	}

	@Override
	public BoardDTO findById(Long boardNo) {
		return getBoardOrThrow(boardNo);
	}
	
	/*게시글 있나?*/
	private BoardDTO getBoardOrThrow(Long boardNo) {
		BoardDTO board = boardMapper.findById(boardNo);
		
		if(board == null) {
			throw new NotFindException("게시글이 없어요!");
		}
		return board;
	}

	@Override
	public BoardDTO update(BoardDTO board, MultipartFile file) {
		
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.store(file);
			board.setBoardFileUrl(filePath);
		}
		
		boardMapper.update(board);
		return board;
	}

	@Override
	public void deleteById(Long boardNo) {
		boardMapper.deleteById(boardNo);
	}

}
