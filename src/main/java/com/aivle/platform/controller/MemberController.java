package com.aivle.platform.controller;

import com.aivle.platform.domain.Member;
import com.aivle.platform.dto.request.MemberRequestDto;
import com.aivle.platform.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        MemberRequestDto request = new MemberRequestDto();
        model.addAttribute("request", request);
        return "register";
    }

    // 프론트에서 중복체크
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("request") MemberRequestDto request) {
        try {
            memberService.createMember(request);
            return "redirect:/index";
        } catch (IllegalArgumentException e) {
            return "register";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/all")
    public String allForm(Model model,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size) {
        // 페이지 요청 파라미터 (기본값: 첫 페이지, 한 페이지당 10개 항목)
        Pageable pageable = PageRequest.of(page, size);

        // 페이징된 멤버 목록 조회
        Page<Member> allMembers = memberService.getAllMembers(pageable);

        // 모델에 멤버 목록과 페이징 정보 추가
        model.addAttribute("members", allMembers.getContent()); // 멤버 목록
        model.addAttribute("currentPage", page); // 현재 페이지
        model.addAttribute("totalPages", allMembers.getTotalPages()); // 전체 페이지 수
        model.addAttribute("totalItems", allMembers.getTotalElements()); // 전체 항목 수

        return "all"; // register.html 페이지로 반환
    }


}
