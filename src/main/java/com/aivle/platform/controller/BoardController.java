package com.aivle.platform.controller;

import com.aivle.platform.domain.Board;
import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.type.Status;
import com.aivle.platform.dto.request.BoardRequestDto;
import com.aivle.platform.dto.response.BoardResponseDto;
import com.aivle.platform.exception.board.BoardCreationFailedException;
import com.aivle.platform.exception.board.BoardDeletionFailedException;
import com.aivle.platform.exception.board.BoardNotFoundException;
import com.aivle.platform.exception.board.BoardUpdateFailedException;
import com.aivle.platform.service.BoardService;
import com.aivle.platform.service.FileService;
import com.aivle.platform.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
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
            Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            Member member = memberService.getMemberEmail(authentication.getName());

            // 이미지 파일 리스트가 null이거나 비어 있을 경우 처리
            List<String> photoUrls = new ArrayList<>();
            if (photoFiles != null && !photoFiles.isEmpty()) {
                photoUrls = photoFiles.stream()
                        .filter(file -> !file.isEmpty()) // 빈 파일 필터링
                        .map(FileService::saveFileAndGetUrl)
                        .collect(Collectors.toList());
            }

            // 이미지 URL 리스트를 요청 DTO에 설정
            request.setImageUrls(photoUrls);

            // 게시판 저장
            Board board = boardService.createBoard(request, member);

            return "redirect:/board/" + board.getBoardId();
        } catch (BoardCreationFailedException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/board/register";
        }
    }

    // 게시판작성(공지사항) GET
    @GetMapping("/board/register-important")
    public String registerBoardImportantForm(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);
        model.addAttribute("request", new BoardRequestDto());

        return "board/registerImportant";
    }

    // 게시판작성(공지사항) POST
    @PostMapping("/board/register-important")
    public String registerBoardImportant(
            @Valid
            @ModelAttribute("request") BoardRequestDto request,
            @RequestPart(value = "photoFiles", required = false) List<MultipartFile> photoFiles,
            Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            Member member = memberService.getMemberEmail(authentication.getName());

            // 이미지 파일 리스트가 null이거나 비어 있을 경우 처리
            List<String> photoUrls = new ArrayList<>();
            if (photoFiles != null && !photoFiles.isEmpty()) {
                photoUrls = photoFiles.stream()
                        .filter(file -> !file.isEmpty()) // 빈 파일 필터링
                        .map(FileService::saveFileAndGetUrl)
                        .collect(Collectors.toList());
            }

            // 이미지 URL 리스트를 요청 DTO에 설정
            request.setImageUrls(photoUrls);

            // 게시판 저장
            Board board = boardService.createBoardByStatus(request, member, Status.IMPORTANT);

            return "redirect:/board/" + board.getBoardId();
        } catch (BoardCreationFailedException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/board/register";
        }
    }

    // 게시판작성(보고서) GET
    @GetMapping("/board/register-report")
    public String registerBoardReportForm(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);
        model.addAttribute("request", new BoardRequestDto());

        return "board/registerReport";
    }

    // 게시판작성(보고서) POST
    @PostMapping("/board/register-report")
    public String registerBoardReport(
            @Valid
            @ModelAttribute("request") BoardRequestDto request,
            @RequestPart(value = "photoFiles", required = false) List<MultipartFile> photoFiles,
            Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            Member member = memberService.getMemberEmail(authentication.getName());

            // 이미지 파일 리스트가 null이거나 비어 있을 경우 처리
            List<String> photoUrls = new ArrayList<>();
            if (photoFiles != null && !photoFiles.isEmpty()) {
                photoUrls = photoFiles.stream()
                        .filter(file -> !file.isEmpty()) // 빈 파일 필터링
                        .map(FileService::saveFileAndGetUrl)
                        .collect(Collectors.toList());
            }

            // 이미지 URL 리스트를 요청 DTO에 설정
            request.setImageUrls(photoUrls);

            // 게시판 저장
            Board board = boardService.createBoardByStatus(request, member, Status.PENDING);

            return "redirect:/board/" + board.getBoardId();
        } catch (BoardCreationFailedException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/board/register";
        }
    }

    // 전체 게시글조회
    @GetMapping("/boards")
    public String getBoards(Model model, Authentication authentication,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size) {

        MemberService.addMemberInfoToModel(model, authentication);

        // 최신 작성순 정렬 추가 (createdAt 기준 내림차순)
        // Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 페이지 요청 파라미터 (기본값: 첫 페이지, 한 페이지당 5개 항목)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 페이징된 게시판 목록 조회
        Page<BoardResponseDto> boards = boardService.getAllBoards(pageable);


        // 모델에 멤버 목록과 페이징 정보 추가
        model.addAttribute("boards", boards); // 멤버 목록
        model.addAttribute("currentPage", page); // 현재 페이지
        model.addAttribute("totalPages", boards.getTotalPages()); // 전체 페이지 수
        model.addAttribute("totalItems", boards.getTotalElements()); // 전체 항목 수

        return "board/boards";

    }

    @GetMapping("/boards/important")
    public String getBoardsImportant(Model model, Authentication authentication,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size) {

        MemberService.addMemberInfoToModel(model, authentication);

        // 페이지 요청 파라미터 (기본값: 첫 페이지, 한 페이지당 5개 항목)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 페이징된 게시판 목록 조회
        List<Status> statuses = List.of(Status.IMPORTANT);
        Page<BoardResponseDto> boards = boardService.getAllBoardsByStatus(statuses, pageable);

        // 모델에 멤버 목록과 페이징 정보 추가
        model.addAttribute("boards", boards); // 멤버 목록
        model.addAttribute("currentPage", page); // 현재 페이지
        model.addAttribute("totalPages", boards.getTotalPages()); // 전체 페이지 수
        model.addAttribute("totalItems", boards.getTotalElements()); // 전체 항목 수

        return "board/boardsImportant";
    }

    @GetMapping("/boards/report")
    public String getBoardsReport(Model model, Authentication authentication,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size) {

        MemberService.addMemberInfoToModel(model, authentication);

        // 페이지 요청 파라미터 (기본값: 첫 페이지, 한 페이지당 5개 항목)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 페이징된 게시판 목록 조회
        List<Status> statuses = List.of(Status.PENDING, Status.COMPLETED);
        Page<BoardResponseDto> boards = boardService.getAllBoardsByStatus(statuses, pageable);

        // 모델에 멤버 목록과 페이징 정보 추가
        model.addAttribute("boards", boards); // 멤버 목록
        model.addAttribute("currentPage", page); // 현재 페이지
        model.addAttribute("totalPages", boards.getTotalPages()); // 전체 페이지 수
        model.addAttribute("totalItems", boards.getTotalElements()); // 전체 항목 수

        return "board/boardsReport";
    }

    @GetMapping("/boards/report-unread")
    public String getBoardsReportUnread(Model model, Authentication authentication,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {

        MemberService.addMemberInfoToModel(model, authentication);

        // 페이지 요청 파라미터 (기본값: 첫 페이지, 한 페이지당 5개 항목)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 페이징된 게시판 목록 조회
        List<Status> statuses = List.of(Status.PENDING);
        Page<BoardResponseDto> boards = boardService.getAllBoardsByStatus(statuses, pageable);

        // 모델에 멤버 목록과 페이징 정보 추가
        model.addAttribute("boards", boards); // 멤버 목록
        model.addAttribute("currentPage", page); // 현재 페이지
        model.addAttribute("totalPages", boards.getTotalPages()); // 전체 페이지 수
        model.addAttribute("totalItems", boards.getTotalElements()); // 전체 항목 수

        return "board/boardsReportUnread";
    }

    @GetMapping("/boards/report-read")
    public String getBoardsReportRead(Model model, Authentication authentication,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size) {

        MemberService.addMemberInfoToModel(model, authentication);

        // 페이지 요청 파라미터 (기본값: 첫 페이지, 한 페이지당 5개 항목)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 페이징된 게시판 목록 조회
        List<Status> statuses = List.of(Status.COMPLETED);
        Page<BoardResponseDto> boards = boardService.getAllBoardsByStatus(statuses, pageable);

        // 모델에 멤버 목록과 페이징 정보 추가
        model.addAttribute("boards", boards); // 멤버 목록
        model.addAttribute("currentPage", page); // 현재 페이지
        model.addAttribute("totalPages", boards.getTotalPages()); // 전체 페이지 수
        model.addAttribute("totalItems", boards.getTotalElements()); // 전체 항목 수

        return "board/boardsReportRead";
    }

    @GetMapping("/board/{boardId}")
    public String getBoard(@PathVariable("boardId") Long boardId, Model model,
                           Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            MemberService.addMemberInfoToModel(model, authentication);
            BoardResponseDto board = boardService.getBoardById(boardId);

            if (authentication != null) {
                Member member = memberService.getMemberEmail(authentication.getName());

                model.addAttribute("memberName", member.getMemberName());
                model.addAttribute("currentMemberId", member.getMemberId()); // 현재 사용자 ID 추가
                model.addAttribute("memberRole", member.getRole().name()); // 현재 사용자 권한확인

                if (Objects.equals(boardService.getBoard(boardId).getMember().getMemberId(), member.getMemberId())) {
                    model.addAttribute("isTrue", true);
                } else {
                    model.addAttribute("isTrue", false);
                }
            }

            model.addAttribute("board", board);

            return "board/board";
        } catch (BoardNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/boards";
        }

    }

    // 게시판수정 GET
    @GetMapping("/board/edit/{boardId}")
    public String editBoardForm(@PathVariable("boardId") Long boardId, Model model,
                                Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            MemberService.addMemberInfoToModel(model, authentication);

            BoardResponseDto response = boardService.getBoardById(boardId);

            model.addAttribute("response", response);
            model.addAttribute("request", new BoardRequestDto());

            return "board/edit";
        } catch (BoardNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/boards";
        }
    }

    // 게시판수정 POST
    @PostMapping("/board/edit/{boardId}")
    public String editBoard(
            @PathVariable("boardId") Long boardId,
            @Valid @ModelAttribute("request") BoardRequestDto request,
            @RequestParam(value = "existingImageUrls", required = false) List<String> existingImageUrls,
            @RequestPart(value = "photoFiles", required = false) List<MultipartFile> photoFiles,
            RedirectAttributes redirectAttributes) {
        try {
            // 1) 새로 업로드된 파일을 실제 저장하고 그 URL 리스트를 만든다
            List<String> photoUrls = new ArrayList<>();
            if (photoFiles != null && !photoFiles.isEmpty()) {
                photoUrls = photoFiles.stream()
                        .filter(file -> !file.isEmpty()) // 빈 파일 필터링
                        .map(FileService::saveFileAndGetUrl) // 로컬 디스크 등에 저장 후 URL 반환
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
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/boards";
        }
    }

    // 게시판삭제 POST
    @PostMapping("/board/delete/{boardId}")
    public String deleteBoard(@PathVariable("boardId") Long boardId, RedirectAttributes redirectAttributes) {
        try {
            boardService.deleteBoard(boardId);
            return "redirect:/boards";
        } catch (BoardDeletionFailedException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/board/" + boardId;
        }
    }

    @PostMapping("/board/complete")
    public String setBoardComplete(@RequestParam("boardId") Long boardId){
        boardService.setBoardComplete(boardId);

        return "redirect:/boards/report-unread";
    }

}
