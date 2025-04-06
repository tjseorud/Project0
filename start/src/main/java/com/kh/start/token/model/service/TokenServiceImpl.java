package com.kh.start.token.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.start.auth.util.JwtUtil;
import com.kh.start.exception.TokenExpireException;
import com.kh.start.token.model.dao.TokenMapper;
import com.kh.start.token.model.vo.RefreshToken;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
	
	private final JwtUtil tokenUtil; 
	private final TokenMapper tokenMapper;

	@Override
	public Map<String, String> generateToken(String username, Long memberNo) {
		//AccessToken / RefreshToken
		Map<String, String> tokens = createToken(username);
		
		//3 DB 저장 (memberNo, refreshToken, expiration)
		saveToken(tokens.get("refreshToken"), memberNo);
		
		//4 만료된 토큰 지우기
		tokenMapper.deleteExpiredRefreshToken(System.currentTimeMillis());
				
		return tokens;
	}
	
	private Map<String, String> createToken(String username) {
		String accessToken = tokenUtil.getAccessToken(username);
		String refreshToken = tokenUtil.getRefreshToken(username);
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);
		
		return tokens;
	}
	
	private void saveToken(String refreshToken, Long memberNo) {
		RefreshToken token = RefreshToken
								.builder()
								.token(refreshToken)
								.memberNo(memberNo)
								.expiration(System.currentTimeMillis() + 36000000L * 72)
								.build();
		tokenMapper.saveToken(token);  //insert
	}

	@Override
	public Map<String, String> refreshToken(String refreshToken) {
		RefreshToken token = RefreshToken
								.builder()
								.token(refreshToken)
								.build();
		RefreshToken respinseToken = tokenMapper.findByToken(token);
		
		if(respinseToken == null || token.getExpiration() < System.currentTimeMillis()) {
			throw new TokenExpireException("유효하지 않은 토큰입니다.");
		}
		
		String username = getUsernameByToken(refreshToken);
		Long memberNo = respinseToken.getMemberNo();
		
		return generateToken(username, memberNo);
	}
	
	private String getUsernameByToken(String refreshToken) {
		Claims claims = tokenUtil.parseJwt(refreshToken);
		
		return claims.getSubject();
	}
	
}
