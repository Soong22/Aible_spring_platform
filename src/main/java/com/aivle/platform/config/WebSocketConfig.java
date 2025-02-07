package com.aivle.platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 엔드포인트 설정 (SockJS fallback 지원)
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트에서 메시지를 보낼 때 사용하는 prefix
        registry.setApplicationDestinationPrefixes("/app");
        // 서버가 구독(브로드캐스트)할 때 사용하는 prefix
        registry.enableSimpleBroker("/topic", "/queue");
        // 개별 사용자 전용 메시지를 보낼 때 사용하는 prefix
        registry.setUserDestinationPrefix("/user");
    }
}
