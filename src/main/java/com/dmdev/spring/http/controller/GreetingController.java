package com.dmdev.spring.http.controller;

import com.dmdev.spring.dto.UserReadDto;
import com.dmdev.spring.entity.Role;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
@SessionAttributes("user")
public class GreetingController {

    @ModelAttribute("roles")
    public List<Role> roles(){
        return Arrays.asList(Role.values());
    }


    @GetMapping("hello")
    public String hello(Model model, HttpServletRequest request,
                        @ModelAttribute("userRead") UserReadDto userReadDto){
        model.addAttribute("user", userReadDto);
        return "greeting/hello";
    }
    @GetMapping("hello/{id}")
    public ModelAndView hello2(ModelAndView modelAndView, HttpServletRequest request,
                              Integer age,
                              @RequestHeader(value = "accept", required = false) String accept,
                              @CookieValue("JSESSIONID") String jSession,
                              @PathVariable("id") Integer id){
        String sAccept = request.getHeader("accept");
        Cookie[] cookies = request.getCookies();
        modelAndView.setViewName("greeting/hello");
        return modelAndView;
    }

    @GetMapping("bye")
    public String bye(Model model, @SessionAttribute("user") UserReadDto userReadDto){
        userReadDto.getId();
        return "greeting/bye";
    }
}
