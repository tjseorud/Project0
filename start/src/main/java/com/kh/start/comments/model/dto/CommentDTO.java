package com.kh.start.comments.model.dto;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDTO {
		
	private Long commentNo;
	
	@NotBlank(message = "댓글은 없을 수 없음")
	private String commentContent;
	private String commentWriter;
	private Date createDate; 
	private Long refBoardNo;

}
