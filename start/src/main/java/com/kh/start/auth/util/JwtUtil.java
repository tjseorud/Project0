package com.kh.start.auth.util;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {
	
	// 애플리케이션 설정파일(application.properties, application.yml)에 정의된
	// 속성의 값들을 클래스 내부에서 불러서 사용하고싶다!
	@Value("${jwt.secret}")
	private String secretKey;
	private SecretKey key;
	
	@PostConstruct
	public void init() {
		//log.info("{}",secretKey);
		byte[] keyArr = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyArr);
	}
	
	/*토큰 생성*/
	public String getAccessToken(String username) {
		return Jwts
				.builder()
				.subject(username)  //사용자 이름 //ID
				.issuedAt(new Date())  //발급일
				.expiration(new Date(System.currentTimeMillis() + 36000000L * 24))  //만료일
				.signWith(key)  //서명
				.compact();
	}	
	public String getRefreshToken(String username) {
		return Jwts
				.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 36000000L * 24 * 3))	//만료일
				.signWith(key)
				.compact();
	}
	
	/*토큰검증*/
	public Claims parseJwt(String token) {
		return Jwts
				.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
		
}
