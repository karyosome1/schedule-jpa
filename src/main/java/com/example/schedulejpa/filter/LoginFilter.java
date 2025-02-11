package com.example.schedulejpa.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {
    // 인증을 하지 않아도될 URL Path 배열
    private static final String[] WHITE_LIST = {"/", "/users/signup", "/login", "/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 다양한 기능을 사용하기 위해 다운 캐스팅
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        log.info("로그인 필터 로직 실행");

        // 요청된 URI가 isWhiteList에 포함되지 않는 경우
        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);

            if (session == null || session.getAttribute("userId") == null) {
                //throw new RuntimeException("로그인 해주세요.");
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
                return;
            }
            // 로그인 성공 로직
            log.info("로그인에 성공했습니다.");
        }

        chain.doFilter(request, response);
    }

    // 로그인 여부를 확인하는 URI인지 체크하는 메서드
    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
