package com.aivle.platform.controller;

import com.aivle.platform.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homeForm(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "index";
    }

    // 메인 맵 페이지
    @GetMapping("/region/gwangjumap")
    public String gwangjumap(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "region/gwangjumap";
    }

    @GetMapping("/introduce/team_introduce")
    public String teamIntroduce(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "introduce/team_introduce"; // templates/introduce/team_introduce.html 매핑
    }

    @GetMapping("/login")
    public String loginPage(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "member/login";
    }

    @GetMapping("/notice_board/boards")
    public String getNoticeBoard(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);
        return "notice_board/boards";
    }

    @GetMapping("/management/anomaly")
    public String anomaly(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "management/anomaly";
    }

    @GetMapping("/management/crime")
    public String crime(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "management/crime";
    }

    // AI 소개 페이지 매핑
    @GetMapping("/ai_introduce/ai_introduce")
    public String aiIntroduce(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "ai_introduce/ai_introduce";
    }

    // 경찰 Google Map 페이지 매핑
    @GetMapping("/police/police_google_map")
    public String policeGoogleMap(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "police/police_google_map";
    }

}
