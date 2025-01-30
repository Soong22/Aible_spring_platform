package com.aivle.platform.controller;

import com.aivle.platform.domain.PoliceUnit;
import com.aivle.platform.dto.request.MemberRequestDto;
import com.aivle.platform.dto.response.MemberResponseDto;
import com.aivle.platform.exception.member.MemberCreationFailedException;
import com.aivle.platform.exception.member.MemberDeletionFailedException;
import com.aivle.platform.exception.member.MemberNotFoundException;
import com.aivle.platform.exception.member.MemberUpdateFailedException;
import com.aivle.platform.service.MemberService;
import com.aivle.platform.service.PoliceUnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PoliceUnitService policeUnitService;

    // 회원가입 GET
    @GetMapping("/member/register")
    public String registerMemberForm(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        MemberRequestDto request = new MemberRequestDto();
        model.addAttribute("request", request);

        return "member/register";
    }

    // 회원가입 POST, 프론트에서 이메일 중복체크
    @PostMapping("/member/register")
    public String registerMember(@Valid @ModelAttribute("request") MemberRequestDto request, Model model) {
        try {
            memberService.createMember(request);
            return "redirect:/";
        } catch (MemberCreationFailedException e) {

            // 실패 시 다시 회원가입으로 리다이렉트
            return "redirect:/member/register";
        } catch (Exception e) {

            // 예상못한 에러의 경우 에러페이지로 리다이렉트
            model.addAttribute("errorMessage", e.getMessage() != null ? e.getMessage() : "알 수 없는 오류가 발생했습니다.");
            return "error/error";
        }

    }

    @GetMapping("/members")
    public String getMembers(Model model, Authentication authentication,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {
        try {
            MemberService.addMemberInfoToModel(model, authentication);

            // 페이지 요청 파라미터 (기본값: 첫 페이지, 한 페이지당 10개 항목)
            Pageable pageable = PageRequest.of(page, size);

            // 페이징된 멤버 목록 조회
            Page<MemberResponseDto> members = memberService.getAllMembers(pageable);

            // 경찰서 정보를 추가적으로 조회
            Map<Long, PoliceUnit> policeUnits = members.getContent().stream()
                    .map(MemberResponseDto::getPoliceUnitId) // policeUnitId가 있는 멤버만 처리
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(
                            id -> id,
                            policeUnitService::getPoliceUnitById
                    ));

            // 모델에 멤버 목록과 페이징 정보 추가
            model.addAttribute("members", members); // 멤버 목록
            model.addAttribute("policeUnits", policeUnits); // 경찰서 정보 맵
            model.addAttribute("currentPage", page); // 현재 페이지
            model.addAttribute("totalPages", members.getTotalPages()); // 전체 페이지 수
            model.addAttribute("totalItems", members.getTotalElements()); // 전체 항목 수

            return "member/members"; // register.html 페이지로 반환
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage() != null ? e.getMessage() : "알 수 없는 오류가 발생했습니다.");
            return "error/error";
        }
    }

    // 단건 조회
    @GetMapping("/member/{memberId}")
    public String getMember(@PathVariable("memberId") Long memberId, Model model, Authentication authentication) {
        try {
            MemberService.addMemberInfoToModel(model, authentication);
            MemberResponseDto member = memberService.getMemberById(memberId);

            if (member.getPoliceUnitId() != null) {
                // 경찰서 정보 맵
                model.addAttribute("policeUnit",
                        policeUnitService.getPoliceUnitById(member.getPoliceUnitId()));
            }

            model.addAttribute("member", member);

            return "member/member";
        } catch (MemberNotFoundException e) {
            return "redirect:/members";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage() != null ? e.getMessage() : "알 수 없는 오류가 발생했습니다.");
            return "error/error";
        }
    }

    // 회원수정 GET
    @GetMapping("/member/edit/{memberId}")
    public String editMemberForm(@PathVariable("memberId") Long memberId, Model model, Authentication authentication) {
        try {
            MemberService.addMemberInfoToModel(model, authentication);
            MemberResponseDto response = memberService.getMemberById(memberId);
            model.addAttribute("response", response);
            model.addAttribute("request", new MemberRequestDto());

            if (response.getPoliceUnitId() != null) {
                PoliceUnit policeUnit = policeUnitService.getPoliceUnitById(response.getPoliceUnitId());

                model.addAttribute("selectedPoliceUnitId", policeUnit.getPoliceUnitId());
                model.addAttribute("selectedDeptName", policeUnit.getDeptName());
                model.addAttribute("selectedStationName", policeUnit.getStationName());
                model.addAttribute("selectedPoliceUnitName", policeUnit.getPoliceUnitName());
            } else {
                model.addAttribute("selectedPoliceUnitId", null);
                model.addAttribute("selectedDeptName", null);
                model.addAttribute("selectedStationName", null);
                model.addAttribute("selectedPoliceUnitName", null);
            }

            return "member/edit";
        } catch (MemberNotFoundException e) {
            return "redirect:/members";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage() != null ? e.getMessage() : "알 수 없는 오류가 발생했습니다.");
            return "error/error";
        }
    }

    // 회원수정 POST
    @PostMapping("/member/edit/{memberId}")
    public String editMember(@PathVariable("memberId") Long memberId,
                             @Valid @ModelAttribute("request") MemberRequestDto request, Model model) {
        try {
            memberService.updateMember(memberId, request);

            return "redirect:/member/" + memberId;  // 수정 후 회원 상세 조회 페이지로 리디렉션
        } catch (MemberUpdateFailedException e) {
            return "redirect:/members";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage() != null ? e.getMessage() : "알 수 없는 오류가 발생했습니다.");
            return "error/error";
        }

    }

    // 회원삭제 POST
    @PostMapping("/member/delete/{memberId}")
    public String deleteMember(@PathVariable("memberId") Long memberId, Model model) {
        try {
            memberService.deleteMember(memberId);
            return "redirect:/members";
        } catch (MemberDeletionFailedException e) {
            return "redirect:/member/" + memberId;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage() != null ? e.getMessage() : "알 수 없는 오류가 발생했습니다.");
            return "error/error";
        }
    }

}
