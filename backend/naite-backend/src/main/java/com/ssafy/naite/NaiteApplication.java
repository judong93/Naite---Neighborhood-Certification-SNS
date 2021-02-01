package com.ssafy.naite;

import com.ssafy.naite.interceptor.JwtInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@AllArgsConstructor
@SpringBootApplication
public class NaiteApplication implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(NaiteApplication.class, args);
    }

    /** Jwt 설치 */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/user/**") // 기본 적용 경로
                .excludePathPatterns(Arrays.asList("/user/sign/**"));// 토큰 요구 제외 경로
    }

    /** CORS 처리 */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("auth-token");
    }
}
