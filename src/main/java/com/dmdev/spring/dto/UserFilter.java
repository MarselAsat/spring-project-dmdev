package com.dmdev.spring.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserFilter {
    String firstname;
    String lastname;
    LocalDate birthDate;
}
