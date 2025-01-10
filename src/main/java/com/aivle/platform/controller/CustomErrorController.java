package com.aivle.platform.controller;

import com.aivle.platform.service.MemberService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model, Authentication authentication) {
        MemberService.addMemberInfoToModel(model, authentication);

        return "error/error";
    }

}
