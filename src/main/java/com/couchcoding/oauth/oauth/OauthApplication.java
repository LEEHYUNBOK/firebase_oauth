package com.couchcoding.oauth.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")// 이 경로에 접근하는
						.allowedOrigins("*") // 자원 공유를 허락할 Origin 지정
						.allowedMethods("GET", "POST") // 허용할 HTTP method 지정
						.maxAge(3000); // 설정 시간만큼 pre-flight 리퀘스트 캐싱
			}
		};
	}
}
