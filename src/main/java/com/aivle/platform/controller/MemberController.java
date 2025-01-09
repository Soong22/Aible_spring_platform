package com.aivle.platform.controller;

import com.aivle.platform.domain.Member;
import com.aivle.platform.dto.request.MemberRequestDto;
import com.aivle.platform.dto.response.MemberResponseDto;
import com.aivle.platform.exception.MemberCreationFailedException;
import com.aivle.platform.exception.MemberDeletionFailedException;
import com.aivle.platform.exception.MemberNotFoundException;
import com.aivle.platform.exception.MemberUpdateFailedException;
import com.aivle.platform.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    // 회원가입 GET
    @GetMapping("/register")
    public String registerMemberForm(Model model) {
        MemberRequestDto request = new MemberRequestDto();
        model.addAttribute("request", request);
        return "member/register";
    }

    // 회원가입 POST, 프론트에서 이메일 중복체크
    @PostMapping("/register")
    public String registerMember(@Valid @ModelAttribute("request") MemberRequestDto request) {
        try {
            memberService.createMember(request);
            return "redirect:/";
        } catch (MemberCreationFailedException e) {
            // 실패 시 다시 회원가입으로 리다이렉트
            return "redirect:/member/register";
        } catch (Exception e) {
            // 예상못한 에러의 경우 에러페이지로 리다이렉트
            return "/error";
        }
    }

    @GetMapping("/")
    public String getMembers(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {
        // 페이지 요청 파라미터 (기본값: 첫 페이지, 한 페이지당 10개 항목)
        Pageable pageable = PageRequest.of(page, size);

        // 페이징된 멤버 목록 조회
        Page<Member> members = memberService.getAllMembers(pageable);

        // 모델에 멤버 목록과 페이징 정보 추가
        model.addAttribute("members", members.getContent()); // 멤버 목록
        model.addAttribute("currentPage", page); // 현재 페이지
        model.addAttribute("totalPages", members.getTotalPages()); // 전체 페이지 수
        model.addAttribute("totalItems", members.getTotalElements()); // 전체 항목 수

        return "member/members"; // register.html 페이지로 반환
    }

    // 단건 조회
    @GetMapping("/{memberId}")
    public String getMember(@PathVariable("memberId") Long memberId, Model model) {
        try {
            Member member = memberService.getMemberById(memberId);
            model.addAttribute("member", member);
            return "member/member";
        } catch (MemberNotFoundException e) {
            return "redirect:/member/";
        } catch (Exception e) {
            return "/error";
        }
    }

    // 회원수정 GET
    @GetMapping("/edit/{memberId}")
    public String editMemberForm(@PathVariable("memberId") Long memberId, Model model) {
        try {
            MemberResponseDto response = MemberResponseDto.toDto(memberService.getMemberById(memberId));
            model.addAttribute("response", response);
            model.addAttribute("request", new MemberRequestDto());
            return "member/edit";
        } catch (MemberNotFoundException e) {
            return "redirect:/member/";
        } catch (Exception e) {
            return "/error";
        }
    }

    // 회원수정 POST
    @PostMapping("/edit/{memberId}")
    public String editMember(@PathVariable("memberId") Long memberId,
                             @Valid @ModelAttribute("request") MemberRequestDto request) {
        try {
            memberService.updateMember(memberId, request);

            return "redirect:/member/" + memberId;  // 수정 후 회원 상세 조회 페이지로 리디렉션
        } catch (MemberUpdateFailedException e) {
            return "redirect:/member/";
        } catch (Exception e) {
            return "/error";
        }

    }

    // 회원삭제 POST
    @PostMapping("/delete/{memberId}")
    public String deleteMember(@PathVariable("memberId") Long memberId) {
        try{
            memberService.deleteMember(memberId);
            return "redirect:/member/";
        } catch (MemberDeletionFailedException e) {
            return "redirect:/member/" + memberId;
        } catch (Exception e) {
            return "/error";
        }
    }

}
