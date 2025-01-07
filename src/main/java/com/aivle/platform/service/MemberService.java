package com.aivle.platform.service;

import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.Role;
import com.aivle.platform.dto.request.MemberRequestDto;
import com.aivle.platform.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member createMember(MemberRequestDto request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return memberRepository.save(toMember(request));
    }

    @Transactional(readOnly = true)
    public Boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public Member findMemberByNameAndPhone(String memberName, String phoneNumber) {
        return memberRepository.findByMemberNameAndPhoneNumber(memberName, phoneNumber)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<Member> getAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public Member updateMember(Long memberId, MemberRequestDto request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        Member member = getMemberById(memberId);
        member.setMemberName(request.getMemberName());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setEmail(request.getEmail());
        member.setPassword(request.getPassword());

        return memberRepository.save(member);
    }

    public Member updateMemberRole(Long memberId, Role newRole) {
        Member member = getMemberById(memberId);
        member.setRole(newRole);

        return memberRepository.save(member);
    }

    public void deleteMember(Long memberId) {
        Member member = getMemberById(memberId);

        memberRepository.delete(member);
    }

    private Member toMember(MemberRequestDto request) {
        Member member = new Member();
        member.setMemberName(request.getMemberName());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setEmail(request.getEmail());
        member.setPassword(request.getPassword());
        member.setRole(Role.GUEST);
        member.setCreatedAt(LocalDateTime.now());

        return member;
    }
}
