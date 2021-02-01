package com.ssafy.naite.interceptor;

import com.ssafy.naite.service.user.JwtService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws Exception {
        System.out.println(req.getMethod()+" : "+req.getServletPath());

        // option 요청 통과
        if(req.getMethod().equals("OPTIONS")) {
            return true;
        }else {
            String token = req.getHeader("auth-token");

            if(token != null && token.length() > 0){
                // 토큰이 존재하면 유효성 검사
                jwtService.checkValid(token);
                return true;
            }
            else {
                throw new Exception("인증 토큰이 없습니다.");
            }
        }
    }
}
