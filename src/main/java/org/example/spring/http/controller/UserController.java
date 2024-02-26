package org.example.spring.http.controller;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.example.spring.database.entity.Role;
import org.example.spring.dto.PageResponse;
import org.example.spring.dto.UserCreateEditDto;
import org.example.spring.dto.UserFilter;
import org.example.spring.service.CompanyService;
import org.example.spring.service.UserService;
import org.example.spring.validation.group.CreateAction;
import org.example.spring.validation.group.UpdateAction;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CompanyService companyService;

    @GetMapping
    public String findAll(Model model, @ModelAttribute UserFilter userFilter, Pageable pageable) {
        var page = userService.findAll(userFilter, pageable);
        model.addAttribute("users", PageResponse.of(page));
        return "user/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("companies", companyService.findAll());
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("companies", companyService.findAll());
        return "user/registration";
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Validated(value = {Default.class, CreateAction.class}) @ModelAttribute UserCreateEditDto user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/users/registration";
        }
        userService.create(user);
        return "redirect:/login";
    }

//    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id,
                         @Validated(value = {Default.class, UpdateAction.class}) @ModelAttribute UserCreateEditDto user) {
        return userService.update(id, user)
                .map(userReadDto -> "redirect:/users/" + userReadDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//    @DeleteMapping("{/id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (userService.delete(id)) {
            return "redirect:/users";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
