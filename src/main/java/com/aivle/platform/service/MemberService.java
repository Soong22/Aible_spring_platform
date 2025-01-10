package com.aivle.platform.service;

import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.Role;
import com.aivle.platform.dto.request.MemberRequestDto;
import com.aivle.platform.exception.MemberCreationFailedException;
import com.aivle.platform.exception.MemberDeletionFailedException;
import com.aivle.platform.exception.MemberNotFoundException;
import com.aivle.platform.exception.MemberUpdateFailedException;
import com.aivle.platform.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member createMember(MemberRequestDto request) {
        // 이메일 중복 체크
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new MemberCreationFailedException("이미 존재하는 이메일 입니다. email: " + request.getEmail());
        }

        Member member = MemberRequestDto.toEntity(request);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRole(Role.USER);
        member.setCreatedAt(LocalDateTime.now());

        try {
            return memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            throw new MemberCreationFailedException("회원가입에 실패하였습니다: ", e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public Boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("유저를 찾을 수 없습니다. member_id: " + memberId));
    }

    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("유저를 찾을 수 없습니다. email: " + email));
    }

    @Transactional(readOnly = true)
    public Member findMemberByNameAndPersonPhone(String memberName, String personPhone) {
        return memberRepository.findByMemberNameAndPersonPhone(memberName, personPhone)
                .orElseThrow(() -> new MemberNotFoundException("유저를 찾을 수 없습니다. member_name: " + memberName + ", person_phone: " + personPhone));
    }

    @Transactional(readOnly = true)
    public Page<Member> getAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    // 전체 수정
    public Member updateMember(Long memberId, MemberRequestDto request) {
        try {
            Member member = getMemberById(memberId);
            Member newMember = MemberRequestDto.toEntity(request);

            newMember.setPassword(passwordEncoder.encode(newMember.getPassword()));
            member.setEmail(newMember.getEmail());
            member.setPassword(newMember.getPassword());
            member.setMemberName(newMember.getMemberName());
            member.setPersonPhone(newMember.getPersonPhone());
            member.setOfficePhone(newMember.getOfficePhone());
            member.setDistrictName(newMember.getDistrictName());

            return memberRepository.save(member);
        } catch (Exception e) {
            throw new MemberUpdateFailedException("회원 수정에 실패하였습니다.", e);
        }
    }

    // 사용자 권한만 수정
    public Member updateMemberRole(Long memberId, Role newRole) {
        Member member = getMemberById(memberId);
        member.setRole(newRole);

        return memberRepository.save(member);
    }

    // 비밀번호만 변경
    public Member updateMemberPassword(Long memberId, String password) {
        Member member = getMemberById(memberId);
        member.setPassword(passwordEncoder.encode(password));

        return memberRepository.save(member);
    }

    // 회원 삭제
    public void deleteMember(Long memberId) {
        try {
            Member member = getMemberById(memberId);
            memberRepository.delete(member);
        } catch (Exception e) {
            throw new MemberDeletionFailedException("회원 삭제에 실패했습니다.", e);
        }
    }

    // 회원 인증 처리 메소드
    public static void addMemberInfoToModel(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            boolean isUser = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            model.addAttribute("isUser", isUser);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("username", username);
        } else {
            model.addAttribute("isUser", false);
            model.addAttribute("isAdmin", false);
        }
    }

}
