package com.aivle.platform.config;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSessionExpiredStrategy implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        HttpServletResponse response = event.getResponse();
        response.setContentType("text/html;charset=UTF-8");

        // Alert 메시지와 리디렉션 스크립트 작성
        String alertScript = """
            <script>
                alert('다른 기기에서 로그인이 되어 로그아웃 되었습니다.');
                window.location.href = '/'; // 홈으로 리디렉션
            </script>
        """;

        response.getWriter().write(alertScript);
    }
}
