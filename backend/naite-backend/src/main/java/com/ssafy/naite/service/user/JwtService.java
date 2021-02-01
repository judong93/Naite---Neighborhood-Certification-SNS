package com.ssafy.naite.service.user;

import com.ssafy.naite.domain.user.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtService {

    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.expmin}")
    private Long expireMin;

    /** 로그인 성공 시 사용자 정보를 기반으로 JWT Token을 생성해서 반환한다. */
    public String create(final User user){

        final JwtBuilder jwtBuilder = Jwts.builder();

        // Header
        jwtBuilder.setHeaderParam("typ", "JWT"); // 토큰의 타입으로 고정 값 지정

        // Payload
        jwtBuilder.setSubject("auth-token")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
                .claim("user",user).claim("greeting", user.getUserNick() +"님 환영합니다.");

        // Signature : 암호화
        jwtBuilder.signWith(SignatureAlgorithm.HS256,salt.getBytes());

        final String jwt = jwtBuilder.compact();
        System.out.println("토큰발행: "+jwt);
        return jwt;
    }

    /** 전달받은 토큰이 유효한지 확인하고 문제가 있다면 Runtime 예외를 발생시킨다. */
    public void checkValid(final String jwt){
        Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
    }

    /** jwt 토큰을 분석해서 필요한 정보를 반환한다. */
    public Map<String,Object> get(final String jwt){
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
        }catch(RuntimeException e) {
            throw new RuntimeException(e);
        }
        return claims.getBody();
    }

}
