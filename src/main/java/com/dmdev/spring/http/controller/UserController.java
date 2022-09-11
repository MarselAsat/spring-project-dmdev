package com.dmdev.spring.http.controller;

import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.entity.Role;
import com.dmdev.spring.service.CompanyService;
import com.dmdev.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final CompanyService companyService;

    @GetMapping
    public String findAll(Model model){
        model.addAttribute("users", userService.findAll());
        return "greeting/users";
    }

    @GetMapping("/{id}")
    public String findById(Model model,
                           @PathVariable("id") Long id){

      return userService.findById(id).map(user -> {
               model.addAttribute("user", user);
               model.addAttribute("roles", Role.values());
               model.addAttribute("companies", companyService.findAll());
                return "greeting/user";
       })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("companies", companyService.findAll());
        return "greeting/registration";
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@ModelAttribute UserCreateEditDto user,
                             RedirectAttributes redirectAttributes){
        if(true) {
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/users/registration";
        }
        return "redirect:/users/" + userService.create(user).getId();
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable("id") Long userId,
                             UserCreateEditDto user){
        return userService.update(userId, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long userId){
        if (!userService.delete(userId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }
}
