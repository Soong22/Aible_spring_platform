package com.aivle.platform.controller;

import com.aivle.platform.domain.Member;
import com.aivle.platform.domain.Notification;
import com.aivle.platform.dto.request.NotificationRequestDto;
import com.aivle.platform.dto.response.BoardResponseDto;
import com.aivle.platform.dto.response.NotificationResponseDto;
import com.aivle.platform.exception.notification.NotificationCreationFailedException;
import com.aivle.platform.service.FileService;
import com.aivle.platform.service.MemberService;
import com.aivle.platform.service.NotificationService;
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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final MemberService memberService;

    // 알림작성 GET
    @GetMapping("/notification/register")
    public String registerNotificationForm(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);
        model.addAttribute("request", new NotificationRequestDto());

        return "notification/register";
    }

    // 알림작성 POST
    @PostMapping("/notification/register")
    public String registerNotification(
            @Valid
            @ModelAttribute("request") NotificationRequestDto request,
            @RequestPart(value = "photoFiles", required = false) List<MultipartFile> photoFiles,
            @RequestParam("receiverId") Long receiverId,
            Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            Member sender = memberService.getMemberEmail(authentication.getName());
            Member receiver = memberService.getMember(receiverId);

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
            Notification notification = notificationService.createNotification(request, sender, receiver);

            return "redirect:/";
//            return "redirect:/notification/" + notification.getNotificationId();
        } catch (NotificationCreationFailedException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/notification/register";
        }
    }

    @GetMapping("/notifications")
    public String getNotifications(Model model, Authentication authentication,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size) {

        MemberService.addMemberInfoToModel(model, authentication);

        // 최신 작성순 정렬 추가 (createdAt 기준 내림차순)
        // Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 페이지 요청 파라미터 (기본값: 첫 페이지, 한 페이지당 5개 항목)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 페이징된 게시판 목록 조회
        Page<NotificationResponseDto> notifications = notificationService.getAllNotifications(pageable);


        // 모델에 멤버 목록과 페이징 정보 추가
        model.addAttribute("notifications", notifications); // 멤버 목록
        model.addAttribute("currentPage", page); // 현재 페이지
        model.addAttribute("totalPages", notifications.getTotalPages()); // 전체 페이지 수
        model.addAttribute("totalItems", notifications.getTotalElements()); // 전체 항목 수

        return "notification/notifications";

    }


}
