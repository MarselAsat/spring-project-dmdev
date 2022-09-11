package com.dmdev.spring.mapper;

import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.entity.Company;
import com.dmdev.spring.entity.User;
import com.dmdev.spring.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<User, UserCreateEditDto>{
    private final CompanyRepository companyRepository;
    @Override
    public User mapTo(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    @Override
    public User mapTo(UserCreateEditDto from, User to) {
        copy(from, to);
        return to;
    }

    private void copy(UserCreateEditDto object, User user) {
        user.setUsername(object.getUsername());
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setBirthDate(object.getBirthDate());
        user.setRole(object.getRole());
        user.setCompany(getCompany(object.getCompany()));
    }

    private Company getCompany(Integer id){
        return Optional.ofNullable(id)
                .flatMap(companyRepository::findById)
                .orElse(null);
    }
}
