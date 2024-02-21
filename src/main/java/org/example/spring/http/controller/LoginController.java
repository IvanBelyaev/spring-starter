package org.example.spring.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"user"})
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "user/login";
    }
}
