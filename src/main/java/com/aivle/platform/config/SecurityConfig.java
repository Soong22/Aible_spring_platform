package com.aivle.platform.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호 암호화 설정
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        // H2 콘솔에서 프레임 사용 허용
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/index", "/all", "/login", "/check-email/**", "/h2-console/**").permitAll()
                        .requestMatchers("/alld").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .permitAll()
                        .loginProcessingUrl("/index")
                        .defaultSuccessUrl("/index", true)
                        .failureUrl("/login")
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable);
//
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers( "/register", "/index" ,"/all", "/login","/check-email/**", "/h2-console/**").permitAll()  // 로그인, 회원가입은 누구나 접근 가능
//                        .requestMatchers("/alld").hasRole("ADMIN")     // /admin/ 경로는 관리자만 접근 가능
//                        .anyRequest().authenticated()                   // 그 외의 모든 요청은 인증된 사용자만 접근 가능
//                )
////                .formLogin(form -> form
////                        .loginPage("/login") // 커스텀 로그인 페이지
////                        .permitAll()
////                )
////                .logout(LogoutConfigurer::permitAll
////                );
//                .formLogin(form -> form
//                        .permitAll() // 기본 로그인 페이지를 사용하므로 모든 사용자에게 접근 허용
//                        .loginProcessingUrl("/index")
//                        .defaultSuccessUrl("/index", true)
//                        .failureUrl("/login")
//                )
//                .logout(logout -> logout.permitAll()); // 로그아웃 설정
//
//        return http.build();
//    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService) // UserDetailsService 설정
                .passwordEncoder(passwordEncoder());     // 비밀번호 암호화 방식 설정
        return authenticationManagerBuilder.build();
    }
}
