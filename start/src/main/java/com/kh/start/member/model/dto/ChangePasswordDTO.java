package com.kh.start.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {
	
	@NotBlank(message = "현재 비밀번호를 입력해주세요.")
	@Size(min = 4, max = 15, message = "비밀번호는 15글자 이하 4글자 이상만 사용가능합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "비밀번호는 영문/숫자만 사용가능합니다.")
	private String currentPassword;
	
	@NotBlank(message = "새 비밀번호를 입력해주세요.")
	@Size(min = 4, max = 15, message = "비밀번호는 15글자 이하 4글자 이상만 사용가능합니다.")
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$", message = "비밀번호 첫 글자에는 영문으로 시작해야햐며, 영문/숫자만 사용가능합니다.")
	private String newPassword;
	
}
