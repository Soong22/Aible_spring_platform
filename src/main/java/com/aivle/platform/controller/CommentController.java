package com.aivle.platform.controller;

import com.aivle.platform.domain.Board;
import com.aivle.platform.domain.Member;
import com.aivle.platform.dto.request.CommentRequestDto;
import com.aivle.platform.exception.comment.CommentCreationFailedException;
import com.aivle.platform.exception.comment.CommentDeletionFailedException;
import com.aivle.platform.exception.comment.CommentUpdateFailedException;
import com.aivle.platform.service.BoardService;
import com.aivle.platform.service.CommentService;
import com.aivle.platform.service.FileService;
import com.aivle.platform.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final MemberService memberService;

    // 댓글작성 POST
    @PostMapping("/comment/register")
    public String registerComment(
            @Valid
            @ModelAttribute("request") CommentRequestDto request,
            @RequestPart(value = "photoFiles", required = false) List<MultipartFile> photoFiles,
            Authentication authentication, RedirectAttributes redirectAttributes) {
        try{
            Member member = memberService.getMemberEmail(authentication.getName());
            Board board = boardService.getBoard(request.getBoardId());

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
            commentService.createComment(request, member, board);

            return "redirect:/board/" + request.getBoardId();
        } catch (CommentCreationFailedException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/board/" + request.getBoardId();
        }
    }

    // 댓글수정 POST
    @PostMapping("/comment/edit/{commentId}")
    public String editComment(
            @PathVariable("commentId") Long commentId,
            @Valid @ModelAttribute("request") CommentRequestDto request,
            @RequestParam(value = "existingImageUrls", required = false) List<String> existingImageUrls,
            @RequestPart(value = "photoFiles", required = false) List<MultipartFile> photoFiles, // 새 이미지 파일
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            Member member = memberService.getMemberEmail(authentication.getName());

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
            commentService.updateComment(commentId, request, member);

            return "redirect:/board/" + request.getBoardId();
        } catch (CommentUpdateFailedException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/board/" + request.getBoardId();
        }
    }

    // 댓글삭제 POST
    @PostMapping("/comment/delete")
    public String deleteComment(
            @RequestParam("commentId") Long commentId,
            @RequestParam("boardId") Long boardId,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            Member member = memberService.getMemberEmail(authentication.getName());
            commentService.deleteComment(commentId, member);

            return "redirect:/board/" + boardId;
        } catch (CommentDeletionFailedException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/board/" + boardId;
        }
    }

}
