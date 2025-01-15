package com.aivle.platform.controller;

import com.aivle.platform.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homeForm(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "index";
    }

    @GetMapping("/mypage")
    public String mypageForm(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        // 4. 일반적인 에러 메시지 추가 (필요하면 유지)
        model.addAttribute("errorMessage", "미구현");

        return "error/error"; // 도달하지 않음 (위에서 예외 발생)
    }

}
