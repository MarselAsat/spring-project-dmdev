package com.dmdev.spring.dto;

import com.dmdev.spring.entity.Company;
import com.dmdev.spring.entity.Role;
import com.dmdev.spring.entity.UserChat;
import lombok.Builder;
import lombok.Value;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Value
public class UserReadDto {
    Long id;

    String username;

    LocalDate birthDate;

    String firstname;

    String lastname;

    Role role;

    CompanyReadDto company;
}
