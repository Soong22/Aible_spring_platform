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

        model.addAttribute("errorMessage", "Dasdadasd");

        return "error/error";
    }

    @GetMapping("/index2")
    public String homeForm2(Model model) {
        boolean isUser = true;
        boolean isAdmin = false;

        model.addAttribute("isUser", isUser);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("username", "user");

        return "index";
    }

}
