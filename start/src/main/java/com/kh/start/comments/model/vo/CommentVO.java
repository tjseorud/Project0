package com.kh.start.comments.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
public class CommentVO {
	
	private Long commentNo;
	private String commentContent;
	private Long commentWriter;
	private Date createDate; 
	private Long refBoardNo;
}
