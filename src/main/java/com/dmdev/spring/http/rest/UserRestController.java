package com.dmdev.spring.http.rest;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<UserReadDto> findAll(UserFilter userFilter, Pageable pageable){
        Page<UserReadDto> page = userService.findAll(userFilter, pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable("id") Long id){
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto createUser(@Validated({Default.class, CreateActive.class}) @RequestBody UserCreateEditDto user){
        return userService.create(user);
    }

    @PutMapping(value = "/{id}")
    public UserReadDto update(@PathVariable("id") Long id,
                              @Validated({Default.class, EditAction.class}) @RequestBody UserCreateEditDto user) {
        return userService.update(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        return userService.delete(userId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping(value = "{id}/avatar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Long id){
        return userService.findAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }
}