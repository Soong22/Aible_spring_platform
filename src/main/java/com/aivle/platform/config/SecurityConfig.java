package com.aivle.platform.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final CustomSessionExpiredStrategy customSessionExpiredStrategy;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화 설정
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 헤더 설정
        http.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // 동일 출처의 iframe 허용
        );

        // 권한 설정
        http.authorizeHttpRequests(authorize -> authorize
                // 인증 없이 접근 가능한 경로
                .requestMatchers("/", "/error", "/api/**").permitAll()
                .requestMatchers("/css/**", "/images/**", "/js/**").permitAll()
                .requestMatchers("/member/register", "/mypage").permitAll()

                // 인증이 필요한 경로
                .requestMatchers("/member/**").authenticated()
                .requestMatchers("/board/**", "/boards", "/comment/**", "/comments").authenticated()

                // 관리자 권한이 필요한 경로
                .requestMatchers("/members").hasRole("ADMIN")

                // 기타 모든 요청은 인증 필요
                .anyRequest().authenticated()
        );

        // 로그인 설정
        http.formLogin(form -> form
                .permitAll() // 로그인 페이지 접근 허용
                .loginPage("/login") // 커스텀 로그인 페이지 URL
                .loginProcessingUrl("/process-login") // 로그인 처리 URL
                .defaultSuccessUrl("/", true) // 로그인 성공 시 리다이렉트할 URL
                .failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트할 URL
        );

        // 로그아웃 설정
        http.logout(logout -> logout
                .permitAll() // 로그아웃 접근 허용
                .logoutSuccessUrl("/login?logout=true") // 로그아웃 성공 시 리다이렉트
        );

        // 중복 로그인 차단을 위한 세션 관리
        http.sessionManagement(session -> session
                .maximumSessions(1) // 세션 최대 허용 수: 1
//                .maxSessionsPreventsLogin(true) // 새로운 로그인 차단
                .maxSessionsPreventsLogin(false) // 기존 세션 만료, 새로운 로그인 허용
                .expiredSessionStrategy(customSessionExpiredStrategy) // 커스텀 전략 적용
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

}
