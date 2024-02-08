package org.example.spring.http.controller;

import org.example.spring.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"user"})
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("login") LoginDto loginDto, Model model) {
        return "user/login";
    }
}
