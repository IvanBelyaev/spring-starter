package org.example.spring.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.spring.dto.UserReadDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("api/v1")
@SessionAttributes({"user"})
public class GreetingController {

    @GetMapping("hello")
    public ModelAndView hello(ModelAndView modelAndView,
                              HttpServletRequest request) {
//        request.setAttribute("user", new UserReadDto(1L, "Ivan"));
//        request.getSession().setAttribute("user", new UserReadDto(1L, "Ivan"));

        modelAndView.setViewName("greeting/hello");
        modelAndView.addObject("user", new UserReadDto(1L, "Ivan"));
        return modelAndView;
    }

    @GetMapping("hello/{id}")
    public ModelAndView hello2(ModelAndView modelAndView,
                              HttpServletRequest request,
                              @RequestParam Integer age,
                              @RequestHeader String accept,
                              @CookieValue("JSESSIONID") String jsessionId,
                              @PathVariable Integer id) {
        var ageParameterValue = request.getParameter("age");
        var acceptHeader = request.getHeader("accept");
        var cookies = request.getCookies();

        modelAndView.setViewName("greeting/hello");
        return modelAndView;
    }

    @GetMapping("bye")
    public ModelAndView bye(@SessionAttribute("user") UserReadDto user,
                            HttpServletRequest request) {
        var userAttribute = (UserReadDto) request.getSession().getAttribute("user");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("greeting/bye");
        return modelAndView;
    }
}
