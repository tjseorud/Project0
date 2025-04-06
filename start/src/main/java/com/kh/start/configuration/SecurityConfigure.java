package com.kh.start.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kh.start.configuration.filter.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfigure {
	
	private final JwtFilter filter;
	
//	필터체인 정의
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {	

		return httpSecurity
					.formLogin(AbstractHttpConfigurer::disable)
					.httpBasic(AbstractHttpConfigurer::disable)
					.csrf(AbstractHttpConfigurer::disable)
					.cors(Customizer.withDefaults())
					.authorizeHttpRequests(requests -> {
						requests.requestMatchers(HttpMethod.POST, "/auth/login", "/auth/refresh", "/members").permitAll();
						requests.requestMatchers("/admin/**").hasRole("ADMIN");
						requests.requestMatchers(HttpMethod.GET,"/uploads/**", "/boards/**", "/comments/**").permitAll();
						requests.requestMatchers(HttpMethod.PUT,"/members","/boards/**").authenticated();
						requests.requestMatchers(HttpMethod.DELETE, "/members","/boards/**").authenticated();
						requests.requestMatchers(HttpMethod.POST, "/boards","/comments").authenticated();
					})
					/*
					 * sessionManagement : 세션을 어떻게 관리할것인지 지정할수 있음
					 * sessionCreationPolicy : 세션 사용 정책을 설정			
					 */
					.sessionManagement(manager ->
						manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					)
					.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
					.build();
	}
//  외부 접속용
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
//	인증용
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
//	비번용
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
