package com.dmdev.spring.mapper;

import com.dmdev.spring.dto.CompanyReadDto;
import com.dmdev.spring.dto.UserReadDto;
import com.dmdev.spring.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<UserReadDto, User> {

    private final CompanyReadMapper companyReadMapper;
    @Override
    public UserReadDto mapTo(User object) {
        CompanyReadDto companyReadDto = Optional.ofNullable(object.getCompany())
                .map(companyReadMapper::mapTo)
                .orElse(null);

        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getBirthDate(),
                object.getFirstname(),
                object.getLastname(),
                object.getRole(),
                companyReadDto);
    }
}
