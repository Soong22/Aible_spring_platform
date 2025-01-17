package com.aivle.platform.controller;

import com.aivle.platform.domain.Member;
import com.aivle.platform.dto.request.BoardRequestDto;
import com.aivle.platform.exception.image.FileSaveException;
import com.aivle.platform.service.BoardService;
import com.aivle.platform.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    // 게시판작성 GET
    @GetMapping("/board/register")
    public String registerBoardForm(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);
        model.addAttribute("request", new BoardRequestDto());

        return "board/register";
    }

    // 게시판작성 POST
    @PostMapping("/board/register")
    public String registerBoard(
            @Valid
            @ModelAttribute("request") BoardRequestDto request,
            @RequestPart("photoFiles") List<MultipartFile> photoFiles,
            Authentication authentication,
            Model model) {
        Member member = memberService.getMemberEmail(authentication.getName());

        log.error("컨트롤러에서의 이전 개수{}", request);

        List<String> photoUrls = photoFiles.stream()
                .map(boardService::saveFileAndGetUrl)
                .collect(Collectors.toList());

        request.setImageUrls(photoUrls);

        log.error("컨트롤러에서의 이후 개수{}", request);

        boardService.createBoard(request, member);

        return "redirect:/";
    }



}
