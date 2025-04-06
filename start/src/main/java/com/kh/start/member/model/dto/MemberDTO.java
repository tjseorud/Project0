package com.kh.start.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {

	private Long memberNo;
	
	@NotBlank(message = "아이디는 비어있을 수 없습니다.")	//NULL 금지
	@Size(min = 5, max = 15, message = "아이디는 15글자 이하 5글자 이상만 사용가능합니다.")
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$", message = "아이디 첫 글자에는 영문으로 시작해야햐며, 영문/숫자만 사용가능합니다.")		//정규표현식
	private String memberId;
	
	@NotBlank(message = "비밀번호는 비어있을 수 없습니다.")	//NULL 금지
	@Size(min = 4, max = 15, message = "비밀번호는 15글자 이하 4글자 이상만 사용가능합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "비밀번호는 영문/숫자만 사용가능합니다.")	//정규표현식
	private String memberPw;
	
	private String memberName;
	private String role;
}
