package org.example.spring.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.spring.database.entity.Role;
import org.example.spring.dto.UserReadDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1")
@SessionAttributes({"user"})
public class GreetingController {

    @ModelAttribute("roles")
    public List<Role> roles() {
        return List.of(Role.values());
    }

    @GetMapping("hello")
    public String hello(Model model,
                        @ModelAttribute("userReadDto") UserReadDto userReadDto,
                        HttpServletRequest request) {
//        request.setAttribute("user", new UserReadDto(1L, "Ivan"));
//        request.getSession().setAttribute("user", new UserReadDto(1L, "Ivan"));

        model.addAttribute("user", new UserReadDto(1L, "Ivan"));
        return "greeting/hello";
    }

    @GetMapping("hello/{id}")
    public String hello2(Model model,
                         HttpServletRequest request,
                         @RequestParam Integer age,
                         @RequestHeader String accept,
                         @CookieValue("JSESSIONID") String jsessionId,
                         @PathVariable Integer id) {
        var ageParameterValue = request.getParameter("age");
        var acceptHeader = request.getHeader("accept");
        var cookies = request.getCookies();

        return "greeting/hello";
    }

    @GetMapping("bye")
    public String bye(@SessionAttribute("user") UserReadDto user,
                      Model model,
                      HttpServletRequest request) {
//        var userAttribute = (UserReadDto) request.getSession().getAttribute("user");

        return "greeting/bye";
    }
}
