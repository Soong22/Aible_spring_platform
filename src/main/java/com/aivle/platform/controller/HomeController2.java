package com.aivle.platform.controller;

import com.aivle.platform.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class HomeController2 {

    // AI 소개 페이지 매핑
    @GetMapping("/ai_introduce/ai_introduce")
    public String aiIntroduce(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "ai_introduce/ai_introduce";
    }

    // 팀 소개 페이지 매핑
    @GetMapping("/introduce/team_introduce")
    public String teamIntroduce(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "introduce/team_introduce";
    }

    // 경찰 Google Map 페이지 매핑
    @GetMapping("/police/police_google_map")
    public String policeGoogleMap(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);
        return "police/police_google_map";
    }

    // CCTV 페이지 매핑
    @GetMapping("/cctv/gwangju_cctv")
    public String gwangjuCctv(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);
        return "cctv/gwangju_cctv";
    }

    // 수사 요청 페이지 매핑
    @GetMapping("/notification/police_map")
    public String policeMap(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);
        return "notification/police_map";
    }

    // 이상 행동 관리 페이지 매핑
    @GetMapping("/management/anomaly")
    public String anomaly(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);
        return "management/anomaly";
    }

    // 범죄 이력 관리 페이지 매핑
    @GetMapping("/management/crime")
    public String crime(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);
        return "management/crime";
    }


}
