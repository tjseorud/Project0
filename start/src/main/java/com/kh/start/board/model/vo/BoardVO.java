package com.kh.start.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter	
@AllArgsConstructor
public class BoardVO {
	
	private Long boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	private String boardFileUrl;
	private String status;
	private Date createDate;
	private Date modifyDate;
}
