package com.dmdev.spring.mapper;

import com.dmdev.spring.dto.CompanyReadDto;
import com.dmdev.spring.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyReadMapper implements Mapper<CompanyReadDto, Company> {
    @Override
    public CompanyReadDto mapTo(Company object) {
        return new  CompanyReadDto(
                object.getId(),
                object.getName());
    }
}
