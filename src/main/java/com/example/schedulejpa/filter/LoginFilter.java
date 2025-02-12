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

    // 화이트리스트의 역할: 특정 URL(예: 로그인 페이지, 회원가입 페이지)은 인증 없이 접근할 수 있도록 허용
    private static final String[] WHITE_LIST = {"/", "/users/signup", "/login", "/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 다양한 기능을 사용하기 위해 다운 캐스팅
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        log.info("로그인 필터 로직 실행");

        // 요청된 URI가 WhiteList에 포함되어 있는지 검사
        // 포함되어 있다면 인증 없이 요청 처리. 포함되어 있지 않다면 로그인 여부 확인
        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);

            // 로그인 확인
            // 세션에 userId가 존재하지 않으면 "로그인이 필요합니다" 메시지와 401 Unauthorized 에러를 반환
            if (session == null || session.getAttribute("userId") == null) {
                //throw new RuntimeException("로그인 해주세요.");
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
                return;
            }
            // 로그인 성공 로직
            log.info("로그인에 성공했습니다.");
        }

        // 다음 필터 또는 서블릿으로 요청을 전달
        chain.doFilter(request, response);
    }

    // 요청된 URI가 화이트리스트에 포함되는지 체크하는 메서드
    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
