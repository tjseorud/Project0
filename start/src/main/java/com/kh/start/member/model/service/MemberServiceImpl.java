package com.kh.start.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.start.auth.model.vo.CustomUserDetails;
import com.kh.start.exception.MemberIdDuplicateException;
import com.kh.start.exception.PasswordMisMatchedException;
import com.kh.start.member.model.dao.MemberMapper;
import com.kh.start.member.model.dto.ChangePasswordDTO;
import com.kh.start.member.model.dto.MemberDTO;
import com.kh.start.member.model.vo.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final MemberMapper mapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void signUp(MemberDTO member) {
		
		MemberDTO searchedMember = mapper.getMemberByMemberId(member.getMemberId());
		
		if(searchedMember != null) {
			throw new MemberIdDuplicateException("이미 존재하는 ID 입니다.");
		}	
		
		MemberVO memberValue = MemberVO
									.builder()
									.memberId(member.getMemberId())
									.memberPw(passwordEncoder.encode(member.getMemberPw()))
									.memberName(member.getMemberName())
									.role("ROLE_USER")
									.build();
		mapper.signUp(memberValue);
		//log.info("사용자 등록 성공 : {}",member);
	}

	@Override
	public void changePassword(ChangePasswordDTO passwordEntity) {
		// 현재 비밀번호를 맞게 입력했는지
		// 맞으면 새로운 비밀번호 암호화
		// SecurityContextHolder에서 사용자 정보 받아오기
		String encodedPassword = passwordEncoder.encode(passwordEntity.getNewPassword());
		Long memberNo = passwordMatches(passwordEntity.getCurrentPassword());
		
		Map<String, Object> changeRequst = new HashMap<>();
		changeRequst.put("memberNo", memberNo);
		changeRequst.put("encodedPassword", encodedPassword);
		mapper.changePassword(changeRequst);
	}

	@Override
	public void deleteByPassword(String password) {
		// 사용자가 입력한 비밀번호와 DB에 저장된 비밀번호가 서로 맞는지 확인
		Long memberNo = passwordMatches(password);
		mapper.deleteByPassword(memberNo);
	}
	
	private Long passwordMatches(String password) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails)auth.getPrincipal();
		
		if( !passwordEncoder.matches(password, user.getPassword()) ) {
			throw new PasswordMisMatchedException("비밀번호가 일치하지 않습니다!");
		}
		
		return user.getMemberNo();
	}

}
