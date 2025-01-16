package com.aivle.platform.controller;

import com.aivle.platform.dto.request.BoardRequestDto;
import com.aivle.platform.service.BoardService;
import com.aivle.platform.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시판작성 GET
    @GetMapping("/board/register")
    public String registerBoardForm(Model model, Authentication authentication) {
        // 인증된 사용자 정보를 모델에 추가
        if (authentication != null && authentication.isAuthenticated()) {
            MemberService.addMemberInfoToModel(model, authentication);
        } else {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }

        BoardRequestDto request = new BoardRequestDto();
        model.addAttribute("request", request);

        return "board/register";
    }

}
