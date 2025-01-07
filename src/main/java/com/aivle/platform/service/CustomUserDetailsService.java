package com.aivle.platform.service;

import com.aivle.platform.domain.Member;
import com.aivle.platform.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

        // UserDetails 객체 생성 (Spring Security가 요구하는 형식)
        return User.builder()
                .username(member.getEmail())  // 이메일을 사용하여 아이디 설정
                .password(member.getPassword())  // 비밀번호 설정
                .roles(member.getRole().name())  // 권한 설정 (예: ROLE_USER)
                .build();
    }
}
