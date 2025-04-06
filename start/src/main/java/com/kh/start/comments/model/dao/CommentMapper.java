package com.kh.start.comments.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.start.comments.model.dto.CommentDTO;
import com.kh.start.comments.model.vo.CommentVO;

@Mapper
public interface CommentMapper {
	
	void insertComment(CommentVO commentVO);
	
	List<CommentDTO> selectCommentList(Long boardNo);
}
