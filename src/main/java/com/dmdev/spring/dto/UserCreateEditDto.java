package com.dmdev.spring.dto;

import com.dmdev.spring.entity.Role;
import com.dmdev.spring.validation.UserInfo;
import com.dmdev.spring.validation.group.CreateActive;
import com.dmdev.spring.validation.group.EditAction;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@FieldNameConstants
@UserInfo(groups = EditAction.class)
public class UserCreateEditDto {

    @Email
    String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    @Size(min = 3, max = 64)
    String firstname;

    String lastname;

    Role role;

    Integer company;

}

