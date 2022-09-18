package com.dmdev.spring.service;

import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.dto.UserReadDto;
import com.dmdev.spring.entity.Role;
import com.dmdev.spring.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserServiceTest extends IntegrationTestBase {

    public static final Long USER_1 = 1L;
    public static final Integer COMPANY_1 = 1;

    private final UserService userService;

    @Test
    void findAll() {
        List<UserReadDto> users = userService.findAll();
        assertThat(users).hasSize(5);
    }

    @Test
    void findById() {
        Optional<UserReadDto> maybeUser = userService.findById(1L);
        maybeUser.ifPresent(user -> assertEquals("ivan@gmail.com", user.getUsername()));
    }

    @Test
    void create() {
        UserCreateEditDto user = new UserCreateEditDto(
                "test",
                LocalDate.now(),
                "Test",
                "Test",
                Role.USER,
                1
        );
        UserReadDto userReadDto = userService.create(user);
        assertEquals(user.getUsername(), userReadDto.getUsername());
        assertEquals(user.getFirstname(), userReadDto.getFirstname());
        assertEquals(user.getLastname(), userReadDto.getLastname());
        assertEquals(user.getBirthDate(), userReadDto.getBirthDate());
        assertEquals(user.getCompanyId(), userReadDto.getCompany().id());
        assertEquals(user.getRole().name(), userReadDto.getRole().name());
    }

    @Test
    void update() {
        UserCreateEditDto user = new UserCreateEditDto(
                "test",
                LocalDate.now(),
                "Test",
                "Test",
                Role.ADMIN,
                COMPANY_1
        );
        Optional<UserReadDto> update = userService.update(1L, user);
        UserReadDto userReadDto = update.orElse(null);
        assertEquals(user.getUsername(), userReadDto.getUsername());
        assertEquals(user.getFirstname(), userReadDto.getFirstname());
        assertEquals(user.getLastname(), userReadDto.getLastname());
        assertEquals(user.getBirthDate(), userReadDto.getBirthDate());
        assertEquals(user.getCompanyId(), userReadDto.getCompany().id());
        assertEquals(user.getRole().name(), userReadDto.getRole().name());
    }

    @Test
    void delete() {
        userService.findById(USER_1)
                .ifPresent(user ->{
                    assertTrue(userService.delete(USER_1));
                });
    }
}