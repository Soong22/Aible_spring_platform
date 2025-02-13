package com.aivle.platform.service;

import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.PoliceUnit;
import com.aivle.platform.domain.type.Role;
import com.aivle.platform.dto.request.MemberRequestDto;
import com.aivle.platform.dto.response.MemberResponseDto;
import com.aivle.platform.dto.response.NotificationForMemberResponseDto;
import com.aivle.platform.exception.member.*;
import com.aivle.platform.exception.police_unit.PoliceUnitNotFoundException;
import com.aivle.platform.repository.MemberRepository;
import com.aivle.platform.repository.PoliceUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PoliceUnitRepository policeUnitRepository;
    private final PasswordEncoder passwordEncoder;

    public Member createMember(MemberRequestDto request) {
        // 이메일 중복 체크
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new MemberCreationFailedException("이미 존재하는 이메일 입니다. email: " + request.getEmail());
        }

        PoliceUnit policeUnit = policeUnitRepository.findById(request.getPoliceUnitId())
                .orElseThrow(() -> new PoliceUnitNotFoundException("존재하지 않는 지구대/파출소 정보입니다."));

        try {
            Member member = MemberRequestDto.toEntity(request, policeUnit);
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            member.setRole(Role.USER);
            member.setCreatedAt(LocalDateTime.now());

            return memberRepository.save(member);
        } catch (Exception e) {
            throw new MemberCreationFailedException("회원 가입에 실패하였습니다: " + e.getMessage(), e);

        }
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("유저를 찾을 수 없습니다. member_id: " + memberId));
    }

    @Transactional(readOnly = true)
    public Member getMemberEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("유저를 찾을 수 없습니다. email: " + email));
    }

    @Transactional(readOnly = true)
    public Boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 지구대/파출소 사용 여부 확인
    @Transactional(readOnly = true)
    public boolean checkPoliceUnitUsed(Long policeUnitId) {
        return memberRepository.existsByPoliceUnit_PoliceUnitId(policeUnitId);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMemberById(Long memberId) {
        return MemberResponseDto.fromEntity(getMember(memberId));
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("유저를 찾을 수 없습니다. email: " + email));

        return MemberResponseDto.fromEntity(member);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findMemberByNameAndPersonPhone(String memberName, String personPhone) {
        Member member = memberRepository.findByMemberNameAndPersonPhone(memberName, personPhone)
                .orElseThrow(() -> new MemberNotFoundException("유저를 찾을 수 없습니다. member_name: " + memberName + ", person_phone: " + personPhone));

        return MemberResponseDto.fromEntity(member);
    }

    @Transactional(readOnly = true)
    public Page<MemberResponseDto> getAllMembers(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);

        return members.map(MemberResponseDto::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<MemberResponseDto> getAllMembersByStationName(String stationName, Pageable pageable) {
        Page<Member> members = memberRepository.findAllByPoliceUnit_StationName(stationName, pageable);

        return members.map(MemberResponseDto::fromEntity);
    }

    // 전체 수정
    public MemberResponseDto updateMember(Long memberId, MemberRequestDto request) {
        PoliceUnit policeUnit = policeUnitRepository.findById(request.getPoliceUnitId())
                .orElseThrow(() -> new PoliceUnitNotFoundException("존재하지 않는 지구대/파출소 정보입니다."));

        try {
            Member member = getMember(memberId);
            Member newMember = MemberRequestDto.toEntity(request, policeUnit);
            newMember.setPassword(passwordEncoder.encode(newMember.getPassword()));

            member.setEmail(newMember.getEmail());
            member.setPassword(newMember.getPassword());
            member.setMemberName(newMember.getMemberName());
            member.setPersonPhone(newMember.getPersonPhone());
            member.setOfficePhone(newMember.getOfficePhone());
            member.setPoliceUnit(newMember.getPoliceUnit());

            return MemberResponseDto.fromEntity(memberRepository.save(member));
        } catch (Exception e) {
            throw new MemberUpdateFailedException("회원 수정에 실패하였습니다: " + e.getMessage(), e.getCause());
        }
    }

    // 사용자 권한만 수정
    public MemberResponseDto updateMemberRole(Long memberId, Role newRole) {
        Member member = getMember(memberId);
        member.setRole(newRole);

        return MemberResponseDto.fromEntity(memberRepository.save(member));
    }

    // 비밀번호만 변경
    public MemberResponseDto updateMemberPassword(Long memberId, String password) {
        Member member = getMember(memberId);
        member.setPassword(passwordEncoder.encode(password));

        return MemberResponseDto.fromEntity(memberRepository.save(member));
    }

    // 회원 삭제
    public void deleteMember(Long memberId) {
        try {
            Member member = getMember(memberId);
            PoliceUnit policeUnit = member.getPoliceUnit();

            // member -> policeUnit 연결 해제
            member.setPoliceUnit(null);
            member.setRole(Role.WITHDRAWN);

            // policeUnit -> member 연결도 해제
            if (policeUnit != null) {
                policeUnit.setMember(null);
            }

            // memberRepository.delete(member);
        } catch (Exception e) {
            throw new MemberDeletionFailedException("회원 삭제에 실패했습니다: " + e.getMessage(), e.getCause());
        }
    }

    // 회원 인증 처리 메소드
    public static void addMemberInfoToModel(Model model, Authentication authentication) {
        if (authentication != null) {
            String userName = authentication.getName();
            // 이메일 이라서 나중에 변경필요 static으로 이메일을 통한 이름반환가능, 이메일은 고유해서 중복 x

            boolean isUser = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            model.addAttribute("isUser", isUser);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("userName", userName);
        } else {
            model.addAttribute("isUser", false);
            model.addAttribute("isAdmin", false);
        }
    }

    // 탈퇴자가 아닌 유저 목록 조회 (지구대/파출소 정보 포함)
    public List<NotificationForMemberResponseDto> getActiveMembers() {
        return memberRepository.findActiveMembersWithPoliceUnit();
    }

    public boolean checkEmail(String email, String memberName) {
        return memberRepository.existsByEmailAndMemberName(email, memberName);
    }

    public boolean changePassword(String email, String password) {
        try {
            Member member = getMemberEmail(email);
            member.setPassword(passwordEncoder.encode(password));
            memberRepository.save(member);
            return true;
        } catch (Exception e){
            throw new MemberUpdateFailedException("회원 수정에 실패하였습니다: " + e.getMessage(), e.getCause());
        }
    }

}
