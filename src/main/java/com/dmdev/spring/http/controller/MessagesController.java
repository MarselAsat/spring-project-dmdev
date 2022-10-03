package com.dmdev.spring.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessagesController {

    private final MessageSource messageSource;

    @GetMapping
    public String getMessages(@RequestParam("key") String key,
                              @RequestParam("lang") String lang){
        return messageSource.getMessage(key, null, null, new Locale(lang));
    }
}
