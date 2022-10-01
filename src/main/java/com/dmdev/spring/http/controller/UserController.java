package com.dmdev.spring.http.controller;

import com.dmdev.spring.dto.PageResponse;
import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.dto.UserFilter;
import com.dmdev.spring.dto.UserReadDto;
import com.dmdev.spring.entity.Role;
import com.dmdev.spring.service.CompanyService;
import com.dmdev.spring.service.UserService;
import com.dmdev.spring.validation.group.CreateActive;
import com.dmdev.spring.validation.group.EditAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final CompanyService companyService;



    @GetMapping
    public String findAll(Model model, UserFilter userFilter, Pageable pageable){
//        model.addAttribute("users", userService.findAll());
        Page<UserReadDto> page = userService.findAll(userFilter, pageable);
        model.addAttribute("users", PageResponse.of(page));
        model.addAttribute("filter", userFilter);
        return "greeting/users";
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostAuthorize("returnObject")
    public String findById(Model model,
                           @PathVariable("id") Long id,
                           @CurrentSecurityContext SecurityContext securityContext,
                           @AuthenticationPrincipal UserDetails userDetails){
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
    public String createUser(@ModelAttribute @Validated({Default.class, CreateActive.class}) UserCreateEditDto user,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }

        userService.create(user);
        return "redirect:/login";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable("id") Long userId,
                             @Validated({Default.class, EditAction.class}) UserCreateEditDto user){
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
