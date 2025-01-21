package com.aivle.platform.controller;

import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.Role;
import com.aivle.platform.dto.request.BoardRequestDto;
import com.aivle.platform.dto.response.BoardResponseDto;
import com.aivle.platform.exception.board.BoardNotFoundException;
import com.aivle.platform.exception.board.BoardUpdateFailedException;
import com.aivle.platform.service.BoardService;
import com.aivle.platform.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
            @RequestPart(value = "photoFiles", required = false) List<MultipartFile> photoFiles,
            Authentication authentication) {
        Member member = memberService.getMemberEmail(authentication.getName());

        // 이미지 파일 리스트가 null이거나 비어 있을 경우 처리
        List<String> photoUrls = new ArrayList<>();
        if (photoFiles != null && !photoFiles.isEmpty()) {
            photoUrls = photoFiles.stream()
                    .filter(file -> !file.isEmpty()) // 빈 파일 필터링
                    .map(boardService::saveFileAndGetUrl)
                    .collect(Collectors.toList());
        }

        // 이미지 URL 리스트를 요청 DTO에 설정
        request.setImageUrls(photoUrls);

        // 게시판 저장
        boardService.createBoard(request, member);

        return "redirect:/boards";
    }

    @GetMapping("/boards")
    public String getBoards(Model model, Authentication authentication,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size) {
        try {
            MemberService.addMemberInfoToModel(model, authentication);

            // 페이지 요청 파라미터 (기본값: 첫 페이지, 한 페이지당 10개 항목)
            Pageable pageable = PageRequest.of(page, size);

            // 페이징된 게시판 목록 조회
            Page<BoardResponseDto> boards = boardService.getAllBoards(pageable);


            // 모델에 멤버 목록과 페이징 정보 추가
            model.addAttribute("boards", boards); // 멤버 목록
            model.addAttribute("currentPage", page); // 현재 페이지
            model.addAttribute("totalPages", boards.getTotalPages()); // 전체 페이지 수
            model.addAttribute("totalItems", boards.getTotalElements()); // 전체 항목 수

            return "board/boards"; // register.html 페이지로 반환
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage() != null ? e.getMessage() : "알 수 없는 오류가 발생했습니다.");
            return "error/error";
        }
    }

    @GetMapping("/board/{boardId}")
    public String getBoard(@PathVariable("boardId") Long boardId, Model model, Authentication authentication) {
        try {
            MemberService.addMemberInfoToModel(model, authentication);
            BoardResponseDto board = boardService.getBoardById(boardId);

            if (authentication != null) {
                Member member = memberService.getMemberEmail(authentication.getName());

                if (member.getRole() == Role.ADMIN ||
                        Objects.equals(boardService.getBoard(boardId).getMember().getMemberId(), member.getMemberId())
                ) {
                    model.addAttribute("isTrue", true);
                }
            }

            model.addAttribute("board", board);

            return "board/board";
        } catch (BoardNotFoundException e) {
            return "redirect:/boards";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage() != null ? e.getMessage() : "알 수 없는 오류가 발생했습니다.");
            return "error/error";
        }
    }

    // 게시판수정 GET
    @GetMapping("/board/edit/{boardId}")
    public String editBoardForm(@PathVariable("boardId") Long boardId, Model model, Authentication authentication) {
        try {
            MemberService.addMemberInfoToModel(model, authentication);

            BoardResponseDto response = boardService.getBoardById(boardId);

            model.addAttribute("response", response);
            model.addAttribute("request", new BoardRequestDto());

            return "board/edit";
        } catch (BoardNotFoundException e) {
            return "redirect:/boards";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage() != null ? e.getMessage() : "알 수 없는 오류가 발생했습니다.");
            return "error/error";
        }
    }

    // 게시판수정 POST
    @PostMapping("/board/edit/{boardId}")
    public String editBoard(
            @PathVariable("boardId") Long boardId,
            @Valid @ModelAttribute("request") BoardRequestDto request,
            @RequestParam(value = "existingImageUrls", required = false) List<String> existingImageUrls,
            @RequestPart(value = "photoFiles", required = false) List<MultipartFile> photoFiles, // 새 이미지 파일
            Model model) {
        try {
            // 1) 새로 업로드된 파일을 실제 저장하고 그 URL 리스트를 만든다
            List<String> photoUrls = new ArrayList<>();
            if (photoFiles != null && !photoFiles.isEmpty()) {
                photoUrls = photoFiles.stream()
                        .filter(file -> !file.isEmpty()) // 빈 파일 필터링
                        .map(boardService::saveFileAndGetUrl) // 로컬 디스크 등에 저장 후 URL 반환
                        .toList();
            }

            // 2) “기존에 유지할 이미지” + “새로 업로드된 이미지”를 합쳐 최종 URLs로 만든다
            List<String> finalImageUrls = new ArrayList<>();
            if (existingImageUrls != null) {
                finalImageUrls.addAll(existingImageUrls);
            }
            finalImageUrls.addAll(photoUrls);

            // 3) DTO에 최종 이미지 리스트를 넣는다
            request.setImageUrls(finalImageUrls);

            // 4) 서비스 호출
            boardService.updateBoard(boardId, request);

            return "redirect:/board/" + boardId;

        } catch (BoardUpdateFailedException e) {
            return "redirect:/boards";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage() != null ? e.getMessage() : "알 수 없는 오류가 발생했습니다.");
            return "error/error";
        }
    }

}
