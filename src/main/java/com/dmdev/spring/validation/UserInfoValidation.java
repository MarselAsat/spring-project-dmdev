package com.dmdev.spring.validation;

import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class UserInfoValidation implements ConstraintValidator <UserInfo, UserCreateEditDto> {

    private final CompanyRepository companyRepository;

    @Override
    public boolean isValid(UserCreateEditDto createEditDto, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(createEditDto.getFirstname()) || StringUtils.hasText(createEditDto.getLastname());
    }
}
