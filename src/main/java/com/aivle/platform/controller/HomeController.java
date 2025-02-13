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

    @GetMapping("/login")
    public String loginPage(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "member/login";
    }

    @GetMapping("/index2")
    public String index2(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "index2"; // templates/index2.html 반환
    }

    @GetMapping("/notice_board/boards")
    public String getNoticeBoard(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);
        return "notice_board/boards";
    }


    // Google Map 기본 페이지 요청 처리
    @GetMapping("/cctv/google_map")
    public String googleMapPage(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);
        return "cctv/google_map"; // templates/cctv/google_map.html
    }

    // 구별 페이지 요청 처리
    @GetMapping("/cctv/{region}")
    public String cctvPage(@PathVariable("region") String region, Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return switch (region) {   // 북구
            // 광산구
            // 서구
            // 동구
            case "buk", "gwang", "seo", "dong", "nam" ->   // 남구
                    "cctv/cctv_" + region; // templates/cctv/cctv_{region}.html
            default -> "redirect:/cctv/google_map"; // 잘못된 region 값에 대해 기본 페이지로 리디렉션
        };
    }

    // JSON 데이터 제공
    @GetMapping("/cctv/cctv_data_{region}.json")
    public ResponseEntity<?> getCctvData(@PathVariable("region") String region, Model model, Authentication authentication) {
        String filePath = "src/main/resources/static/cctv_data_" + region + ".json"; // JSON 파일 경로
        Path path = Paths.get(filePath);

        if (Files.exists(path)) {
            try {
                byte[] content = Files.readAllBytes(path); // 파일 읽기
                return ResponseEntity.ok()
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .body(new String(content));
            } catch (IOException e) {
                // 파일 읽기 실패 처리
                return ResponseEntity.internalServerError().body("파일 읽기 오류: " + e.getMessage());
            }
        } else {
            // 파일이 없는 경우 404 반환
            return ResponseEntity.status(404).body("JSON 파일을 찾을 수 없습니다: " + filePath);
        }
    }



}
