package com.kh.start.member.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.start.member.model.dto.ChangePasswordDTO;
import com.kh.start.member.model.dto.MemberDTO;
import com.kh.start.member.model.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	@PostMapping
	public ResponseEntity<?> signUp(@RequestBody @Valid MemberDTO member) {
		//log.info("CT : {}",member);
		memberService.signUp(member);
		
		return ResponseEntity.status(201).build();
	}
	
	//PW 변경 기능구현
	@PutMapping
	public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDTO passwordEntity) {
		//log.info("비번 {}",passwordEntity);
		memberService.changePassword(passwordEntity);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteByPassword(@RequestBody Map<String, String> request) {
		//log.info("이거 지워줘 {}",request);
		memberService.deleteByPassword(request.get("password"));
		
		return ResponseEntity.ok("지워짐");
	}
	
}
